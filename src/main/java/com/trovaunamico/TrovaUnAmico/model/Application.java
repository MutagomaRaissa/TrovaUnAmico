package com.trovaunamico.TrovaUnAmico.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
//applicant chooses pet and then has to put their info(link TO pet needed, relationship is N:1)

@Entity
@Table(name = "applications")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "city")
    private String city;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(20) default 'NEW'")
    private ApplicationStatus status = ApplicationStatus.NEW;

    public enum ApplicationStatus {
        NEW,
        IN_REVIEW,
        ACCEPTED
    }
}

