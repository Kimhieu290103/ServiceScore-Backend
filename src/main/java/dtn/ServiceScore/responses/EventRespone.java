package dtn.ServiceScore.responses;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRespone {

    private Long id;

    private String name;


    private String description;


    private LocalDateTime date;


    private LocalDateTime endDate;


    private LocalDateTime registrationStartDate;


    private LocalDateTime registrationEndDate;


//  private String status;


    private String semester;


    private Long user_id;


    private Long score;


    private Long maxRegistrations;

    private Long currentRegistrations;


    private String location;


    private String additionalInfo;

    private String eventType;
    private EventCriteriaResponse eventCriteria;
    //  private Event event;
    private List<EventImageRespone> eventImage;


}
