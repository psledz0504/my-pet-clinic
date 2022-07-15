package com.springframework.petclinic.controller;

import com.springframework.petclinic.model.Owner;
import com.springframework.petclinic.model.Pet;
import com.springframework.petclinic.model.PetType;
import com.springframework.petclinic.service.OwnerService;
import com.springframework.petclinic.service.PetService;
import com.springframework.petclinic.service.PetTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/owners/{ownerId}")
@Controller
@Slf4j
public class PetController {
    private final PetService petService;
    private final PetTypeService petTypeService;
    private final OwnerService ownerService;

    private final String CREATE_OR_UPDATE_PET_FROM =  "pets/createOrUpdatePetForm";

    public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
        this.petService = petService;
        this.petTypeService = petTypeService;
        this.ownerService = ownerService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes(){
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner populateOwner(@PathVariable Long ownerId){
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @RequestMapping("/pets/new")
    public String initCreateForm(Owner owner, Model model){
        Pet pet = new Pet();
        owner.getPets().add(pet);
        model.addAttribute("pet", pet);
        return CREATE_OR_UPDATE_PET_FROM;
    }

    @RequestMapping(value = "/pets/new", method = RequestMethod.POST)
    public String processCreatePetForm(Owner owner, @Validated Pet pet, BindingResult result, @PathVariable("ownerId") Long ownerId){
        // validate the input data
        if(StringUtils.hasLength(pet.getName())) {
            if (pet.isNew() && !owner.getPets().contains(pet)) {
                Pet foundPet = owner.getPets().stream()
                        .filter(pet1 -> pet1.getName().equals(pet.getName()))
                        .findFirst().orElse(null);
                if (foundPet!=null) {
                result.rejectValue("name", "duplicate", "already used for other pet for this owner");
                }
            }
        }else {
            result.rejectValue("name", "null", "name of pet cannot be empty");
        }

        log.error(String.valueOf(result.hasErrors()));

        // update the pet information in database
        // when apply the hibernate db, data store in db in sql style,
        // all the java model only created after apply CrudRepository to retrieve
        // data from database; or created before apply crudRepository to store data to database
        // Therefore, there is no need to update the pet set in owner model.
        // Instead of it, only to maintain the relationship between pet and owner in hibernate db.

        pet.setOwner(owner);
        if(result.hasErrors()){
            return CREATE_OR_UPDATE_PET_FROM;
        }else{
            petService.save(pet);
            return "redirect:/owners/" + ownerId;
        }
    }

    @RequestMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable Long petId, Owner owner, Model model){
        Pet pet = petService.findById(petId);
        model.addAttribute("pet", pet);
        return CREATE_OR_UPDATE_PET_FROM;
    }

    @RequestMapping(value = "/pets/{petId}/edit", method = RequestMethod.POST)
    public String processUpdatePetForm(@PathVariable Long petId, @PathVariable("ownerId") Long ownerId, Owner owner,
                                       @Validated Pet pet, BindingResult result){
        // validate the input data
        if(StringUtils.hasLength(pet.getName())) {
            if (!pet.isNew() && owner.getPets().contains(pet)) {
                Pet foundPet = owner.getPets().stream()
                        .filter(pet1 -> pet1.getName().equals(pet.getName()))
                        .findFirst().orElse(null);
                if (foundPet!=null) {
                    result.rejectValue("name", "duplicate", "already used for other pet for this owner");
                }
            }
        }else {
            result.rejectValue("name", "null", "name of pet cannot be empty");
        }

        // update the pet information in database
        // when apply the hibernate db, data store in db in sql style,
        // all the java model only created after apply CrudRepository to retrieve
        // data from database; or created before apply crudRepository to store data to database
        // Therefore, there is no need to update the pet set in owner model.
        // Instead of it, only to maintain the relationship between pet and owner in hibernate db.

        pet.setOwner(owner);
        if(result.hasErrors()){
            return CREATE_OR_UPDATE_PET_FROM;
        }else{
            petService.save(pet);
            return "redirect:/owners/" + ownerId;
        }
    }


}
