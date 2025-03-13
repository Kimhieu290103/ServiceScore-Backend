package dtn.ServiceScore.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassSearchRequest {
    private Long departmentId;
    private Long courseId;
}
