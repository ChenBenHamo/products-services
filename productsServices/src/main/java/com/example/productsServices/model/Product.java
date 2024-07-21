package com.example.productsServices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private Long id;
    private String barcode;
    private String title;
    private List<String> images;
    private List<String> tags;
    private Double rating;
    private Double price;
}
