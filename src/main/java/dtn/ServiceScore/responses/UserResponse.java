package dtn.ServiceScore.responses;

import dtn.ServiceScore.model.Class;
import dtn.ServiceScore.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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




}
