package uz.abduraxim.speechclerk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import uz.abduraxim.speechclerk.dto.UserImpDto;
import uz.abduraxim.speechclerk.mapper.UserImpMapper;
import uz.abduraxim.speechclerk.repository.UserImpRepository;

@Service
@RequiredArgsConstructor
public class UserImpService {

    private final UserImpRepository userImpRepository;

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
}