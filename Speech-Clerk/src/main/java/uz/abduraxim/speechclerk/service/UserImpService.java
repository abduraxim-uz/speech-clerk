package uz.abduraxim.speechclerk.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.abduraxim.speechclerk.dto.ResponseDto;
import uz.abduraxim.speechclerk.dto.UserImpDto;
import uz.abduraxim.speechclerk.mapper.UserImpMapper;
import uz.abduraxim.speechclerk.model.UserImp;
import uz.abduraxim.speechclerk.repository.UserImpRepository;
import uz.abduraxim.speechclerk.restService.RestService;

import javax.naming.InsufficientResourcesException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserImpService {

    private final UserImpRepository userImpRepository;

    private final RestService restService;

    private final UserImpMapper mapper;

    public void register(UserImpDto dto) {

        if (userImpRepository.existsByEmail(dto.getEmail())) {
            throw new DataIntegrityViolationException("Email Already Exists");
        }

        try {
            userImpRepository.save(mapper.dtoToModel(dto));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Invalid username or password");
        }
    }

    @Transactional
    public byte[] textToSpeech(String text, Authentication authentication) {

        String email = ((UserImp) authentication.getPrincipal()).getEmail();

        UserImp user = userImpRepository.findUserImpByEmail(email);

        if (user != null && user.getCount() > 0) {
            String speechId = restService.getSpeechId(text) + ".wav";
            byte[] speech = restService.getSpeech(speechId);

            user.setCount(user.getCount() - 1);
            userImpRepository.save(user);

            return speech;
        }

        try {
            throw new InsufficientResourcesException("Insufficient credits");
        } catch (InsufficientResourcesException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ResponseDto> getAllUserDetails() {
        return userImpRepository.findAll()
                .stream()
                .map(user -> ResponseDto.builder()
                        .name(user.getName())
                        .count(user.getCount())
                        .email(user.getEmail())
                        .build())
                .toList();
    }

    public void changeUserTokenCount(String email, int count) {

        if (userImpRepository.existsByEmail(email)) {
            UserImp user = userImpRepository.findUserImpByEmail(email);
            user.setCount(count);
            userImpRepository.save(user);
        }
        throw new UsernameNotFoundException("User not found");
    }
}