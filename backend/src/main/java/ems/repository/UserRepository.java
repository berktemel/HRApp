package ems.repository;

import ems.module.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByResetPwToken(String resetPwToken);
}
