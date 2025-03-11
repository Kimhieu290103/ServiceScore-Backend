package dtn.ServiceScore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExternalEventDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("proof_url")
    private String proofUrl;

    @JsonProperty("points")
    private Long points;

    @JsonProperty("semester_id")
    private Long semesterId;
}
