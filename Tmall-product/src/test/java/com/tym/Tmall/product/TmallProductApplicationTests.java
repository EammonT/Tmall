package com.tym.Tmall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tym.Tmall.product.entity.BrandEntity;
import com.tym.Tmall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TmallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    public void contextLoads(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(13L);
        brandEntity.setDescript("华为好");
        List<BrandEntity> brand_id = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 13));
        brand_id.forEach( (item) -> {
            System.out.println(item);
        });
    }
}
