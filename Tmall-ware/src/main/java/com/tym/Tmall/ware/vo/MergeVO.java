package com.tym.Tmall.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class MergeVO {
    private Long purchaseId;
    private List<Long> items;
}
