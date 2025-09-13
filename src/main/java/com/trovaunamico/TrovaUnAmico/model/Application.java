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
    private Long applicationId;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String city;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    private LocalDateTime createdAt;

    @Column(columnDefinition = "varchar(20) default 'NEW'")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.NEW;

    public enum ApplicationStatus {
        NEW,
        IN_REVIEW,
        ACCEPTED

    }
}
