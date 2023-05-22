package com.tym.Tmall.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class SkuItemSaleAttrVO {
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVO> attrValues;
}
