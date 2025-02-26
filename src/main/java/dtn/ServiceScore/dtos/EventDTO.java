package dtn.ServiceScore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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


    // ngay ket thuc
    @JsonProperty( "end_date")
    private LocalDate endDate;

    // ngay bat dau dang ky
    @JsonProperty( "registration_start_date")
    private LocalDate registrationStartDate;

    // ngay ket thuc dang ky
    @JsonProperty( "registration_end_date")
    private LocalDate registrationEndDate;

    // ngay ket thuc dang ky
    @JsonProperty( "status")
    private String status;

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

    private List<MultipartFile> files;
}
