package uz.abduraxim.speechclerk.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import uz.abduraxim.speechclerk.dto.UserImpDto;
import uz.abduraxim.speechclerk.model.Role;
import uz.abduraxim.speechclerk.model.UserImp;

@Component
public class UserImpMapper {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserImp dtoToModel(UserImpDto dto) {
        return UserImp.builder()
                .role(Role.USER)
                .name(dto.getName())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .provider("MANUAL")
                .count(10) // for a new users
                .email(dto.getEmail())
                .build();
    }

    public Boolean checkPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
