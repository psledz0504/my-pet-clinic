package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.Speciality;
import com.springframework.petclinic.service.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class SpecialityMapService extends AbstractMapService<Speciality, Long> implements SpecialityService {
}
