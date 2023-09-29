package com.clearsolutionstesttask.repository;

import com.clearsolutionstesttask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    List<User> findByBirthDateBetween(LocalDate from, LocalDate to);

    void deleteByEmail(String email);
}
