package com.springframework.petclinic.service;

import com.springframework.petclinic.model.Owner;

import java.util.List;
import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long>{
    Owner findByLastName(String lastName);
    List<Owner> findAllByLastNameContainingIgnoreCase(String lastName);
}
