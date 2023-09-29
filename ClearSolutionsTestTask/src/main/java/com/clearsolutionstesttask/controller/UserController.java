package com.clearsolutionstesttask.controller;

import com.clearsolutionstesttask.model.User;
import com.clearsolutionstesttask.model.dto.DateRangeDTO;
import com.clearsolutionstesttask.model.dto.UserRegistrationDTO;
import com.clearsolutionstesttask.model.dto.UserResponseDTO;
import com.clearsolutionstesttask.model.dto.UserUpdateDTO;
import com.clearsolutionstesttask.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@Valid @RequestBody UserRegistrationDTO userDTO) {
        User saved = userService.addNewUser(userDTO);
        if (saved != null)
            return ResponseEntity.ok("User is valid and saved successfully");
        else
            return ResponseEntity.ok("User already exist");
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDTO newUser) {
        userService.updateUser(newUser);
        return ResponseEntity.ok("User was successfully updated");
    }

    @RequestMapping(value = "/users/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseDTO>> searchUsersByBirthDateRange(@Valid @RequestBody DateRangeDTO dateRange) {
        return ResponseEntity.ok(userService.getUsersByBirthDateRange(dateRange));
    }

    @RequestMapping(value = "/users/{email}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUserWithGivenEmail(@PathVariable String email) {
        userService.deleteUserWithEmail(email);
        return ResponseEntity.ok("User with given email was deleted");
    }
}
