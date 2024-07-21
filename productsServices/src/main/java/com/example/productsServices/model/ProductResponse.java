package com.example.productsServices.model;

import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {
    private List<ExternalProduct> products;
    @Data
    public static class ExternalProduct {
        private Long id;
        private String title;
        private List<String> images;
        private List<String> tags;
        private Double rating;
        private Double price;
        private Meta meta;

        @Data
        public static class Meta {
            private String barcode;
        }
    }
}
