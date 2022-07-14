package com.springframework.petclinic.service.springdatajpa;

import com.springframework.petclinic.model.Owner;
import com.springframework.petclinic.repository.OwnerRepository;
import com.springframework.petclinic.repository.PetRepository;
import com.springframework.petclinic.repository.PetTypeRepository;
import com.springframework.petclinic.service.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    final String LAST_NAME = "Smith";
    final Long OWNER_ID = 1L;

    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(anyString())).thenReturn(returnOwner);

        Owner ownerFind = ownerSDJpaService.findByLastName(LAST_NAME);
        assertNotNull(ownerFind);
        assertEquals(LAST_NAME, ownerFind.getLastName());
        verify(ownerRepository).findByLastName(anyString());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(Owner.builder().id(1L).build());
        ownerSet.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(ownerSet);

        assertEquals(2, ownerSDJpaService.findAll().size());
        verify(ownerRepository).findAll();
    }

    @Test
    void findById() {
        when(ownerRepository.findById(OWNER_ID)).thenReturn(Optional.ofNullable(returnOwner));

        Owner owner1 = ownerSDJpaService.findById(OWNER_ID);

        assertNotNull(owner1);
        assertEquals(OWNER_ID, owner1.getId());
        verify(ownerRepository).findById(OWNER_ID);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(OWNER_ID)).thenReturn(Optional.empty());

        Owner owner1 = ownerSDJpaService.findById(OWNER_ID);

        assertNull(owner1);
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(ownerToSave);

        Owner savedOwner = ownerSDJpaService.save(ownerToSave);
        assertNotNull(savedOwner);
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(returnOwner);
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(1L);
        verify(ownerRepository).deleteById(anyLong());
    }
}