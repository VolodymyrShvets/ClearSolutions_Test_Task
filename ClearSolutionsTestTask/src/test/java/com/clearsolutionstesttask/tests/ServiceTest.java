package com.clearsolutionstesttask.tests;

import com.clearsolutionstesttask.model.User;
import com.clearsolutionstesttask.model.dto.DateRangeDTO;
import com.clearsolutionstesttask.model.dto.UserResponseDTO;
import com.clearsolutionstesttask.model.dto.UserUpdateDTO;
import com.clearsolutionstesttask.model.exception.UserNotFoundException;
import com.clearsolutionstesttask.repository.UserRepository;
import com.clearsolutionstesttask.service.UserService;
import com.clearsolutionstesttask.service.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import jakarta.validation.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.clearsolutionstesttask.utility.TestUtility.*;
import static com.clearsolutionstesttask.utility.TestUtility.user4;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ServiceTest {
    @Mock
    private UserRepository repository;

    @Mock
    private Validator validator;

    @InjectMocks
    private UserService service;

    @Test
    public void addNewUserTest1() {
        User expected = user1();

        when(repository.existsByEmail(expected.getEmail())).thenReturn(false);
        when(repository.save(any(User.class))).thenReturn(expected);

        User actual = service.addNewUser(UserMapper.INSTANCE.userToUserRegistrationDTO(expected));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addNewUserTest2() {
        User expected = user3();

        when(repository.existsByEmail(expected.getEmail())).thenReturn(false);
        when(repository.save(any(User.class))).thenReturn(expected);

        User actual = service.addNewUser(UserMapper.INSTANCE.userToUserRegistrationDTO(expected));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllUsersTest() {
        List<User> listOfUsers = List.of(user1(), user2(), user3(), user4());
        when(repository.findAll()).thenReturn(listOfUsers);

        List<UserResponseDTO> expectedList = listOfUsers.stream().map(UserMapper.INSTANCE::userToUserResponseDTO).toList();

        List<UserResponseDTO> actual = service.getAllUsers();
        Assert.assertArrayEquals(expectedList.toArray(), actual.toArray());
    }

    @Test
    public void updateUserTest() {
        User savedUser = user3();
        UserUpdateDTO userUpdateDTO = updatedUser3();
        userUpdateDTO.setPhoneNumber("+3809708719388");
        userUpdateDTO.setAddress("Kyiv");
        User updatedUser = user3();
        updatedUser.setPhoneNumber("+3809708719388");
        updatedUser.setAddress("Kyiv");

        when(repository.findById(savedUser.getID())).thenReturn(Optional.of(savedUser));
        when(repository.save(updatedUser)).thenReturn(updatedUser);

        User actual = service.updateUser(userUpdateDTO);

        Assert.assertEquals(updatedUser, actual);
    }

    @Test
    public void updateWrongUserTest() {
        User savedUser = user3();
        UserUpdateDTO userUpdateDTO = updatedUser3();
        userUpdateDTO.setPhoneNumber("+3809708719388");
        userUpdateDTO.setAddress("Kyiv");
        User updatedUser = user3();
        updatedUser.setPhoneNumber("+3809708719388");
        updatedUser.setAddress("Kyiv");

        when(repository.findById(savedUser.getID() + 1)).thenReturn(Optional.of(savedUser));

        assertThrows(UserNotFoundException.class, () ->
                service.updateUser(userUpdateDTO));
    }

    @Test
    public void getUsersByBirthDateRangeTest() {
        LocalDate from = LocalDate.of(2001, 10, 12);
        LocalDate to = LocalDate.of(2002, 5, 25);
        DateRangeDTO dateRangeDTO = new DateRangeDTO();
        dateRangeDTO.setFrom(from);
        dateRangeDTO.setTo(to);

        List<User> expected = List.of(user3(), user4());

        when(repository.findByBirthDateBetween(from, to)).thenReturn(expected);

        List<UserResponseDTO> actual = service.getUsersByBirthDateRange(dateRangeDTO);
        Assert.assertArrayEquals(expected.stream().map(UserMapper.INSTANCE::userToUserResponseDTO).toArray(), actual.toArray());
    }

    @Test
    public void getUsersByWrongBirthDateRangeTest() {
        LocalDate from = LocalDate.of(2002, 10, 12);
        LocalDate to = LocalDate.of(2002, 5, 25);
        DateRangeDTO dateRangeDTO = new DateRangeDTO();
        dateRangeDTO.setFrom(from);
        dateRangeDTO.setTo(to);

        List<User> expected = List.of(user3(), user4());

        when(repository.findByBirthDateBetween(from, to)).thenReturn(expected);

        assertThrows(IllegalArgumentException.class, () ->
                service.getUsersByBirthDateRange(dateRangeDTO));
    }

    @Test
    public void deleteUserWithEmailTest() {
        User expected = user4();
        when(repository.existsByEmail(expected.getEmail())).thenReturn(true);

        service.deleteUserWithEmail(user4().getEmail());

        verify(repository, times(1)).deleteByEmail(user4().getEmail());
    }

    @Test
    public void deleteUserWithWrongEmailTest() {
        User expected = user4();
        //expected.setEmail("zelensky.v@ukr.net");
        when(repository.existsByEmail(expected.getEmail())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () ->
        {
            service.deleteUserWithEmail(user4().getEmail());
        });
    }
}
