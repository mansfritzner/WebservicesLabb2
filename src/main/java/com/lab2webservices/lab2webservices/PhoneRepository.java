package com.lab2webservices.lab2webservices;

import org.springframework.data.jpa.repository.JpaRepository;

interface PhoneRepository extends JpaRepository<Phone, Long> {

    Phone findByPhoneName(String phoneName);

    boolean existsPhoneByPhoneName(String phoneName);

}
