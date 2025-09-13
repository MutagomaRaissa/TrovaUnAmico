package com.trovaunamico.TrovaUnAmico.service;

import com.trovaunamico.TrovaUnAmico.model.Application;
import com.trovaunamico.TrovaUnAmico.model.Pet;
import com.trovaunamico.TrovaUnAmico.repository.ApplicationRepository;
import com.trovaunamico.TrovaUnAmico.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// create, read, update, delete
@Service
public class ApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationRepository applicationRepository;
    private final PetRepository petRepository;
    private final EmailService emailService;



    public ApplicationService(ApplicationRepository applicationRepository, PetRepository petRepository, EmailService emailService) {
        this.applicationRepository = applicationRepository;
        this.petRepository = petRepository;
        this.emailService = emailService;
    }

//to save application, first find by id  if the pet exists, then save the pet in the app, send email to confirm application  was received

    public Application saveApplication(Long petId, Application application) {
        logger.info("Saving new application for petId: {}", petId);
//finding pet
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> {
                    logger.error("Pet not found with id {}", petId);
                    return new RuntimeException("Pet not found");
                });
        logger.info("Pet found with id: {}", petId);
        application.setPet(pet);
        application.setCreatedAt(LocalDateTime.now());
        application.setStatus(Application.ApplicationStatus.NEW);
        Application newApplication = applicationRepository.save(application);

        try {
            sendConfirmationEmail(newApplication);
        } catch (Exception e) {
            logger.error("Failed to send confirmation email  for application {}: {}", newApplication.getApplicationId(), e.getMessage());
        }

    logger.info("Application {} saved successfully for pet {}", newApplication.getApplicationId(), petId);
    return newApplication;
}




    // Update application: find application, then set new values.
    //send email here as well?

    public Application updateApplicationById(Long applicationId, Application application) {

        logger.info("Updating application with id {}", applicationId);
        Application existingApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> {
                    logger.error("Application not found with id {}", applicationId);
                    return new RuntimeException("Application not found");
                });

        existingApplication.setFirstName(application.getFirstName());
        existingApplication.setLastName(application.getLastName());
        existingApplication.setEmail(application.getEmail());
        existingApplication.setPhoneNumber(application.getPhoneNumber());
        existingApplication.setCity(application.getCity());
        existingApplication.setDescription(application.getDescription());

//update on if the pet is valid

        if (application.getPet() != null && application.getPet().getPetId() != null) {
            Pet pet = petRepository.findById(application.getPet().getPetId())
                    .orElseThrow(() -> new RuntimeException("Pet not found with id: " + application.getPet().getPetId()));
            existingApplication.setPet(pet);
            logger.info("Updated pet for application {} to pet {}", applicationId, pet.getPetId());
        } else {
            logger.info("Keeping existing pet for application {}", applicationId);
        }

        Application updatedApplication = applicationRepository.save(existingApplication);

        try {
            sendConfirmationEmail(updatedApplication);
        } catch (Exception e) {
            logger.error("Failed to send thank-you email for updated application {}: {}", updatedApplication.getApplicationId(), e.getMessage());
        }
        logger.info("Application {} updated successfully", updatedApplication.getApplicationId());

        return updatedApplication;
    }


    public List<Application> getAllApplications() {
        logger.info("Fetching all applications");
        return applicationRepository.findAll();
    }


    public Application getApplicationById(Long applicationId) {
        logger.info("Getting application by id: " + applicationId);

        return applicationRepository.findById(applicationId).orElseThrow(() -> {
            logger.error("Application not found for id: " + applicationId);
            return new RuntimeException("Application not found");
        });
    }


    public void deleteApplicationById(Long applicationId) {
        logger.info("Deleting application by id: " + applicationId);

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> {
                    logger.error("Application to be deleted not found: " + applicationId);
                    return new RuntimeException("Application not found");
                });

        applicationRepository.delete(application);
    }




    public void sendConfirmationEmail(Application application) {
        logger.info("Submitting application" );
        String Subject="Thank you for your application!";

        String Body="Hi "+application.getFirstName()+",\n\n"+ "Your application for pet " + application.getPet().getName()
                + " has been received.We will get back to you as soon as possible \n\n"+
                "Best Regards, \nTrovaUnAmico Team";

        emailService.sendEmail(application.getEmail(),  Subject, Body);

   logger.info("Thank you email sent to {}", application.getEmail());

}
    public List<Application> getApplicationsByUserEmail(String email) {
        logger.info("Fetching applications for user email: {}", email);
        return applicationRepository.findByEmail(email);
    }


    public List<Application> getApplicationsByPetId(Long petId) {
        logger.info("Fetching applications for petId: {}", petId);
        return applicationRepository.findByPet_PetId(petId);
    }

    public Application updateApplicationStatus(Long applicationId, Application.ApplicationStatus newStatus) {
        logger.info("Updating status of application {} to {}", applicationId, newStatus);

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> {
                    logger.error("Application not found with id {}", applicationId);
                    return new RuntimeException("Application not found");
                });

        application.setStatus(newStatus);
        Application updatedApplication = applicationRepository.save(application);

        try {
            sendStatusUpdateEmail(updatedApplication);
        } catch (Exception e) {
            logger.error("Failed to send status update email for application {}: {}", applicationId, e.getMessage());
        }

        logger.info("Application {} status updated to {}", applicationId, newStatus);
        return updatedApplication;
    }
    public void sendStatusUpdateEmail(Application application) {
        String subject = "Your pet application status has changed!";
        String body = "Hi " + application.getFirstName() + ",\n\n" +
                "The status of your application for pet " + application.getPet().getName() +
                " is now: " + application.getStatus() + ".\n\n" +
                "Best regards,\nTrovaUnAmico Team";

        emailService.sendEmail(application.getEmail(), subject, body);
        logger.info("Status update email sent to {}", application.getEmail());
    }

}
