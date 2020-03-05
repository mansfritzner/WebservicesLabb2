package com.lab2webservices.lab2webservices;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/brands")
@RestController
public class BrandController {

    private final BrandRepository repository;

    public BrandController(BrandRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Brand> all() {
        return repository.findAll();
    }

    @GetMapping(value = "{brandName}")
    public Brand one(@PathVariable String brandName) {
        return repository.findByBrandName(brandName);
    }


    @PostMapping
    Brand newBrand(@RequestBody Brand brand) {
        return repository.save(brand);
    }



}
