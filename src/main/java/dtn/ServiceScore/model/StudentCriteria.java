package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "student_criteria")// bang trung gian sinh vien dat duoc tieu chi
public class StudentCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    // ten tieu chi 5 tot
    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private FiveGoodCriteria criteria;

    // da hoan thanh chua(an hien, neu ma qua ki thi an di)
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = true;

    // ngay dat duoc
    @Column(name = "achieved_at")
    private LocalDate achievedAt;
}
