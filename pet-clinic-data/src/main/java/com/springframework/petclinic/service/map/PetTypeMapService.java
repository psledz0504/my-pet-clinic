package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.PetType;
import com.springframework.petclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class PetTypeMapService extends AbstractMapService<PetType, Long> implements PetTypeService {
}
