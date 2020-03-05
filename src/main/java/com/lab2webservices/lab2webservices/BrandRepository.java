package com.lab2webservices.lab2webservices;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByBrandName(String brandName);

}