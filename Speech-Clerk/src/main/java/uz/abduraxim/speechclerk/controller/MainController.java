package uz.abduraxim.speechclerk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.abduraxim.speechclerk.restService.RestService;

@RestController
@RequestMapping(value = "/v1/api/main")
public class MainController {

    private final RestService restService;

    @Autowired
    public MainController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping(value = "/text-to-speech")
    public ResponseEntity<byte[]> textToSpeech(@RequestParam("text") String text) {

        System.out.println("Yuborilgan matn: " + text);

        String speechId = restService.getSpeechId(text) + ".wav";
        byte[] audioBytes = restService.getSpeech(speechId);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/x-wav"));
        headers.setContentLength(audioBytes.length);
        return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
    }
}