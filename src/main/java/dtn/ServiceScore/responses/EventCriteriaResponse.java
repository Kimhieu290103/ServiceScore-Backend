package dtn.ServiceScore.responses;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCriteriaResponse {
    private List<CriteriaResponse> eventCriteriaLcd = new ArrayList<>();
    private List<CriteriaResponse> eventCriteria = new ArrayList<>();
}
