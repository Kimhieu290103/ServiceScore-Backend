package dtn.ServiceScore.responses;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentPointResponse {
    private Long studentId;
    private String studentName;
    private String className;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String Department;
    private Long totalPoints;
    private String address;
}
