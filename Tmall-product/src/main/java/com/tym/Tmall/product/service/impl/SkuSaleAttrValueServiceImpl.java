package com.tym.Tmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.common.utils.Query;
import com.tym.Tmall.product.dao.SkuSaleAttrValueDao;
import com.tym.Tmall.product.entity.SkuSaleAttrValueEntity;
import com.tym.Tmall.product.service.SkuSaleAttrValueService;
import com.tym.Tmall.product.vo.SkuItemSaleAttrVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuItemSaleAttrVO> getSaleAttrsBySpuId(Long spuId) {

        List<SkuItemSaleAttrVO> saleAttrVOS = this.baseMapper.getSaleAttrsBySpuId(spuId);

        return saleAttrVOS;
    }

    @Override
    public List<String> getSkuSaleAttrValuesAsStringList(Long skuId) {

        return this.baseMapper.getSkuSaleAttrValuesAsStringList();
    }

}