package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event_criteria_lcd") // bang trung gian noi event dap ung tieu chi hoat dong nao cua lien chi doan
public class EventCriteriaLcd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Event event;

    // id bo tieu chi cua lien chi doan
    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private FiveGoodCriteriaLcd criteria;
}
