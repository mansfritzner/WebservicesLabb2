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
    @Id @GeneratedValue int id;
    String brandName;

}
