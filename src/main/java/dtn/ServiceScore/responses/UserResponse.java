package dtn.ServiceScore.responses;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;


    private String fullname;


    private String phoneNumber;


    private String studentId;


    private String address;


    private boolean isActive;


    private LocalDate dateOfBirth;


    private String email;


    private String username;

    private String clazz;

    private String Department;

    private boolean attendances;

}
