package com.tym.Tmall.order.vo;

import com.tym.Tmall.order.entity.OrderEntity;
import lombok.Data;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-04 22:34
 **/

@Data
public class SubmitOrderResponseVO {

    private OrderEntity order;

    /** 错误状态码 **/
    private Integer code;


}
