package com.springframework.petclinic.model;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

//@Data
//@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@MappedSuperclass
public abstract class Person extends BaseEntity{
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
}
