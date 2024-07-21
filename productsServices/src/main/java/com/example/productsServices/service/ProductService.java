package com.example.productsServices.service;

import com.example.productsServices.dao.IProductsDao;
import com.example.productsServices.model.Product;
import com.example.productsServices.model.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private IProductsDao productRepository;
    @Autowired
    private RestTemplate restTemplate;
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id);
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Long id, Product product) {
        productRepository.update(id, product);
    }

    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }

    public void fetchAndStoreProducts() {
        String apiUrl = "https://dummyjson.com/products";
        ProductResponse response = restTemplate.getForObject(apiUrl, ProductResponse.class);

        if (response != null && response.getProducts() != null) {
            List<Product> products = response.getProducts().stream()
                    .map(this::mapToProduct)
                    .collect(Collectors.toList());

            for (Product product : products) {
                productRepository.save(product);
            }
        }
    }

    private Product mapToProduct(ProductResponse.ExternalProduct externalProduct) {
        return new Product(
                externalProduct.getId(),
                externalProduct.getMeta().getBarcode(),
                externalProduct.getTitle(),
                externalProduct.getImages(),
                externalProduct.getTags(),
                externalProduct.getRating(),
                externalProduct.getPrice()
        );
    }
}
