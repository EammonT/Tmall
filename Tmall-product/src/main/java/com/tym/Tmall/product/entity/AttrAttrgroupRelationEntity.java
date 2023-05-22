package com.tym.Tmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 属性&属性分组关联
 * 
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 16:14:29
 */
@Data
@TableName("pms_attr_attrgroup_relation")
public class AttrAttrgroupRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 属性id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long attrId;
	/**
	 * 属性分组id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long attrGroupId;
	/**
	 * 属性组内排序
	 */
	private Integer attrSort;

}
