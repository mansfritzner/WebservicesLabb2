package com.lab2webservices.lab2webservices;

import org.springframework.data.jpa.repository.JpaRepository;

interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findByBrandName(String brandName);

}