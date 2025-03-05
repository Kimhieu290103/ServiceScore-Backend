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
@Table(name = "semesters") // hoc ki
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ten hoc ki
    @Column(name = "name", nullable = false)
    private String name;

    // ngay bat dau
    @Column(name = "start_date")
    private LocalDate startDate;

    // ngay ket thuc
    @Column(name = "end_date")
    private LocalDate endDate;

//    @OneToMany(mappedBy = "semester")
//    private List<Event> events;
//
//    @OneToMany(mappedBy = "semester")
//    private List<DisciplinaryPoint> disciplinaryPoints;
//
//    @OneToMany(mappedBy = "semester")
//    private List<ExternalEvent> externalEvents;
}
