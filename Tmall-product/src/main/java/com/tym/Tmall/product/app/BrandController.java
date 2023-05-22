package com.tym.Tmall.product.app;

import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.common.utils.R;
import com.tym.Tmall.product.entity.BrandEntity;
import com.tym.Tmall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;


/**
 * 品牌
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-23 16:41:18
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save(@Valid @RequestBody BrandEntity brand /*, BindingResult result*/) {
//        Map<String,String> map = new HashMap<>();
//        if (result.hasErrors()) {
//            result.getFieldErrors().forEach( fieldError -> {
//                String defaultMessage = fieldError.getDefaultMessage();
//                String field = fieldError.getField();
//                map.put(field,defaultMessage);
//            });
//            return R.error(400,"提交数据不合法").put("data",map);
//        }else {
//            brandService.save(brand);
//        }
        brandService.save(brand);
        return R.ok();
    }

    @RequestMapping("/uploadImg")
    public R updateFile(@RequestParam("name") String name,
                        @RequestParam("files") MultipartFile[] files) {
        for (MultipartFile multipartFile : files) {
            System.out.println(multipartFile);
        }
        return R.ok().put("data",files);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@Valid @RequestBody BrandEntity brand) {
        brandService.updateDetail(brand);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        for (int i = 0; i < brandIds.length; i++) {
            System.out.println(brandIds[i]);
        }
        brandService.removeByIds(Arrays.asList(brandIds));
        System.out.println("删除成功");
        return R.ok();
    }

}
