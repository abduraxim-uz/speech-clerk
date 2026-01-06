package uz.abduraxim.speechclerk.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uz.abduraxim.speechclerk.model.Role;
import uz.abduraxim.speechclerk.model.UserImp;
import uz.abduraxim.speechclerk.repository.UserImpRepository;
import uz.abduraxim.speechclerk.service.jwtService.JwtUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserImpRepository userRepository;

    private final JwtUtil jwtUtil;

    private final ObjectMapper objectMapper;

//    public OAuth2LoginSuccessHandler(UserImpRepository userRepository, JwtUtil jwtUtil) {
//        this.userRepository = userRepository;
//        this.jwtUtil = jwtUtil;
//    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = token.getPrincipal().getAttributes();

        String providerId = (String) attributes.get("sub");
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        UserImp user = userRepository.findByProviderAndProviderId("GOOGLE", providerId)
                .orElseGet(() -> {
                    UserImp newUser = new UserImp();
                    newUser.setProvider("GOOGLE");
                    newUser.setProviderId(providerId);
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setCount(10);
                    newUser.setRole(Role.USER);
                    newUser.setPassword(UUID.randomUUID().toString());
                    newUser.setCreatedAt(LocalDateTime.now());
                    return userRepository.save(newUser);
                });

        String frontendUrl = "http://127.0.0.1:3000/dashboard.html?token=" + jwtUtil.encode(user.getUsername(), user.getAuthorities()) + "&name=" + user.getName() + "&email=" + user.getEmail() + "&count=" + user.getCount();
        response.sendRedirect(frontendUrl);

//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(objectMapper.writeValueAsString(
//                ResponseDto.builder()
//                        .name(user.getName())
//                        .email(user.getEmail())
//                        .token(jwtUtil.encode(user.getUsername(), user.getAuthorities()))
//                        .count(user.getCount())
//                        .build()));
//        response.getWriter().flush();
    }
}
