package com.weater.repos;

import com.weater.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String name);

    User findByActivationCode(String code);
}
