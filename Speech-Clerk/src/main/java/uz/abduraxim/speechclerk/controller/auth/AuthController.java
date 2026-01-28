package uz.abduraxim.speechclerk.controller.auth;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import uz.abduraxim.speechclerk.dto.LoginDto;
import uz.abduraxim.speechclerk.dto.ResponseDto;
import uz.abduraxim.speechclerk.dto.UserImpDto;
import uz.abduraxim.speechclerk.service.UserImpService;
import uz.abduraxim.speechclerk.service.auth.AuthService;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/v2/auth")
public class AuthController {

    private final UserImpService userImpService;

    private final AuthService authService;

    @Autowired
    public AuthController(UserImpService userImpService, AuthService authService) {
        this.userImpService = userImpService;
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserImpDto dto) {
        userImpService.register(dto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginDto dto) {
        log.debug(dto.toString());
        return ResponseEntity.ok(authService.login(dto));
    }

//    @GetMapping(value = "/google-auth")
//    public ResponseEntity<Map<String, Object>> googleAuth(OAuth2AuthenticationToken token) {
//        Map<String, Object> res = Map.of(
//                "name", token.getPrincipal().getName(),
//                "email", token.getPrincipal().getAttributes().get("email"),
//                "authorities", token.getPrincipal().getAuthorities()
//        );
//        return ResponseEntity.ok(res);
//    }

}
