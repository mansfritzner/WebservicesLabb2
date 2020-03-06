package com.lab2webservices.lab2webservices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(RacketRepository racketRepo) {
        return args -> {
            racketRepo.save(new Racket(0L, "Tennis", 1));
            racketRepo.save(new Racket(0L, "Badminton", 2));
        };
    }
}
