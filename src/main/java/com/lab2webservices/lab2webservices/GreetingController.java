package com.lab2webservices.lab2webservices;

//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
public class GreetingController {

//    private List<Phone> phoneList = Collections.synchronizedList(new ArrayList<>());
//
//    private static final String template = "Hello, %s!";
//    private final AtomicLong counter = new AtomicLong();
//
//    @GetMapping("/greeting")
//    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return new Greeting(counter.incrementAndGet(), String.format(template, name));
//    }
    private PhoneRepository repository;

    GreetingController(PhoneRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/phones")
    public List<Phone> all() {
        return repository.findAll();
    }

    @GetMapping(value = "/api/phones/{phoneName}")
    public Phone one(@PathVariable String phoneName) {
        return repository.findByPhoneName(phoneName);
    }

    @GetMapping(value = "/api/phones/brand/{brandId}")
    public Optional<Phone> one(@PathVariable int brandId) {
        return repository.findById((long) brandId);
    }

    @PostMapping("/api/phones")
    ResponseEntity<Phone> newPhone(@RequestBody Phone phone) {
        if(repository.existsPhoneByPhoneName(phone.getPhoneName()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        if(repository.findByPhoneName(phone.getPhoneName()).equals(phone.getPhoneName())) {
//            System.out.println("phone already exists");
//        }
        repository.save(phone);
        return new ResponseEntity<>(phone, HttpStatus.OK);
    }

//    public boolean containsName(PhoneRepository phoneRepository, final String name){
//        return phoneRepository.().map(MyObject::getName).filter(name::equals).findFirst().isPresent();
//    }

//    @PostMapping("/api/phones")
//    public ResponseEntity<Phone> createPhone(@RequestBody Phone phone) {
////        log.info("POST create Person " + person);
//        var p = phoneList.add(phone);
////        log.info("Saved to repository " + p);
//        HttpHeaders headers = new HttpHeaders();
////        headers.setLocation(linkTo(PersonsController.class).slash(p.getId()).toUri());
////        headers.add("Location", "/api/persons/" + phone.getId());
//        return new ResponseEntity<>(phone, headers, HttpStatus.CREATED);
//    }

}
