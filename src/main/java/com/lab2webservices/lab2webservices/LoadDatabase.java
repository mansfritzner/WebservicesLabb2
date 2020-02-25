package com.lab2webservices.lab2webservices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(PhoneRepository repository) {
        return args -> {
            repository.save(new Phone("Iphone X", 1));
            repository.save(new Phone("Samsung Galaxy S10", 2));
        };
    }
}
