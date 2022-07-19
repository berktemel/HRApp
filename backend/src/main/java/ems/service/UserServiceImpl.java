package ems.service;

import ems.module.User;
import ems.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) throws NoSuchAlgorithmException {
        String hashed = hashPassword(user.getPassword());
        user.setPassword(hashed);
        return userRepository.save(user);
    }
    @Override
    public void save(User user) {
        userRepository.save(user);
    }
    @Override
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(password.getBytes());
        byte[] digest = md5.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User with id " + id + " doesnt exist.");
        }
    }
    @Override
    public boolean isEmpty() {
        return userRepository.count() == 0;
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public void deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with id " + id + "doesn't exist.");
        }
    }

    @Override
    public User findByResetPwToken(String hash) {
        return userRepository.findByResetPwToken(hash);
    }
}
