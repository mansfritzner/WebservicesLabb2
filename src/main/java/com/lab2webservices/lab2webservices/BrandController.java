package com.lab2webservices.lab2webservices;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/brands")
@RestController
public class BrandController {


    private final BrandDataModelAssembler brandDataModelAssembler;
    private final BrandRepository brandRepository;

    public BrandController(BrandRepository repository, BrandDataModelAssembler brandDataModelAssembler) {
        this.brandRepository = repository;
        this.brandDataModelAssembler = brandDataModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Brand>> all() {
        return brandDataModelAssembler.toCollectionModel(brandRepository.findAll());
    }

    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<EntityModel<Brand>> one(@PathVariable Long id) {
        return brandRepository.findById(id)
                .map(brandDataModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{brandName:[\\D]+}")
    public ResponseEntity<EntityModel<Brand>> one(@PathVariable String brandName) {
        return brandRepository.findByBrandName(brandName)
                .map(brandDataModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    Brand newBrand(@RequestBody Brand brand) {
        return this.brandRepository.save(brand);
    }



}
