package com.springframework.petclinic.controller;

import com.springframework.petclinic.model.Owner;
import com.springframework.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listOfOwners(Model model){
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

    @RequestMapping("/{id}")
    public String ownerDetails(Model model, @PathVariable Long id){
        model.addAttribute("owner", ownerService.findById(id));
        return "owners/ownerDetails";
    }



    @RequestMapping({"/find"})
    public String findOwners(Model model){
        model.addAttribute("owner", new Owner());
        return "owners/findOwners";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String processFindForm(Owner owner, BindingResult result, Model model) {

        // allow parameterless GET request for /owners to return all records
        if (owner.getLastName() == null) {
            owner.setLastName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        List<Owner> ownersResults = ownerService.findAllByLastNameContainingIgnoreCase(owner.getLastName());
        if (ownersResults.isEmpty()) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        }
        else if (ownersResults.size() == 1) {
            // 1 owner found
            owner = ownersResults.iterator().next();
            return "redirect:/owners/" + owner.getId();
        }
        else {
            // multiple owners found
            model.addAttribute("owners", ownersResults);
            return "owners/index";
        }
    }

    @RequestMapping( "/new")
    public String initCreateForm(Model model){
        model.addAttribute("owner", new Owner());
        return "owners/createOrUpdateOwnerForm";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreateForm(@Validated Owner owner, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "owners/createOrUpdateOwnerForm";
        }else{
            return "redirect:/owners/" + ownerService.save(owner).getId();
        }
    }

    @RequestMapping("/{id}/edit")
    public String initUpdateOwnerForm(@PathVariable("id") Long id, Model model) {
        Owner owner = ownerService.findById(id);
        model.addAttribute(owner);
        return "owners/createOrUpdateOwnerForm";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String processUpdateOwnerForm(@Validated Owner owner, BindingResult result,
                                         @PathVariable("id") Long id) {
        if (result.hasErrors()) {
            return "owners/createOrUpdateOwnerForm";
        }
        else {
            owner.setId(id);
            return "redirect:/owners/" + ownerService.save(owner).getId();
        }
    }
}
