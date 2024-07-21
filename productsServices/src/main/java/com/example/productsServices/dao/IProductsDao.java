package com.example.productsServices.dao;

import com.example.productsServices.model.Product;

import java.util.List;

public interface IProductsDao {
    List<Product> findAll();
    Product findById(Long id);
    void save(Product product);
    void update(Long id, Product product);
    void delete(Long id);
}
