package com.tym.Tmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品评价回复关系
 * 
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 16:14:29
 */
@Data
@TableName("pms_comment_replay")
public class CommentReplayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	/**
	 * 评论id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long commentId;
	/**
	 * 回复id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long replyId;

}
