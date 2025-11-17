package uz.abduraxim.speechclerk.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {

    @Column(nullable = false, unique = true)
    @Email(message = "Email is invalid format")
    private String email;

    @Column(nullable = false)
    @Size(min = 5, max = 15)
    private String password;
}
