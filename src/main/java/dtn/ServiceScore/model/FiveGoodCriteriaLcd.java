package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "five_good_criteria_lcd") // bo tieu chi hoat dong tot cua lien chi doan
public class FiveGoodCriteriaLcd extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ten tieu chi
    @Column(name = "name", nullable = false)
    private String name;

    // giai thich tieu chi
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester; // Liên kết tiêu chí với kỳ học

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true; // Mặc định là hiển thị (true)

//    @ManyToMany(mappedBy = "fiveGoodCriteriaLcds")
//    private List<Event> events;
//
//    @OneToMany(mappedBy = "fiveGoodCriteriaLcd")
//    private List<LcdCriteria> lcdCriteria;
}
