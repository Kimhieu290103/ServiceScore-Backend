package dtn.ServiceScore.responses;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRegistrationResponse {
    private List<UserResponse> users;
    private int totalRegistrations;
    private int maxRegistrations;
    private Map<String, Long> studentByCourse;
}