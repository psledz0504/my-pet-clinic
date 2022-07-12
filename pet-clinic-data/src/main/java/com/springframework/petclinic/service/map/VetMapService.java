package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.Vet;
import com.springframework.petclinic.service.VetService;
import org.springframework.stereotype.Service;

@Service
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {
}
