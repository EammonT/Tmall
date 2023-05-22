package com.tym.Tmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.common.utils.Query;
import com.tym.Tmall.product.dao.SkuInfoDao;
import com.tym.Tmall.product.entity.SkuInfoEntity;
import com.tym.Tmall.product.entity.SpuInfoDescEntity;
import com.tym.Tmall.product.service.AttrGroupService;
import com.tym.Tmall.product.service.SkuInfoService;
import com.tym.Tmall.product.service.SkuSaleAttrValueService;
import com.tym.Tmall.product.service.SpuInfoDescService;
import com.tym.Tmall.product.vo.SkuItemSaleAttrVO;
import com.tym.Tmall.product.vo.SkuItemVO;
import com.tym.Tmall.product.vo.SpuItemAttrGroupVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Resource
    private SpuInfoDescService spuInfoDescService;

    @Resource
    private AttrGroupService attrGroupService;

    @Resource
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondiction(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> wrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.hasLength(key)){
            wrapper.and((w)->{
                w.eq("sku_id",key).or().like("sku_name",key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if (StringUtils.hasLength(catelogId)&& !"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }
        String brandId = (String) params.get("brandId");
        if (StringUtils.hasLength(brandId)&& !"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }
        String max = (String) params.get("max");
        if (StringUtils.hasLength(max)){
            BigDecimal maxB = new BigDecimal(max);
            if (maxB.compareTo(new BigDecimal(0))==1) {
                wrapper.le("price",max);

            }
        }
        String min = (String) params.get("min");
        if (StringUtils.hasLength(min)){
            wrapper.ge("price",min);
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);

    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {

        List<SkuInfoEntity> list = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id",spuId));
        return list;
    }

    @Override
    public SkuItemVO item(Long skuId) {
        SkuItemVO skuItemVO = new SkuItemVO();
        SkuInfoEntity info = getById(skuId);
        Long catalogId = info.getCatalogId();
        skuItemVO.setInfo(info);
        Long spuId = info.getSpuId();
        List<SkuItemSaleAttrVO> saleAttrVOS = skuSaleAttrValueService.getSaleAttrsBySpuId(spuId);
        saleAttrVOS.stream().map(item->{
            System.out.println("item================>"+item);
            return item;
        });
        skuItemVO.setSaleAttr(saleAttrVOS);
        SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(spuId);
        System.out.println("spuInfoDescEntity================>"+spuInfoDescEntity);
        skuItemVO.setDesc(spuInfoDescEntity);
        List<SpuItemAttrGroupVO> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(spuId,catalogId);
        attrGroupVos.stream().map(item->{
            System.out.println("spuInfoDescEntity================>"+item);
            return item;
        });
        skuItemVO.setGroupAttrs(attrGroupVos);
        return skuItemVO;
    }
}