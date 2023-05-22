package com.tym.Tmall.product;

import com.tym.Tmall.product.dao.AttrGroupDao;
import com.tym.Tmall.product.dao.SkuSaleAttrValueDao;
import com.tym.Tmall.product.entity.BrandEntity;
import com.tym.Tmall.product.service.BrandService;
import com.tym.Tmall.product.service.CategoryService;
import com.tym.Tmall.product.service.SkuSaleAttrValueService;
import com.tym.Tmall.product.vo.SkuItemSaleAttrVO;
import com.tym.Tmall.product.vo.SpuItemAttrGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TmallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Test
    public void contextLoads(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(13L);
        brandEntity.setDescript("华为很好");
        brandService.updateById(brandEntity);
    }

    @Test
    public void testFindPath(){
        Long[] catelogPath = categoryService.findCatelogPath(225L);
        log.info("完整路径：{}", Arrays.asList(catelogPath));
    }

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void test(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("hello","world"+ UUID.randomUUID().toString());
        System.out.println(ops.get("hello"));
    }

    @Test
    public void redisson(){
        System.out.println(redissonClient);
    }

    @Test
    public void testAttrGroupDao(){
        List<SpuItemAttrGroupVO> attrGroupWithAttrsBySpuId = attrGroupDao.getAttrGroupWithAttrsBySpuId(11L, 225L);

        System.out.println(attrGroupWithAttrsBySpuId);
    }

    @Test
    public void testSkuDap(){
        System.out.println(skuSaleAttrValueDao.getSaleAttrsBySpuId(11L));
    }

    @Test
    public void test1(){
        List<SkuItemSaleAttrVO> saleAttrsBySpuId = skuSaleAttrValueService.getSaleAttrsBySpuId(11L);
        System.out.println(saleAttrsBySpuId);
    }

}
