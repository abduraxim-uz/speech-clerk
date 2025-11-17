package uz.abduraxim.speechclerk.dto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {

    private String name;

    private String email;

    private String token;
}
