package uz.abduraxim.speechclerk;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import uz.abduraxim.speechclerk.model.Role;
import uz.abduraxim.speechclerk.model.UserImp;
import uz.abduraxim.speechclerk.repository.UserImpRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class SpeechClerkApplication {

    private final UserImpRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {

        SpringApplication.run(SpeechClerkApplication.class, args);
    }

    @Bean
    public CommandLineRunner startUp() {
        return args -> {
            if (!userRepository.existsByEmail("superadmin@speech.clerk.com")) {
                userRepository.save(UserImp.builder()
                        .name("superadmin")
                        .role(Role.ADMIN)
                        .count(1000)
                        .password(encoder.encode("admin.speech"))
                        .email("superadmin@speech.clerk.com")
                        .provider("SYSTEM")
                        .build());
            }
        };
    }
}
