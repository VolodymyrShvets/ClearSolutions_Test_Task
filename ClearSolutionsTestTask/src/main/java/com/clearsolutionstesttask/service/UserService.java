package com.clearsolutionstesttask.service;

import com.clearsolutionstesttask.model.User;
import com.clearsolutionstesttask.model.dto.DateRangeDTO;
import com.clearsolutionstesttask.model.dto.UserRegistrationDTO;
import com.clearsolutionstesttask.model.dto.UserResponseDTO;
import com.clearsolutionstesttask.model.dto.UserUpdateDTO;
import com.clearsolutionstesttask.model.exception.UserNotFoundException;
import com.clearsolutionstesttask.repository.UserRepository;
import com.clearsolutionstesttask.service.mapper.UserMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Lazy
    private final Validator validator;

    public User addNewUser(UserRegistrationDTO userDTO) {
        log.info("Attempting to register new User with Email {}", userDTO.getEmail());

        User saved = null;
        if (!userRepository.existsByEmail(userDTO.getEmail())) {
            User receivedUser = UserMapper.INSTANCE.userRegistrationDTOToUser(userDTO);

            validateUser(receivedUser);

            saved = userRepository.save(receivedUser);
            log.info("New User with Email {} successfully registered", saved.getEmail());
        }
        return saved;
    }

    public List<UserResponseDTO> getAllUsers() {
        log.info("Retrieving all Users");

        return userRepository.findAll().stream().map(UserMapper.INSTANCE::userToUserResponseDTO).toList();
    }

    public User updateUser(UserUpdateDTO newUser) {
        log.info("Updating User with ID {}", newUser.getID());

        Optional<User> userOptional = userRepository.findById(newUser.getID());

        User savedUser = userOptional.orElseThrow(() -> new UserNotFoundException("User with given parameters not found"));

        savedUser = UserMapper.INSTANCE.populateUserWithPresentUserUpdateDTOFields(savedUser, newUser);
        validateUser(savedUser);

        User updatedUser = userRepository.save(savedUser);

        log.info("User with ID {} successfully updated", updatedUser.getID());
        return updatedUser;
    }

    public List<UserResponseDTO> getUsersByBirthDateRange(DateRangeDTO dateRange) {
        log.info("Retrieving Users by Birth Date from {} to {}", dateRange.getFrom(), dateRange.getTo());

        if (dateRange.getFrom().isAfter(dateRange.getTo()))
            throw new IllegalArgumentException("From date must be less than or equal to To date");

        return userRepository.findByBirthDateBetween(dateRange.getFrom(), dateRange.getTo()).stream().map(UserMapper.INSTANCE::userToUserResponseDTO).toList();
    }

    @Transactional
    public void deleteUserWithEmail(String email) {
        log.info("Attempting to delete User with Email {}", email);

        if (userRepository.existsByEmail(email)) {
            userRepository.deleteByEmail(email);

            log.info("User successfully deleted");
        } else {
            log.error("User with Email {} not found", email);

            throw new UserNotFoundException("User with given Email not found");
        }
    }

    private void validateUser(User user) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<User> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            log.error("Following errors occurred: {}", violations);
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
    }
}
