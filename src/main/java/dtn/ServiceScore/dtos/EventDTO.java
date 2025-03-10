package dtn.ServiceScore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private LocalDateTime date;

    // ngay ket thuc
    @JsonProperty("end_date")
    private LocalDateTime endDate;

    // ngay bat dau dang ky
    @JsonProperty("registration_start_date")
    private LocalDateTime registrationStartDate;

    // ngay ket thuc dang ky
    @JsonProperty("registration_end_date")
    private LocalDateTime registrationEndDate;

    // ngay ket thuc dang ky
    @JsonProperty("status")
    private String status;

    @JsonProperty("semester") // Giữ nguyên kiểu camelCase cho API dễ đọc
    private String semester;

    @JsonProperty("location")
    private String location;          // Thêm trường location

    @JsonProperty("additional_info")
    private String additionalInfo;

    @JsonProperty("event_score")
    private Long score;

    @JsonProperty("max_participants") // Đổi tên cho dễ hiểu
    private Long maxRegistrations;

    @JsonProperty("current_participants")
    private Long currentRegistrations;

    @JsonProperty("event_type")
    private Long eventType;

    @JsonProperty("five_good")
    private String five_good_id;

    @JsonProperty("five_good_lcd")
    private String five_good_lcd_id;

    private List<MultipartFile> files;
}
