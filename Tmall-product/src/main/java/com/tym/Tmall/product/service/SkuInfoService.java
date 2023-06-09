package com.tym.Tmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.product.entity.SkuInfoEntity;
import com.tym.Tmall.product.vo.SkuItemVO;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-23 17:16:49
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageByCondiction(Map<String, Object> params);

    List<SkuInfoEntity> getSkusBySpuId(Long spuId);

    SkuItemVO item(Long skuId);

}

