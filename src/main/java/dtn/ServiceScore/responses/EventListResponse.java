package dtn.ServiceScore.responses;

import dtn.ServiceScore.model.Event;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventListResponse {
    private List<EventRespone> events;
    private int totalPage;
}
