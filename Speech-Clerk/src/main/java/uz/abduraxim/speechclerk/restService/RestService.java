package uz.abduraxim.speechclerk.restService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestService {

    @Value("${rest.get.speech-id}")
    private String GET_SPEECH_ID_API;

    @Value("${rest.get.speech}")
    private String GET_SPEECH;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getSpeechId(String text) {

        String url = UriComponentsBuilder.fromUriString(GET_SPEECH_ID_API).toUriString();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("text", text);

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "*/*");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                String.class
        );

        return response.getStatusCode().is2xxSuccessful() && response.getBody() != null
                ? response.getBody()
                : "";
    }

    public byte[] getSpeech(String speechId) {

        String url = UriComponentsBuilder.fromUriString(GET_SPEECH)
                .path(speechId)
                .toUriString();

        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(null),
                byte[].class
        );

        return response.getStatusCode().is2xxSuccessful() && response.getBody() != null
                ? response.getBody()
                : new byte[0];
    }
}
