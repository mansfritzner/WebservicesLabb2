package com.lab2webservices.lab2webservices;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;



@RequestMapping("/api/v1/rackets")
@RestController
public class RacketController {

    private final RacketDataModelAssembler racketDataModelAssembler;
    private RacketRepository repository;

    RacketController(RacketRepository repository, RacketDataModelAssembler racketDataModelAssembler) {
        this.repository = repository;
        this.racketDataModelAssembler = racketDataModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Racket>> all() {
        return racketDataModelAssembler.toCollectionModel(repository.findAll());
    }

    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<EntityModel<Racket>> one(@PathVariable Long id) {
        return repository.findById(id)
                .map(racketDataModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{racketName:[\\D]+[\\d]*}")
    public ResponseEntity<EntityModel<Racket>> oneOrMany(@PathVariable String racketName) {
        return repository.findByRacketName(racketName)
                .map(racketDataModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    ResponseEntity<Racket> newRacket(@RequestBody Racket racket) {
        if (repository.existsRacketByRacketName(racket.getRacketName()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);


        repository.save(racket);
        var entityModelResponseEntity = repository.findById(racket.getId())
                .map(racketDataModelAssembler::toModel);
        return new ResponseEntity(entityModelResponseEntity, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteRacket(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Racket>> replaceRacket(@RequestBody Racket racketIn, @PathVariable Long id) {

        if(repository.findById(id).isPresent()){
            var p = repository.findById(id)
                    .map(existingRacket -> {
                            existingRacket.setRacketName(racketIn.getRacketName());
                            existingRacket.setBrandId(racketIn.getBrandId());
                        repository.save(existingRacket);
                        return existingRacket;})
                    .get();
            var entityModel = racketDataModelAssembler.toModel(p);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    ResponseEntity<EntityModel<Racket>> modifyUser(@RequestBody Racket updatedRacket, @PathVariable Long id){
        if(repository.findById(id).isPresent()){
            var p = repository.findById(id)
                    .map(newRacket -> {
                        if(updatedRacket.getRacketName() != null)
                            newRacket.setRacketName(updatedRacket.getRacketName());
                        if(updatedRacket.getBrandId() != 0)
                            newRacket.setBrandId(updatedRacket.getBrandId());
                        repository.save(newRacket);
                        return newRacket;}).get();
            var entityModel = racketDataModelAssembler.toModel(p);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
