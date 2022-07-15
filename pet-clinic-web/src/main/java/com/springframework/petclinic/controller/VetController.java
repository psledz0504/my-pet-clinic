package com.springframework.petclinic.controller;

import com.springframework.petclinic.model.Vet;
import com.springframework.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@RequestMapping("/vets")
@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @RequestMapping({"/index", "/index.html"})
    public String listOfVets(Model model){
        model.addAttribute("vets", vetService.findAll());
        return "vets/index";
    }

    @RequestMapping("")
    public @ResponseBody Set<Vet> listOfVets(){
        return vetService.findAll();
    }
}
