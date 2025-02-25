package dtn.ServiceScore.responses;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRespone {
    private String accessToken;
}
