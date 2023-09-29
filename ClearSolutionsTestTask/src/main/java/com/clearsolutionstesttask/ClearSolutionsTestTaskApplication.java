package com.clearsolutionstesttask;

import com.clearsolutionstesttask.model.User;
import com.clearsolutionstesttask.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ClearSolutionsTestTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClearSolutionsTestTaskApplication.class, args);
    }

    //@Bean
    public CommandLineRunner run(UserRepository repository) {
        List<User> users = List.of(user1(), user2(), user3(), user4());

        return args -> {
            repository.saveAll(users);
        };
    }

    private User user1() {
        User user = new User();
        user.setEmail("s.bandera@ukr.net");
        user.setAddress("Staryi Uhryniv");
        user.setFirstName("Stepan");
        user.setLastName("Bandera");
        user.setPhoneNumber("");
        user.setBirthDate(LocalDate.of(1909, 1, 1));
		return user;
    }

    private User user2() {
        User user = new User();
        user.setEmail("vshvets295@gmail.com");
        user.setAddress("Kryvyi Rih");
        user.setFirstName("Volodymyr");
        user.setLastName("Shvets");
        user.setPhoneNumber("+380680282322");
        user.setBirthDate(LocalDate.of(2002, 6, 1));
        return user;
    }

    private User user3() {
        User user = new User();
        user.setEmail("noksamem@gmail.com");
        user.setAddress("Obroshyno");
        user.setFirstName("Bohdan");
        user.setLastName("Nanovskyi");
        user.setPhoneNumber("");
        user.setBirthDate(LocalDate.of(2002, 5, 19));
        return user;
    }

    private User user4() {
        User user = new User();
        user.setEmail("ivaniuk.anna.knm.2019@lpnu.ua");
        user.setAddress("Lviv");
        user.setFirstName("Anna");
        user.setLastName("Ivaniuk");
        user.setPhoneNumber("");
        user.setBirthDate(LocalDate.of(2002, 5, 16));
        return user;
    }
}
