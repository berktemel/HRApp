package ems.service;

import ems.module.User;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public interface UserService {

    User addUser(User user) throws NoSuchAlgorithmException;

    void save(User user);

    String hashPassword(String password) throws NoSuchAlgorithmException;

    List<User> findAll();

    User findById(Long id);

    boolean isEmpty();

    User findByEmail(String email);

    void deleteById(Long id);

    User findByResetPwToken(String hash);
}
