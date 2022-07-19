package ems.module;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String role = "user";
    private String token;
    private String resetPwToken;
    private boolean verified;
}
