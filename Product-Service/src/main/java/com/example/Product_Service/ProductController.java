package com.example.Product_Service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private List<Product> products;

    @GetMapping("/")
    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
            this.products.add(new Product(1,"piring1",123));
            this.products.add(new Product(2,"gelas",123));
            this.products.add(new Product(3,"sendok",123));
            this.products.add(new Product(4,"garpu",123));
        }

        if (this.products.isEmpty()) {
            this.products.add(new Product(1,"piring",123));
            this.products.add(new Product(2,"gelas",123));
            this.products.add(new Product(3,"sendok",123));
            this.products.add(new Product(4,"garpu",123));
        }

        return products;
    }
}
