package com.tym.Tmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.product.entity.SpuInfoEntity;
import com.tym.Tmall.product.vo.SpuSaveVO;

import java.util.Map;

/**
 * spu信息
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-23 17:16:49
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVO spuSaveVO);

    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void up(Long spuId);

    SpuInfoEntity getSpuInfoBySkuId(Long skuId);
}

