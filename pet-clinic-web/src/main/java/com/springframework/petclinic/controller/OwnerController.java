package com.springframework.petclinic.controller;

import com.springframework.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners")
@Controller
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listOfOwners(Model model){
        ownerService.findAll().forEach(x-> System.out.println(x));
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
        return "notimplemented";
    }
}
