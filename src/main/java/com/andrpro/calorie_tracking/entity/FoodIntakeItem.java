package com.andrpro.calorie_tracking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "food_intake_items")
public class FoodIntakeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private NutritionalFood food;

    private float quantity;

    @ManyToOne
    @JoinColumn(name = "food_intake_id", nullable = false)
    private FoodIntake foodIntake;

}
