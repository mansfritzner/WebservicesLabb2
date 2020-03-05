package com.lab2webservices.lab2webservices;

//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.RepresentationModel;

import org.apache.coyote.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/api/v1/phones")
@RestController
public class PhoneController {

//    private List<Phone> phoneList = Collections.synchronizedList(new ArrayList<>());
//
//    private static final String template = "Hello, %s!";
//    private final AtomicLong counter = new AtomicLong();
//
//    @GetMapping("/greeting")
//    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return new Greeting(counter.incrementAndGet(), String.format(template, name));
//    }

    private final PhoneDataModelAssembler phoneDataModelAssembler;
    private PhoneRepository repository;

    PhoneController(PhoneRepository repository, PhoneDataModelAssembler phoneDataModelAssembler) {
        this.repository = repository;
        this.phoneDataModelAssembler = phoneDataModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Phone>> all() {
        return phoneDataModelAssembler.toCollectionModel(repository.findAll());
    }

    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<EntityModel<Phone>> one(@PathVariable Long id) {
        return repository.findById(id)
                .map(phoneDataModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{phoneName:[\\D]+[\\d]*}")
    public ResponseEntity<EntityModel<Phone>> oneOrMany(@PathVariable String phoneName) {
        return repository.findByPhoneName(phoneName)
                .map(phoneDataModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping(value = "/brand/{brandId}")
    public Optional<Phone> one(@PathVariable int brandId) {
        return repository.findById((long) brandId);
    }

    @PostMapping
    ResponseEntity<Phone> newPhone(@RequestBody Phone phone) {
        if (repository.existsPhoneByPhoneName(phone.getPhoneName()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        if(repository.findByPhoneName(phone.getPhoneName()).equals(phone.getPhoneName())) {
//            System.out.println("phone already exists");
//        }

        repository.save(phone);
        var entityModelResponseEntity = repository.findById(phone.getId())
                .map(phoneDataModelAssembler::toModel);
        return new ResponseEntity(entityModelResponseEntity, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePhone(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<Phone>> replacePhone(@RequestBody Phone phoneIn, @PathVariable Long id) {

        if(repository.findById(id).isPresent()){
            var p = repository.findById(id)
                    .map(existingPhone -> {
                        existingPhone.setPhoneName(phoneIn.getPhoneName());
                        existingPhone.setBrandId(phoneIn.getBrandId());
                        repository.save(existingPhone);
                        return existingPhone;})
                    .get();
            var entityModel = phoneDataModelAssembler.toModel(p);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    ResponseEntity<EntityModel<Phone>> modifyUser(@RequestBody Phone updatedPhone, @PathVariable Long id){
        if(repository.findById(id).isPresent()){
            var p = repository.findById(id)
                    .map(newPhone -> {
                        if(updatedPhone.getPhoneName() != null)
                            newPhone.setPhoneName(updatedPhone.getPhoneName());
                        if(updatedPhone.getBrandId() != 0)
                            newPhone.setBrandId(updatedPhone.getBrandId());
                        repository.save(newPhone);
                        return newPhone;}).get();
            var entityModel = phoneDataModelAssembler.toModel(p);
            return new ResponseEntity<>(entityModel, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
