package dtn.ServiceScore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDTO {
    @JsonProperty("event_name") // Đổi tên JSON thành "event_name" thay vì "name"
    private String name;

    @JsonProperty("event_description")
    private String description;

    @JsonProperty("event_date")
    private LocalDate date;

    @JsonProperty("semester") // Giữ nguyên kiểu camelCase cho API dễ đọc
    private String semester;

    @JsonProperty("organizing_committee")
    private String organizingCommittee;

    @JsonProperty("event_score")
    private Long score;

    @JsonProperty("max_participants") // Đổi tên cho dễ hiểu
    private Long maxRegistrations;

    @JsonProperty("current_participants")
    private Long currentRegistrations;

    @JsonProperty("event_category") // Đổi "eventType" thành "event_category"
    private String eventType;
}
