package com.trovaunamico;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAsync
public class TrovaUnAmicoApplication {
private static final Logger logger = LoggerFactory.getLogger(TrovaUnAmicoApplication.class);
	public static void main(String[] args) {

        SpringApplication.run(TrovaUnAmicoApplication.class, args);
        logger.info("Application started successfully");

	}
    @Component
    public class EnvTest {
        @Value("${SPRING_SENDGRID_API_KEY}")
        private String sendGridApiKey;

        @PostConstruct
        public void printKey() {
            System.out.println("SendGrid API Key: " + sendGridApiKey);
        }
    }
//    @Bean
//    public CommandLineRunner init(PetRepository petRepository, ApplicationRepository applicationRepository) {
//        return args -> {
//            logger.info("Initialising Pets");
//            Pet dog = Pet.builder()
//                    .name("Peggy")
//                    .breed("Corgi")
//                    .age(3)
//                    .gender("Female")
//                    .weight(25.0)
//                    .color("Brown")
//                    .location("Turin")
//                    .category(Pet.Category.DOG)
//                    .build();
//
//            Pet cat = Pet.builder()
//                    .name("Whiskers")
//                    .breed("Siamese")
//                    .age(2)
//                    .gender("Female")
//                    .weight(5.0)
//                    .color("White")
//                    .location("Turin")
//                    .category(Pet.Category.CAT)
//                    .build();
//
//            petRepository.saveAll(List.of(dog, cat));
//            logger.info("Pets saved: {}", List.of(dog.getName(), cat.getName()));
//
//            // Create applications
//            Application app1 = Application.builder()
//                    .firstName("Paolo")
//                    .lastName("Rossi")
//                    .email("paolo@gmail.com")
//                    .phoneNumber("1234567890")
//                    .description("I am good with pets")
//                    .city("Turin")
//                    .createdAt(LocalDateTime.now())
//                    .pet(dog)
//                    .build();
//
//            Application app2 = Application.builder()
//                    .firstName("John")
//                    .lastName("Doe")
//                    .email("john@gmail.com")
//                    .phoneNumber("0987654321")
//                    .description("I love pets")
//                    .city("Berlin")
//                    .createdAt(LocalDateTime.now())
//                    .pet(cat)
//                    .build();
//
//            applicationRepository.saveAll(List.of(app1, app2));
//            logger.info("Applications saved: {}", List.of(app1.getFirstName(), app2.getFirstName()));
//
//        };
    }


