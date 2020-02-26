package com.lab2webservices.lab2webservices;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BrandController {

    private final BrandRepository repository;

    public BrandController(BrandRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/brands")
    public List<Brand> all() {
        return repository.findAll();
    }

    @GetMapping(value = "/api/brands/{brandName}")
    public Brand one(@PathVariable String brandName) {
        return repository.findByBrandName(brandName);
    }

//    @GetMapping(value = "/api/phones/brand/{brandId}")
//    public Optional<Brand> one(@PathVariable Long brandId) {
//        return repository.findById((long) brandId);
//    }

    @PostMapping("/api/brands")
    Brand newBrand(@RequestBody Brand brand) {
        return repository.save(brand);
    }



}
