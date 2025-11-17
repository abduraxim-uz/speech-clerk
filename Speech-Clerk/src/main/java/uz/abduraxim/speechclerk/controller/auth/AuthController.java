package uz.abduraxim.speechclerk.controller.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.abduraxim.speechclerk.dto.LoginDto;
import uz.abduraxim.speechclerk.dto.ResponseDto;
import uz.abduraxim.speechclerk.dto.UserImpDto;
import uz.abduraxim.speechclerk.service.UserImpService;
import uz.abduraxim.speechclerk.service.auth.AuthService;

@RestController
@RequestMapping(value = "/api/v1/auth")
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
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(value = "/google-auth")
    public ResponseEntity<String> googleAuth() {
        return null;
    }
}
