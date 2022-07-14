package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.Owner;
import com.springframework.petclinic.service.PetService;
import com.springframework.petclinic.service.PetTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

@ActiveProfiles("map")
@ExtendWith(MockitoExtension.class)
class OwnerMapServiceTest {

    @Mock
    private PetTypeService petTypeService;

    @Mock
    private PetService petService;

    @InjectMocks
    private OwnerMapService ownerMapService;

    private final Long ownerId = 1L;
    private final String lastName = "Smith";

    @BeforeEach
    void setUp() {
        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();
        Assertions.assertEquals(1, ownerSet.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);
        Assertions.assertEquals(ownerId, owner.getId());
    }

    @Test
    void saveProvidedId() {
        Long id = 2L;
        Owner owner2 = Owner.builder().id(id).build();
        Owner savedOwner = ownerMapService.save(owner2);
        Assertions.assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNotProvidedId(){
        Owner owner2 = Owner.builder().build();
        Owner savedOwner = ownerMapService.save(owner2);
        Assertions.assertNotNull(savedOwner);
        Assertions.assertNotNull(savedOwner.getId());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);
        Assertions.assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));
        Assertions.assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerMapService.findByLastName(lastName);
        Assertions.assertNotNull(owner);
        Assertions.assertEquals(lastName, owner.getLastName());
    }

    @Test
    void findByLastNameNotFound() {
        Owner owner = ownerMapService.findByLastName("not");
        Assertions.assertNull(owner);
    }
}