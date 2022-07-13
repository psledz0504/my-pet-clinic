package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.Pet;
import com.springframework.petclinic.service.PetService;
import com.springframework.petclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {
    private final PetTypeService petTypeService;

    public PetMapService(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public Pet save(Pet pet) {
        if(pet.getPetType() != null){
            //if pet type id == null -> pet type needs to be saved
            if(pet.getPetType().getId() == null){
                petTypeService.save(pet.getPetType());
            }
            return super.save(pet);
        }else{
            throw new RuntimeException("Pet type is required");
        }
    }
}
