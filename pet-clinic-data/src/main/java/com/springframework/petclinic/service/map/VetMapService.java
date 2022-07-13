package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.Speciality;
import com.springframework.petclinic.model.Vet;
import com.springframework.petclinic.service.SpecialityService;
import com.springframework.petclinic.service.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {
    private final SpecialityService specialityService;

    public VetMapService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Vet save(Vet vet) {
        if(vet.getSpecialities().size() > 0){
            vet.getSpecialities().forEach(speciality -> {
                //we need to save new speciality
                if(speciality.getId() == null){
                    Speciality savedSpeciality = specialityService.save(speciality);
                    speciality.setId(savedSpeciality.getId());
                }
            });
        }
        return super.save(vet);
    }
}
