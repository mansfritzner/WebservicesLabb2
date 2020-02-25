package com.lab2webservices.lab2webservices;

import lombok.Data;
//import org.springframework.hateoas.RepresentationModel;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Phone {

    @Id @GeneratedValue private  Long id;
    private String phoneName;
    private int brandId;

    public Phone(){}

    public Phone(String phoneName, int brandId){
        this.phoneName = phoneName;
        this.brandId = brandId;
    }
}
