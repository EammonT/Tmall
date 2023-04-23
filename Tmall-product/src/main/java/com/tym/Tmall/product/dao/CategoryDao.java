package com.tym.Tmall.product.dao;

import com.tym.Tmall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-23 16:41:18
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
