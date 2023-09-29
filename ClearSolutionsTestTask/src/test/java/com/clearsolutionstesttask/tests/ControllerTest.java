package com.clearsolutionstesttask.tests;

import com.clearsolutionstesttask.controller.UserController;
import com.clearsolutionstesttask.model.User;
import com.clearsolutionstesttask.model.dto.DateRangeDTO;
import com.clearsolutionstesttask.model.dto.UserRegistrationDTO;
import com.clearsolutionstesttask.model.dto.UserResponseDTO;
import com.clearsolutionstesttask.model.dto.UserUpdateDTO;
import com.clearsolutionstesttask.service.UserService;
import com.clearsolutionstesttask.service.mapper.UserMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.clearsolutionstesttask.utility.TestUtility.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService service;

    @Before
    public void init() {
        initMocks(this);
    }

    @BeforeEach
    void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addUserTest() throws Exception {
        User expected = user4();

        when(service.addNewUser(any(UserRegistrationDTO.class))).thenReturn(expected);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("User is valid and saved successfully"));
    }

    @Test
    public void addExistingUserTest() throws Exception {
        User expected = user4();

        when(service.addNewUser(UserMapper.INSTANCE.userToUserRegistrationDTO(expected))).thenReturn(null);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(expected)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("User already exist"));
    }

    @Test
    public void addUserWithInvalidEmailTest() throws Exception {
        User expected = user4();
        expected.setEmail("zelensky.v.gmail.com");

        when(service.addNewUser(UserMapper.INSTANCE.userToUserRegistrationDTO(expected))).thenReturn(expected);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(expected)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllUsersTest() throws Exception {
        List<UserResponseDTO> expected = Stream.of(user1(), user2(), user3(), user4()).map(UserMapper.INSTANCE::userToUserResponseDTO).toList();

        when(service.getAllUsers()).thenReturn(expected);

        MvcResult result = mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<UserResponseDTO> actual = fromJson(content);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void updateUserTest() throws Exception {
        UserUpdateDTO userUpdateDTO = updatedUser3();
        userUpdateDTO.setPhoneNumber("+3809708719388");
        userUpdateDTO.setAddress("Kyiv");
        User updatedUser = user3();
        updatedUser.setPhoneNumber("+3809708719388");
        updatedUser.setAddress("Kyiv");

        when(service.updateUser(userUpdateDTO)).thenReturn(updatedUser);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userUpdateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("User was successfully updated"));
    }

    @Test
    public void searchUsersByBirthDateRangeTest() throws Exception {
        LocalDate from = LocalDate.of(2001, 10, 12);
        LocalDate to = LocalDate.of(2002, 5, 25);

        DateRangeDTO dateRangeDTO = new DateRangeDTO();
        dateRangeDTO.setFrom(from);
        dateRangeDTO.setTo(to);

        List<UserResponseDTO> expected = Stream.of(user3(), user4()).map(UserMapper.INSTANCE::userToUserResponseDTO).toList();

        when(service.getUsersByBirthDateRange(any(dateRangeDTO.getClass()))).thenReturn(expected);

        MvcResult mvcResult = mockMvc.perform(get("/users/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dateRangeDTO.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        System.out.println(content);
        List<UserResponseDTO> actual = fromJson(content);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void searchUsersByWrongBirthDateRangeTest() throws Exception {
        LocalDate from = LocalDate.of(2002, 10, 12);
        LocalDate to = LocalDate.of(2002, 5, 25);

        DateRangeDTO dateRangeDTO = new DateRangeDTO();
        dateRangeDTO.setFrom(from);
        dateRangeDTO.setTo(to);

        when(service.getUsersByBirthDateRange(any(dateRangeDTO.getClass()))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/users/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dateRangeDTO.toString()))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteUserTest() throws Exception {
        doNothing().when(service).deleteUserWithEmail(user1().getEmail());

        mockMvc.perform(delete("/users/{email}", user1().getEmail()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("User with given email was deleted"));
    }

    private String toJson(User expected) {
        JSONObject obj = new JSONObject();

        obj.put("ID", expected.getID());
        obj.put("email", expected.getEmail());
        obj.put("firstName", expected.getFirstName());
        obj.put("lastName", expected.getLastName());
        obj.put("birthDate", "" + expected.getBirthDate());
        obj.put("address", expected.getAddress());
        obj.put("phoneNumber", expected.getPhoneNumber());

        return obj.toJSONString();
    }

    private String toJson(UserUpdateDTO expected) {
        JSONObject obj = new JSONObject();

        obj.put("ID", expected.getID());
        obj.put("email", expected.getEmail());
        obj.put("firstName", expected.getFirstName());
        obj.put("lastName", expected.getLastName());
        obj.put("birthDate", "" + expected.getBirthDate());
        obj.put("address", expected.getAddress());
        obj.put("phoneNumber", expected.getPhoneNumber());

        return obj.toJSONString();
    }

    private List<UserResponseDTO> fromJson(String content) throws ParseException {
        JSONArray array = (JSONArray) new JSONParser().parse(content);

        List<UserResponseDTO> list = new ArrayList<>();
        UserResponseDTO user;

        for (Object o : array) {
            JSONObject jo = (JSONObject) o;
            user = new UserResponseDTO();

            user.setEmail((String) jo.get("email"));
            user.setFirstName((String) jo.get("firstName"));
            user.setLastName((String) jo.get("lastName"));
            user.setBirthDate(LocalDate.parse((String) jo.get("birthDate")));
            user.setAddress((String) jo.get("address"));
            user.setPhoneNumber((String) jo.get("phoneNumber"));

            list.add(user);
        }

        return list;
    }
}
