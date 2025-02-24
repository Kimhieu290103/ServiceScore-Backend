package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "disciplinary_points")
public class DisciplinaryPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // khoa ngoai sinh vien
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // khoa ngoai hoc ki
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    // so diem sinh vien hoc ki do
    @Column(name = "points")
    private Long points;
}
