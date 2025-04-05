package com.andrpro.calorie_tracking.services;

import com.andrpro.calorie_tracking.dto.AckDto;
import com.andrpro.calorie_tracking.dto.CalorieComplianceDto;
import com.andrpro.calorie_tracking.dto.ComplianceStatus;
import com.andrpro.calorie_tracking.entity.Target;
import com.andrpro.calorie_tracking.exception.BadRequestException;
import com.andrpro.calorie_tracking.exception.ResourceNotFoundException;
import com.andrpro.calorie_tracking.requests.UserRequest;
import com.andrpro.calorie_tracking.entity.User;
import com.andrpro.calorie_tracking.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private static final double LOSE = 0.85;
    private static final double KEEP = 1.0;
    private static final double GAIN = 1.25;

    //допустимые домены для email
    private static final Set<String> ALLOWED_DOMAINS = Set.of(
            "mail.ru",
            "yandex.ru",
            "gmail.com",
            "hotmail.com"
    );


    @Autowired
    UserRepository userRepository;

    @Autowired
    FoodIntakeService foodIntakeService;

    public User getUser(Long id) {
        return getUserOrThrowException(id);
    }

    public List<User> getAllUsers() { return userRepository.findAll();}

    public void createUser(UserRequest userRequest) {
        validateEmail(userRequest.getEmail());

        userRepository
                .findByEmail(userRequest.getEmail())
                .ifPresent(user -> {
                    throw new BadRequestException(String.format("User with email %s is already exists", userRequest.getEmail()));
                });


        User user = userRepository.saveAndFlush(
                User.builder()
                        .name(userRequest.getName())
                        .email(userRequest.getEmail())
                        .age(userRequest.getAge())
                        .weight(userRequest.getWeight())
                        .height(userRequest.getHeight())
                        .target(userRequest.getTarget())
                        .build()
        );
    }

    public int calculateDailyCalories(Long userId) {

        User user = getUserOrThrowException(userId);
        double bmr = calculateBMR(user);

        return (int) Math.round(bmr * getTargetMultiplier(user.getTarget()));
    }

    private double calculateBMR(User user) {
        return 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
    }

    private double getTargetMultiplier(Target target) {
        if (target == null) return KEEP;

        return switch (target) {
            case LOSE_WEIGHT -> LOSE;
            case KEEPING_FIT -> KEEP;
            case WEIGHT_GAIN -> GAIN;
        };
    }

    public CalorieComplianceDto getCaloriesCompliance(Long userId, LocalDate date) {
        getUserOrThrowException(userId);

        int consumed = foodIntakeService.getCaloriesPerDay(userId,date);
        int dailyNorm = calculateDailyCalories(userId);

        int deviation = (int) (dailyNorm * 0.1);
        int lowerBound = dailyNorm - deviation;
        int upperBound = dailyNorm + deviation;

        ComplianceStatus status;

        if (consumed < lowerBound) {
            status = ComplianceStatus.BELOW_NORM;
        } else if (consumed > upperBound) {
            status = ComplianceStatus.ABOVE_NORM;
        } else {
            status = ComplianceStatus.WITHIN_NORM;
        }

        return new CalorieComplianceDto(
                dailyNorm,
                consumed,
                status,
                Math.abs(consumed - dailyNorm)
        );


    }

    @Transactional
    public User editUser(Long userId, UserRequest request) {
        User user = getUserOrThrowException(userId);

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            validateEmail(request.getEmail());
            userRepository
                    .findByEmail(request.getEmail())
                    .ifPresent(u -> {
                        throw new BadRequestException(
                                String.format("Email %s already in use", request.getEmail()));
                    });
            user.setEmail(request.getEmail());
        }

        if (request.getAge() != 0) {
            user.setAge(request.getAge());
        }
        if (request.getWeight() != 0) {
            user.setWeight(request.getWeight());
        }
        if (request.getHeight() != 0) {
            user.setHeight(request.getHeight());
        }
        if (request.getTarget() != null) {
            user.setTarget(request.getTarget());
        }

        return userRepository.save(user);
    }


    public AckDto deleteUser(Long userId) {
        getUserOrThrowException(userId);

        userRepository.deleteById(userId);

        return AckDto.makeDefault(true);
    }

    public User getUserOrThrowException(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with id %s doesn't exist", userId)));
    }


    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new BadRequestException("Email cannot be empty");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BadRequestException("Invalid email format");
        }

        String domain = email.substring(email.indexOf('@') + 1).toLowerCase();

        if (!ALLOWED_DOMAINS.contains(domain)) {
            throw new BadRequestException(
                    "Email must end with: @mail.ru, @yandex.ru, @gmail.com or @hotmail.com"
            );
        }
    }


}
