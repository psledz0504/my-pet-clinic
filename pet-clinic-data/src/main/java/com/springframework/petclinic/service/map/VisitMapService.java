package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.Visit;
import com.springframework.petclinic.service.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {
}
