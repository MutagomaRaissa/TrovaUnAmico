package com.trovaunamico.TrovaUnAmico.repository;

import com.trovaunamico.TrovaUnAmico.model.Application;
import com.trovaunamico.TrovaUnAmico.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long>
{
    List<Application> findByEmail(String email);
    List<Application>  findByPet_PetId (Long petId);




}
