package com.lab2webservices.lab2webservices;

import lombok.Data;
import lombok.NoArgsConstructor;
//import org.springframework.hateoas.RepresentationModel;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Racket {

    @Id @GeneratedValue private  Long id;
    private String racketName;
    private int brandId;

    public Racket(Long id, String racketName, int brandId){
        this.id = id;
        this.racketName = racketName;
        this.brandId = brandId;
    }

    public String getRacketName() {
        return racketName;
    }

    public Long getId() {
        return id;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setRacketName(String racketName) {
        this.racketName = racketName;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }
}
