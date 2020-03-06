package com.lab2webservices.lab2webservices;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface RacketRepository extends JpaRepository<Racket, Long> {

    Optional<Racket> findByRacketName(String racketName);

    boolean existsRacketByRacketName(String racketName);

}
