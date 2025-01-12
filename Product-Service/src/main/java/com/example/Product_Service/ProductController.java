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
            this.products.add(new Product(1,"piring pecah lagi asdasd",123));
            this.products.add(new Product(2,"gelas 1s",123));
            this.products.add(new Product(3,"sendok asd",123));
            this.products.add(new Product(4,"garpu talaasdasd",123));
        }

        if (this.products.isEmpty()) {
            this.products.add(new Product(1,"piring",123));
            this.products.add(new Product(2,"gelas",123));
            this.products.add(new Product(3,"sendok asda",123));
            this.products.add(new Product(4,"garpu",123));
        }

        return products;
    }
}
