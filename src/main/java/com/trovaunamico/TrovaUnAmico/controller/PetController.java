package com.trovaunamico.TrovaUnAmico.controller;

import ch.qos.logback.core.joran.spi.ElementSelector;
import com.trovaunamico.TrovaUnAmico.model.Pet;
import com.trovaunamico.TrovaUnAmico.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

private final  PetService petService;
private static final Logger logger = LoggerFactory.getLogger(PetController.class);

    public PetController(PetService petService) {

        this.petService = petService;
    }
//frontend user
    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
     logger.info("Get request for all pets");
     List<Pet> pets = petService.getAllPets();
     logger.info("Fetched all {} pets", pets.size());
        return ResponseEntity.ok(pets);
    }
 //frontend admin

    @GetMapping("/{id}")
     public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        logger.info("Getting pet by id {}", id);
        Pet pet= petService.getPetById(id);
      return ResponseEntity.ok(pet);
    }

   @GetMapping("/categories")
   public ResponseEntity<Pet.Category[]> getCategories() {
       return ResponseEntity.ok(Pet.Category.values());
    }

// frontend user

    @GetMapping("/category/{category}")
    public ResponseEntity <List<Pet>> getPetByCategory(@PathVariable Pet.Category category) {
      logger.info("Getting pets by category {}", category);
       List<Pet>pets=petService.getPetsByCategory(category);
       logger.info("Fetched {} pets", pets.size());
       return ResponseEntity.ok(pets);
    }
//frontend admin
    @PostMapping
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        logger.info("Adding new pet {}", pet);
        Pet createPet= petService.addPet(pet);
        logger.info("Created pet {}", createPet);
        return ResponseEntity.ok(createPet);
    }
//frontend admin

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long id, @RequestBody Pet pet) {
        logger.info("Updating pet {}", pet);
     Pet existingPet=petService.updatePet(id, pet);
     logger.info("Updated pet {}", existingPet);
     return ResponseEntity.ok(existingPet);
    }

//frontend admin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        logger.info("Deleting pet {}", id);
        petService.deletePet(id);
        logger.info("Deleted pet {}", id);
        return ResponseEntity.noContent().build();
    }
    //frontend user
    @GetMapping("/category/{category}/filter")
    public ResponseEntity<List<Pet>> getPetsByCategoryWithFilters(
            @PathVariable Pet.Category category,
            @RequestParam(required = false) String breed,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) String location
    ) {
        logger.info("Apply filters on a category {} of pets ", category);
        List<Pet> pets = petService.applyFiltersOnCategoryPets(category, breed, gender, age, location);
        logger.info("Returning {} pets after filtering", pets.size());
        return ResponseEntity.ok(pets);
    }
}
