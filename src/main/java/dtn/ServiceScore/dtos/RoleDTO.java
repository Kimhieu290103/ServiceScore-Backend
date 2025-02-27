package dtn.ServiceScore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleDTO {
    // Tên role
    @JsonProperty("name")
    private String name;
}
