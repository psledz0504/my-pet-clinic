package com.springframework.petclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Vet extends Person{
    private Set<Speciality> specialities = new HashSet<>();

}
