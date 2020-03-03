package com.lab2webservices.lab2webservices;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
class PhoneDataModelAssembler implements RepresentationModelAssembler<Phone, EntityModel<Phone>> {

    @Override
    public EntityModel<Phone> toModel(Phone phone) {
        return new EntityModel<>(phone,
                linkTo(methodOn(PhoneController.class).one(phone.getPhoneName())).withSelfRel(),
                linkTo(methodOn(PhoneController.class).all()).withRel("phones"));
    }

    @Override
    public CollectionModel<EntityModel<Phone>> toCollectionModel(Iterable<? extends Phone> entities) {
        var collection = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());
        return new CollectionModel<>(collection,
                linkTo(methodOn(PhoneController.class).all()).withSelfRel());
    }
}