package com.trovaunamico.TrovaUnAmico.repository;

import com.trovaunamico.TrovaUnAmico.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByCategory(Pet.Category category);

}
