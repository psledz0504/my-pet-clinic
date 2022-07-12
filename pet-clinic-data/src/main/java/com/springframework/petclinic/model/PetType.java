package com.springframework.petclinic.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PetType extends BaseEntity{
    private String name;
}
