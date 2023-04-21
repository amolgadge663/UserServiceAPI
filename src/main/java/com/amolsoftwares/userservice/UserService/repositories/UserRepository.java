package com.amolsoftwares.userservice.UserService.repositories;

import com.amolsoftwares.userservice.UserService.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
