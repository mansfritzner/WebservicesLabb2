package com.lab2webservices.lab2webservices;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
class RacketDataModelAssembler implements RepresentationModelAssembler<Racket, EntityModel<Racket>> {

    @Override
    public EntityModel<Racket> toModel(Racket racket) {
        return new EntityModel<>(racket,
                linkTo(methodOn(RacketController.class).one(racket.getId())).withSelfRel(),
                linkTo(methodOn(RacketController.class).all()).withRel("rackets"));
    }

    @Override
    public CollectionModel<EntityModel<Racket>> toCollectionModel(Iterable<? extends Racket> entities) {
        var collection = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(collection,
                linkTo(methodOn(RacketController.class).all()).withSelfRel());
    }
}