package com.clearsolutionstesttask.tests;

import com.clearsolutionstesttask.model.User;
import com.clearsolutionstesttask.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static com.clearsolutionstesttask.utility.TestUtility.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryTest {
    @Autowired
    private UserRepository repository;

    @Before
    public void setUp() {
        List<User> users = List.of(user1(), user2(), user3(), user4());
        repository.saveAll(users);
    }

    @Order(1)
    @Test
    public void existsByEmailTest() {
        Assert.assertTrue(repository.existsByEmail(user1().getEmail()));
        Assert.assertTrue(repository.existsByEmail(user2().getEmail()));
        Assert.assertTrue(repository.existsByEmail(user3().getEmail()));
        Assert.assertTrue(repository.existsByEmail(user4().getEmail()));
    }

    @Order(2)
    @Test
    public void findByBirthDateTest() {
        LocalDate from = LocalDate.of(2001, 10, 12);
        LocalDate to = LocalDate.of(2002, 5, 25);
        List<User> expected = List.of(user3(), user4());

        List<User> actual = repository.findByBirthDateBetween(from, to);
        Assert.assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Order(3)
    @Test
    public void deleteByEmailTest1() {
        Assert.assertTrue(repository.existsByEmail(user2().getEmail()));
        repository.deleteByEmail(user2().getEmail());
        Assert.assertFalse(repository.existsByEmail(user2().getEmail()));
    }

    @Order(4)
    @Test
    public void deleteByEmailTest2() {
        Assert.assertTrue(repository.existsByEmail(user4().getEmail()));
        repository.deleteByEmail(user4().getEmail());
        Assert.assertFalse(repository.existsByEmail(user4().getEmail()));
    }
}
