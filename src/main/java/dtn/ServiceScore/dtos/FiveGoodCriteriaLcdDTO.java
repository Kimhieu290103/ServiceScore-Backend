package dtn.ServiceScore.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FiveGoodCriteriaLcdDTO {
    @NotBlank(message = "Tên tiêu chí không được để trống")
    private String name;

    private String description;

    @NotNull(message = "ID học kỳ không được để trống")
    private Long semesterId;
}
