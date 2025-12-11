package uz.abduraxim.speechclerk.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.abduraxim.speechclerk.dto.LoginDto;
import uz.abduraxim.speechclerk.dto.ResponseDto;
import uz.abduraxim.speechclerk.mapper.UserImpMapper;
import uz.abduraxim.speechclerk.model.UserImp;
import uz.abduraxim.speechclerk.repository.UserImpRepository;
import uz.abduraxim.speechclerk.service.jwtService.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserImpRepository userImpRepository;

    private final UserImpMapper mapper;

    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userImpRepository.existsByEmail(username)) {
            return userImpRepository.findUserImpByEmail(username);
        }
        throw new UsernameNotFoundException("Invalid username or password");
    }

    public ResponseDto login(LoginDto dto) {

        if (userImpRepository.existsByEmail(dto.getEmail())) {

            UserImp user = userImpRepository.findUserImpByEmail(dto.getEmail());

            if (mapper.checkPassword(dto.getPassword(), user.getPassword())) {

                return ResponseDto.builder()
                        .name(user.getName())
                        .email(user.getEmail())
                        .token(jwtUtil.encode(user.getUsername(), user.getAuthorities()))
                        .count(user.getCount())
                        .build();
            }
            throw new DataIntegrityViolationException("Invalid username or password");
        }
        throw new DataIntegrityViolationException("Invalid username or password");
    }
}