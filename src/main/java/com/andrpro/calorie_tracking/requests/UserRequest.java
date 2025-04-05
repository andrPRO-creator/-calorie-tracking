package com.andrpro.calorie_tracking.requests;


import com.andrpro.calorie_tracking.entity.Target;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String name;
    private String email;
    private int age;
    private float weight;
    private float height;
    private Target target;
}
