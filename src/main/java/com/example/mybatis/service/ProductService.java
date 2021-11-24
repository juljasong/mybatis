package com.example.mybatis.service;

import com.example.mybatis.dao.ProductMapper;
import com.example.mybatis.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductMapper productMapper;

    public Product getById(Long productId) {
        return productMapper.findById(productId);
    }
}