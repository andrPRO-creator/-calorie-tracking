package com.andrpro.calorie_tracking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "food_intake")
public class FoodIntake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private Meal meal;
    @OneToMany(mappedBy = "foodIntake", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodIntakeItem> items = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User users;
    @Column
    private LocalDate date;
}
