package com.tym.Tmall.product.feign;

import com.tym.Tmall.common.to.SkuReductionTO;
import com.tym.Tmall.common.to.SpuBoundTO;
import com.tym.Tmall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("tmall-coupon")
public interface CouponFeignService {
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBound(@RequestBody SpuBoundTO spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveInfo")
    R saveSkuReduction(SkuReductionTO skuReductionTO);
}
