package com.example.vehicle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String model;
    private String type;
    private String plate;
    @Column(name = "\"year\"")
    private String year;
    private String mileage;
    private String fuel;
    private String transmission;
    private String color;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)  // Link to the User entity
    private User user;
}
