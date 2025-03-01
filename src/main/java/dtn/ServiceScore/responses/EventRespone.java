package dtn.ServiceScore.responses;

import dtn.ServiceScore.model.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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


  private LocalDate date;


  private LocalDate endDate;


  private LocalDate registrationStartDate;


  private LocalDate registrationEndDate;


//  private String status;


  private String  semester;


  private Long user_id;


  private Long score;


  private Long maxRegistrations;

  private Long currentRegistrations;



  private String location;


  private String additionalInfo;

  private String eventType;

//  private Event event;
  private List<EventImageRespone> eventImage;


}
