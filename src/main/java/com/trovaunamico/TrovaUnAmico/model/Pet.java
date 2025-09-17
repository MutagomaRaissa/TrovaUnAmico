package com.trovaunamico.TrovaUnAmico.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.ResponseEntity;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @Column(name = "name")
    private String name;

    @Column(name = "breed")
    private String breed;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private int age;

    @Column(name = "weight")
    private double weight;

    @Column(name = "location")
    private String location;

    @Column(name = "color")
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Column(name = "image_url")
    private String imageUrl;

    public enum Category { DOG, CAT, OTHER }
}
