package com.tym.Tmall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PurchaseFinishVO {
    @NotNull(message = "id不允许为空")
    private Long id;

    private List<PurchaseItemDoneVO> items;
}
