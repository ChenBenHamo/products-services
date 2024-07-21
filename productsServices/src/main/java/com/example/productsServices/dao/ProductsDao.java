package com.example.productsServices.dao;

import com.example.productsServices.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductsDao implements IProductsDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM PRODUCTS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            try {
                return new Product(
                        Long.valueOf(rs.getString("ID")),
                        rs.getString("BARCODE"),
                        rs.getString("NAME"),
                        objectMapper.readValue(rs.getString("IMAGES"), List.class),
                        objectMapper.readValue(rs.getString("TAGS"), List.class),
                        Double.valueOf(rs.getString("RATING")),
                        Double.valueOf(rs.getString("PRICE"))
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public Product findById(Long id) {
        String sql = "SELECT * FROM PRODUCTS WHERE id = ?";
        List<Product> products = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> {
            try {
                return new Product(
                        Long.valueOf(rs.getString("id")),
                        rs.getString("barcode"),
                        rs.getString("name"),
                        objectMapper.readValue(rs.getString("images"), List.class),
                        objectMapper.readValue(rs.getString("tags"), List.class),
                        Double.valueOf(rs.getString("rating")),
                        Double.valueOf(rs.getString("price"))
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return products.isEmpty() ? null : products.get(0);
    }

    @Override
    public void save(Product product) {
        try {
            String sql = "INSERT INTO PRODUCTS (id, barcode, name, images, tags, rating, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, product.getId().toString(), product.getBarcode(), product.getTitle(),
                    objectMapper.writeValueAsString(product.getImages()),
                    objectMapper.writeValueAsString(product.getTags()),
                    product.getRating().toString(), product.getPrice().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Long id, Product product) {
        try {
            String sql = "UPDATE PRODUCTS SET barcode = ?, name = ?, images = ?, tags = ?, rating = ?, price = ? WHERE id = ?";
            jdbcTemplate.update(sql, product.getBarcode(), product.getTitle(),
                    objectMapper.writeValueAsString(product.getImages()),
                    objectMapper.writeValueAsString(product.getTags()),
                    product.getRating().toString(), product.getPrice().toString(), id.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM PRODUCTS WHERE id = ?";
        jdbcTemplate.update(sql, id.toString());
    }
}
