package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.Owner;
import com.springframework.petclinic.model.Pet;
import com.springframework.petclinic.service.CrudService;
import com.springframework.petclinic.service.OwnerService;
import com.springframework.petclinic.service.PetService;
import com.springframework.petclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@Profile({"default", "map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService{
    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerMapService(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }

    @Override
    public Owner save(Owner owner) {
        if(owner != null){
            if(owner.getPets()!=null){
                owner.getPets().forEach(pet -> {
                    if(pet.getId() == null){
                        //save pet using petService
                        Pet savedPet = petService.save(pet);
                        //populate saved Pet id to current pet -> complete associacion with owner
                        pet.setId(savedPet.getId());
                    }
                });
            }
            return super.save(owner);
        }else{
            return null;
        }
    }
}
