package com.lab2webservices.lab2webservices;

import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

@Data
@Entity
public class Phone {
    @Id
    @GeneratedValue
    Long id;

    String phoneName;
    int brandId;

    public Phone( String phoneName, int brandId){
        this.phoneName = phoneName;
        this.brandId = brandId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
}
