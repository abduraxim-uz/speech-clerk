package uz.abduraxim.speechclerk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import uz.abduraxim.speechclerk.dto.ResponseDto;
import uz.abduraxim.speechclerk.service.UserImpService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/main")
public class MainController {

    private final UserImpService userImpService;

    @Autowired
    public MainController(UserImpService userImpService) {
        this.userImpService = userImpService;
    }

    @GetMapping(value = "/text-to-speech")
    @PreAuthorize(value = "hasAnyRole('ADMIN','USER')")
    public ResponseEntity<byte[]> textToSpeech(@RequestParam("text") String text, Authentication authentication) {

        System.out.println("Yuborilgan matn: " + text);

//        String speechId = restService.getSpeechId(text) + ".wav";
//        byte[] audioBytes = restService.getSpeech(speechId);

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("audio/x-wav"));
//        headers.setContentLength(audioBytes.length);
        return ResponseEntity.ok(userImpService.textToSpeech(text, authentication));
    }

    @GetMapping(value = "/getAllUserDetails")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<List<ResponseDto>> getAllUserDetails() {
        return ResponseEntity.ok(userImpService.getAllUserDetails());
    }

    @PutMapping(value = "/change-user-token-count")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Void> changeUserTokenCount(@Param("email") String email,
                                                     @Param("count") int count) {
        userImpService.changeUserTokenCount(email, count);
        return ResponseEntity.ok().build();
    }
}
