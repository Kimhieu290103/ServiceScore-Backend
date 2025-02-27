package dtn.ServiceScore.responses;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CriteriaResponse {
    private Long id;
    private String name;
    private String description;
}
