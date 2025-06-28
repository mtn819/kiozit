package com.example.employeemanager.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.employeemanager.model.AppUser;

@Repository
public interface AppUserRepo extends CrudRepository<AppUser, Integer> {
    Optional<AppUser> findByEmail(String email);
}
