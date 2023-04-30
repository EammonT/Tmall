package com.tym.Tmall.product;

import com.tym.Tmall.product.entity.BrandEntity;
import com.tym.Tmall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TmallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    public void contextLoads(){
        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(13L);
        brandEntity.setDescript("华为很好");
        brandService.updateById(brandEntity);
    }
}
