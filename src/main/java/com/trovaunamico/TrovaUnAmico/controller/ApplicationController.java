// src/main/java/com/trovaunamico/TrovaUnAmico/controller/ApplicationController.java
package com.trovaunamico.TrovaUnAmico.controller;

import com.trovaunamico.TrovaUnAmico.model.Application;
import com.trovaunamico.TrovaUnAmico.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<Application> getAllApplications() {
        logger.info("Get request for all applications");
        return applicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id){
        logger.info("Get request for application by id {}", id);
        Application application = applicationService.getApplicationById(id);
        return ResponseEntity.ok(application);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicationById(@PathVariable Long id){
        logger.info("Delete request for application by id {}", id);
        applicationService.deleteApplicationById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplicationById(@PathVariable Long id, @RequestBody Application application) {
        logger.info("Put request for application by id {}", id);
        Application updatedApplication = applicationService.updateApplicationById(id, application);
        return ResponseEntity.ok(updatedApplication);
    }

    @PostMapping("/pet/{petId}")
    public ResponseEntity<Application> saveApplication(
            @PathVariable Long petId,
            @RequestBody Application application,
            @AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) return ResponseEntity.status(401).build();
        String email = principal.getAttribute("email");
        application.setEmail(email);
        Application newApplication = applicationService.saveApplication(petId, application);
        return ResponseEntity.ok(newApplication);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Application>> getApplicationsByPetId(@PathVariable Long petId) {
        logger.info("Fetching applications for petId {}", petId);
        List<Application> applications = applicationService.getApplicationsByPetId(petId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Application>> getApplicationsByUserEmail(@PathVariable String email) {
        logger.info("Fetching applications for user {}", email);
        List<Application> applications = applicationService.getApplicationsByUserEmail(email);
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam Application.ApplicationStatus status) {
        logger.info("Request to update status of application {} to {}", id, status);
        Application updatedApplication = applicationService.updateApplicationStatus(id, status);
        return ResponseEntity.ok(updatedApplication);
    }

    @GetMapping("/my")
    public ResponseEntity<List<Application>> getMyApplications(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) return ResponseEntity.status(401).build();
        String email = principal.getAttribute("email");
        List<Application> apps = applicationService.getApplicationsByUserEmail(email);
        return ResponseEntity.ok(apps);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(principal.getAttributes());
    }
}
//remember ko u have to write which api are front end others not
