package dtn.ServiceScore.responses;

import dtn.ServiceScore.model.Event;
import dtn.ServiceScore.model.EventImage;
import dtn.ServiceScore.model.Lcd;
import dtn.ServiceScore.model.Semester;
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

  private Event event;
  private List<EventImageRespone> eventImage;


}
