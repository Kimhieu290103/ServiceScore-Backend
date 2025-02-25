package dtn.ServiceScore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginDTO {

    @JsonProperty("username")
    @NotBlank(message = "username khong duoc de trong")
    private String username;

    @NotBlank(message = "pass khong duoc de trong")
    private String password;
}
