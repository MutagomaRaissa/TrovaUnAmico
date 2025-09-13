package com.trovaunamico.TrovaUnAmico.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.ResponseEntity;


@Entity
@Table( name = "pets")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    private String name;
    private String breed;
    private String gender;
    private int age;
    private double weight;
    private String location;
    private String color;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String imageUrl;

    public enum Category { DOG, CAT, OTHER }


}
