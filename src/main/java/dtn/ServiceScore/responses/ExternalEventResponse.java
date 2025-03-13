package dtn.ServiceScore.responses;

import dtn.ServiceScore.enums.ExternalEventStatus;
import dtn.ServiceScore.model.Semester;
import dtn.ServiceScore.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalEventResponse {

    private Long id;

    // id sinh vien

    private Long user_id;

    private String nameStudent;

    // ten event tham gia ben ngoai

    private String name;

    // mo ta

    private String description;

    // ngay to chuc

    private LocalDate date;

    // duong dan den minh chung(vd: link drive)

    private String proofUrl;


    private String status ;


    private Long points;

    private String studentName;

    private LocalDateTime created_at;

    private String clazz;

    private String semester;
}
