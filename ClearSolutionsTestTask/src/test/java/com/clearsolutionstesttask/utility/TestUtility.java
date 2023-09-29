package com.clearsolutionstesttask.utility;

import com.clearsolutionstesttask.model.User;
import com.clearsolutionstesttask.model.dto.UserUpdateDTO;

import java.time.LocalDate;

public class TestUtility {
    public static User user1() {
        User user = new User();
        user.setID(1L);
        user.setEmail("s.bandera@ukr.net");
        user.setAddress("Staryi Uhryniv");
        user.setFirstName("Stepan");
        user.setLastName("Bandera");
        user.setPhoneNumber("");
        user.setBirthDate(LocalDate.of(1909, 1, 1));
        return user;
    }

    public static User user2() {
        User user = new User();
        user.setID(2L);
        user.setEmail("vshvets295@gmail.com");
        user.setAddress("Kryvyi Rih");
        user.setFirstName("Volodymyr");
        user.setLastName("Shvets");
        user.setPhoneNumber("+380680282322");
        user.setBirthDate(LocalDate.of(2002, 6, 1));
        return user;
    }

    public static User user3() {
        User user = new User();
        user.setID(3L);
        user.setEmail("johnson@gmail.com");
        user.setAddress("London");
        user.setFirstName("Boris");
        user.setLastName("Johnson");
        user.setPhoneNumber("");
        user.setBirthDate(LocalDate.of(2002, 5, 19));
        return user;
    }

    public static User user4() {
        User user = new User();
        user.setID(4L);
        user.setEmail("zelensky.v@gmail.com");
        user.setAddress("Lviv");
        user.setFirstName("Volodymyr");
        user.setLastName("Zelensky");
        user.setPhoneNumber("");
        user.setBirthDate(LocalDate.of(2002, 5, 16));
        return user;
    }

    public static UserUpdateDTO updatedUser3() {
        return
                new UserUpdateDTO(
                        3L,
                        "johnson@gmail.com",
                        "Boris",
                        "Johnson",
                        LocalDate.of(2002, 5, 19),
                        "London",
                        "");
    }
}
