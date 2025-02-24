package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "five_good_criteria") // bo tieu chi sinh vien 5 tot
public class FiveGoodCriteria extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ten tieu chi
    @Column(name = "name", nullable = false)
    private String name;

    // giai thich ve tieu chi
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

}
