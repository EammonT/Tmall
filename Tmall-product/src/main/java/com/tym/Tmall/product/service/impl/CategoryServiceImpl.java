package com.tym.Tmall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.common.utils.Query;
import com.tym.Tmall.product.dao.CategoryDao;
import com.tym.Tmall.product.entity.CategoryEntity;
import com.tym.Tmall.product.service.CategoryBrandRelationService;
import com.tym.Tmall.product.service.CategoryService;
import com.tym.Tmall.product.vo.Catelog2VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> entities = baseMapper.selectList(null);
        //一级分类
        List<CategoryEntity> level1Menus = entities.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == 0;
        }).map( (menu) -> {
            menu.setChildren(getChildrens(menu,entities));
            return menu;
        }).sorted( (menu1,menu2) -> {
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());


        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {


        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        CategoryEntity byId = this.getById(catelogId);
        paths.add(catelogId);
        while (byId.getParentCid()!=0){
            Long parentCid = byId.getParentCid();
            byId = this.getById(parentCid);
            paths.add(parentCid);
        }
        Collections.reverse(paths);
        return paths.toArray(new Long[paths.size()]);
    }

    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    @Override
    public List<CategoryEntity> getLevelFirstCategorys() {

        List<CategoryEntity> entities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return entities;
    }


    @Override
    public Map<String, List<Catelog2VO>> getCatelogJson(){
        //空结果缓存，防止缓存穿透


        //加锁，防止缓存击穿
        String catalogJson = redisTemplate.opsForValue().get("catalogJson");
        if (!StringUtils.hasLength(catalogJson)){
            Map<String, List<Catelog2VO>> catelogJsonFromDB = getCatelogJsonFromDB();
        }
        Map<String, List<Catelog2VO>> result = JSON.parseObject(catalogJson,new TypeReference<Map<String, List<Catelog2VO>>>(){});
        return  result;
    }

    public Map<String, List<Catelog2VO>> getCatelogJsonFromDB() {

        synchronized (this){
            String catalogJson = redisTemplate.opsForValue().get("catalogJson");
            if (StringUtils.hasLength(catalogJson)){
                Map<String, List<Catelog2VO>> result = JSON.parseObject(catalogJson,new TypeReference<Map<String, List<Catelog2VO>>>(){});
                return  result;
            }
            //查1级分类
            List<CategoryEntity> firstCategorys = getLevelFirstCategorys();
            //封装数据
            Map<String, List<Catelog2VO>> parent_cid = firstCategorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                //每一个1级分类的所有2级分类
                List<CategoryEntity> entities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", v.getCatId()));
                List<Catelog2VO> collect = null;
                if (entities != null) {
                    collect = entities.stream().map(l2 -> {
                        Catelog2VO catelog2VO = new Catelog2VO(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName());
                        //当前2级分类的3级分类
                        List<CategoryEntity> thirdCategorys = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", l2.getCatId()));
                        if (thirdCategorys!=null){
                            List<Catelog2VO.Category3Vo> collect2 = thirdCategorys.stream().map(l3 -> {
                                Catelog2VO.Category3Vo category3Vo = new Catelog2VO.Category3Vo(l2.getCatId().toString(),l3.getCatId().toString(), l3.getName());

                                return category3Vo;
                            }).collect(Collectors.toList());
                            catelog2VO.setCatalog3List(collect2);
                        }
                        return catelog2VO;
                    }).collect(Collectors.toList());

                }
                return collect;
            }));
            String s = JSON.toJSONString(parent_cid);
            redisTemplate.opsForValue().set("catalogJson",s,1, TimeUnit.DAYS);
            return parent_cid;
        }

    }

    //查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map( categoryEntity -> {
            categoryEntity.setChildren(getChildrens(categoryEntity,all));
            return categoryEntity;
        }).sorted((menu1,menu2) -> {
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }

}