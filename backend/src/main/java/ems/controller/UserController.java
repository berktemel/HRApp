package ems.controller;

import ems.module.Notification;
import ems.module.NotificationDto;
import ems.module.User;
import ems.module.UserDto;
import ems.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.jsonwebtoken.Jwts;

import javax.xml.bind.DatatypeConverter;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        try {
            if(userService.isEmpty())
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            List<UserDto> users = userService.findAll()
                    .stream().map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/users/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable("email") String email) {
        try {
            User user = userService.findByEmail(email);
            return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<UserDto> getUserByResetPwToken(@RequestParam String hash) {
        try {
            User user = userService.findByResetPwToken(hash);
            return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{email}/notifications")
    public ResponseEntity<List<NotificationDto>> getNotifications(@PathVariable("email") String email) {
        try {
            User user1 = userService.findByEmail(email);
            List<NotificationDto> notificationDtos = user1.getNotifications()
                    .stream().map(this::convertToDto)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(notificationDtos, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{email}/notifications/{id}")
    public ResponseEntity<HttpStatus> setNotificationAsRead(@PathVariable("email") String email, @PathVariable("id") Long id) {
        try {
            User user = userService.findByEmail(email);
            user.getNotificationById(id).setReadStatus(true);
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        try {
            User user = convertToEntity(userDto);
            userService.addUser(user);
            UserDto response = convertToDto(user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/login")
    public ResponseEntity<Boolean> checkUser(@RequestBody UserDto userdto) {
        try {
            User userHashed = userService.findByEmail(userdto.getEmail());
            if (userHashed == null) {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
            String hashedPw = userService.hashPassword(userdto.getPassword());
            if (hashedPw.equals(userHashed.getPassword())) {
                String token = getJWTToken(userHashed.getEmail());
                userHashed.setToken(token);
                userService.save(userHashed);
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/{email}/token")
    public ResponseEntity<Boolean> checkToken(@PathVariable("email") String email) {
        try {
            User user = userService.findByEmail(email);
            String key = "key";
            String base64key = DatatypeConverter.printBase64Binary(key.getBytes());
            byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64key);
            String str = Base64.getEncoder().encodeToString(secretBytes);
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(str))
                    .parseClaimsJws(user.getToken())
                    .getBody();
            if (new Date(System.currentTimeMillis()).after(claims.getExpiration())) {
                user.setToken(null);
                userService.save(user);
                return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            User user = userService.findByEmail(email);
            user.setToken(null);
            userService.save(user);
            return new ResponseEntity<>(false, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/")
    public ResponseEntity<UserDto> setPwToken(@RequestBody UserDto userDto) {
        try {
            User user = userService.findByEmail(userDto.getEmail());
            user.setResetPwToken(userDto.getResetPwToken());
            userService.save(user);
            return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto userDto) {
        try {
            User user = userService.findByEmail(userDto.getEmail());
            user.copy(userDto);
            String hashedPw = userService.hashPassword(userDto.getPassword());
            user.setPassword(hashedPw);
            userService.save(user);
            return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> expireToken(@PathVariable("id") Long id) {
        try {
            User user = userService.findById(id);
            user.setToken(null);
            user.setResetPwToken(null);
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String getJWTToken(String email) {
        String key = "key";
        String token = Jwts.builder().setId("secret").setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 3))
                .signWith(SignatureAlgorithm.HS512, key.getBytes()).compact();

        return token;
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{id}/verification")
    public ResponseEntity<UserDto> verifyUser(@PathVariable("id") Long id) {
        try {
            User user = userService.findById(id);
            user.setVerified(true);
            userService.save(user);
            return new ResponseEntity<>(convertToDto(user), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("[Error]:" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private NotificationDto convertToDto(Notification notification) {
        return modelMapper.map(notification, NotificationDto.class);
    }

    private Notification convertToEntity(NotificationDto notificationDto) {
        return modelMapper.map(notificationDto, Notification.class);
    }

}
