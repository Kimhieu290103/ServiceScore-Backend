package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event_criteria") // bang trung gian de noi event dap ung tieu chi nao cung bo tieu chi 5 tot
public class EventCriteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // id event
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // id bo tieu chi 5 tot
    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private FiveGoodCriteria criteria;
}
