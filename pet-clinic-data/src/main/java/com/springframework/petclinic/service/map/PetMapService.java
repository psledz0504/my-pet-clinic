package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.Pet;
import com.springframework.petclinic.service.PetService;
import org.springframework.stereotype.Service;

@Service
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {
}
