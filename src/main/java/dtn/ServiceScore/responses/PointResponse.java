package dtn.ServiceScore.responses;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointResponse {
    private Long id;
    private String semester;
    private Long points;


}
