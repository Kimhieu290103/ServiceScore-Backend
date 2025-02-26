package dtn.ServiceScore.responses;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventImageRespone {
    private Long id;
    private Long eventID;
    private String imageUrl;
}
