package com.example.mybatis.service;

import com.example.mybatis.entity.Product;

public interface ProductService {

    Product findProductById(Long productId);

}
