package com.example.mybatis.dao;

import com.example.mybatis.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM products WHERE id=#{productId}")
    Product findById(@Param("productId") Long productId);

}
