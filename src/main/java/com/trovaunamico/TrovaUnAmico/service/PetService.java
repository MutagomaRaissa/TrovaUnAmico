package com.trovaunamico.TrovaUnAmico.service;


import com.trovaunamico.TrovaUnAmico.model.Pet;
import com.trovaunamico.TrovaUnAmico.repository.PetRepository;
import com.trovaunamico.TrovaUnAmico.model.Pet.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// create, read, update, delete

@Service
public class PetService {
    private static final Logger logger = LoggerFactory.getLogger(PetService.class);

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }


    public List<Pet> getAllPets() {
        logger.info("Getting all pets");
        return petRepository.findAll();
    }

    public Pet getPetById(Long id) {
        logger.info("Getting pet by id {}", id);
        return petRepository.findById(id)
                .orElseThrow(() -> {
                    logger.info("Pet not found with id {}", id);
                    return new RuntimeException("Pet not found with id " + id);
                });

    }

    public List<Pet> getPetsByCategory(Category category) {
        logger.info("Getting pets by category {}", category);
        List<Pet> pets = petRepository.findByCategory(category);

        if (pets.isEmpty()) {
            logger.warn("No pets found in category {}", category);
            return new ArrayList<>(); // return empty list instead of throwing exception
        }

        return pets;
    }

    public Pet addPet(Pet pet) {
        logger.info("Adding pet {}", pet.getName());
        Pet newPet = petRepository.save(pet);
        return petRepository.save(newPet);
    }

    public Pet updatePet(Long id, Pet pet) {
        logger.info("Updating pet with id {}", id);
        Pet existingPet = getPetById(id);

        if (existingPet == null) {
            logger.error("Pet not found with id {}", id);
            throw new RuntimeException("Pet not found with id " + id);
        }

        existingPet.setName(pet.getName());
        existingPet.setCategory(pet.getCategory());
        existingPet.setAge(pet.getAge());
        existingPet.setGender(pet.getGender());
        existingPet.setBreed(pet.getBreed());
        existingPet.setColor(pet.getColor());
        existingPet.setWeight(pet.getWeight());
        logger.info("Pet updated successfully with id {} ", id);
        return petRepository.save(existingPet);
    }

    public void deletePet(Long id) {
        logger.info("Deleting pet with id {}", id);
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Pet not found with id {}", id);
                    return new RuntimeException("Pet not found with id " + id);

                });
        logger.info("Pet to be deleted with id {} and name {}", id, pet.getName());
        petRepository.delete(pet);
        logger.info("Pet deleted successfully with id {}", id);
    }

    public List<Pet> applyFiltersOnCategoryPets(Pet.Category category, String breed, String gender, Integer age, String location) {
        logger.info("Fetching pets in category {} with filters - breed: {}, gender: {}, age: {}, location: {}",
                category, breed, gender, age, location);
        List<Pet> pets = getPetsByCategory(category);
        List<Pet> filteredPets = pets.stream()
                .filter(pet -> breed == null || pet.getBreed().equalsIgnoreCase(breed))
                .filter(pet -> gender == null || pet.getGender().equalsIgnoreCase(gender))
                .filter(pet -> age==null || pet.getAge() == age)
                .filter(pet -> location == null || pet.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
        return filteredPets;
    }


}
