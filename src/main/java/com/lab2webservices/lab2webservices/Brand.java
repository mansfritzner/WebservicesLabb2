package com.lab2webservices.lab2webservices;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Brand {
    @Id @GeneratedValue Long id;
    String brandName;

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    public Long getId() {
        return id;
    }
}
