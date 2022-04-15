package com.ejder.socialapp.repository;

import com.ejder.socialapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User getUserByUsername(String username);
    User getUserByEmail(String email);
}
