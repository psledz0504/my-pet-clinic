package com.springframework.petclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity{
    private String firstname;
    private String lastname;
}
