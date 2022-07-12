package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.Owner;
import com.springframework.petclinic.service.CrudService;
import com.springframework.petclinic.service.OwnerService;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService{
    @Override
    public Owner findByLastName(String lastName) {
        return null;
    }
}
