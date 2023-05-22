package com.tym.Tmall.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class SpuItemAttrGroupVO{
    private String groupName;
    private List<Attr> attrs;
}