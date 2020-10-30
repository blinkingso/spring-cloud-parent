package com.yz.service;

import com.yz.pojo.Product;
import com.yz.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author andrew
 * @date 2020-10-30
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }
}
