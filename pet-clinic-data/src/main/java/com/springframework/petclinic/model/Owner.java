package com.springframework.petclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Owner extends Person{
    private Set<Pet> pets;

}
