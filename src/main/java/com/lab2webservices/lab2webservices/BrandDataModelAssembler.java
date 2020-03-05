package com.lab2webservices.lab2webservices;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
class BrandDataModelAssembler implements RepresentationModelAssembler<Brand, EntityModel<Brand>> {

    @Override
    public EntityModel<Brand> toModel(Brand brand) {
        return new EntityModel<>(brand,
                linkTo(methodOn(BrandController.class).one(brand.getId())).withSelfRel(),
                linkTo(methodOn(BrandController.class).all()).withRel("brands"));
    }

    @Override
    public CollectionModel<EntityModel<Brand>> toCollectionModel(Iterable<? extends Brand> entities) {
        var collection = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(collection,
                linkTo(methodOn(BrandController.class).all()).withSelfRel());
    }
}
