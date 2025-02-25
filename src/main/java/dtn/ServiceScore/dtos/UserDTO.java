package dtn.ServiceScore.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    @JsonProperty("fullname")
    private String fullname;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("student_id")
    private String studentId;

    @JsonProperty("address")
    private String address;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("retype_password")
    private String retypePassword;

    @JsonProperty("class_id")
    private String className; // Chỉ gửi ID của class thay vì object đầy đủ

    @JsonProperty("role_id")
    private String roleName;  // Chỉ gửi ID của role thay vì object đầy đủ
}
