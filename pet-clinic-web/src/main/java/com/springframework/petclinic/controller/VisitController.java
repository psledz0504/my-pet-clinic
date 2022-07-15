package com.springframework.petclinic.controller;

import com.springframework.petclinic.model.Owner;
import com.springframework.petclinic.model.Pet;
import com.springframework.petclinic.model.Visit;
import com.springframework.petclinic.service.OwnerService;
import com.springframework.petclinic.service.PetService;
import com.springframework.petclinic.service.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@Controller
public class VisitController {
    private final OwnerService ownerService;
    private final PetService petService;
    private final VisitService visitService;

    public VisitController(OwnerService ownerService, PetService petService, VisitService visitService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.visitService = visitService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    /**
     * Called before each and every @RequestMapping annotated method. 2 goals: - Make sure
     * we always have fresh data - Since we do not use the session scope, make sure that
     * Pet object always has an id (Even though id is not part of the form fields)
     * @param petId
     * @return Pet
     */
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("ownerId") Long ownerId, @PathVariable("petId") Long petId,
                                  Map<String, Object> model) {
        Owner owner = ownerService.findById(ownerId);
        Pet pet = owner.getPets().stream().filter(pet1 -> Objects.equals(pet1.getId(), petId)).findFirst().orElse(null);
        if(pet != null){
            model.put("pet", pet);
            model.put("owner", owner);
            Visit visit = new Visit();
            pet.getVisits().add(visit);
            return visit;
        }
        return null;
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
    // called
    @RequestMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
        return "pets/createOrUpdateVisitForm";
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is
    // called
    @RequestMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new", method = RequestMethod.POST)
    public String processNewVisitForm(@ModelAttribute Owner owner, @PathVariable Long petId, @Validated Visit visit,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        }
        else {
            visit.setPet(petService.findById(petId));
            visitService.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }

}
