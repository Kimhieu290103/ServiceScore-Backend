package dtn.ServiceScore.responses;

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
