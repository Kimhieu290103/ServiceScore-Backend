package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lcd_criteria") // bangr trung gian lien chi doan da dat duoc tieu chi hoat dong tot
public class LcdCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ten tieu chi hoat dong tot
    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private FiveGoodCriteriaLcd fiveGoodCriteriaLcd;

    // hoat dong o dau
    @Column(name = "achieved_at")
    private LocalDate achievedAt;

    // da to chuc xong chua
    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted = true;
}
