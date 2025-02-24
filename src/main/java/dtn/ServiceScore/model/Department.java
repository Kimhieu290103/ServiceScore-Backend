package dtn.ServiceScore.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ten khoa
    @Column(name = "name", nullable = false)
    private String name;

    // khoa ngoai khoa hoc, khoa thuoc khoa hoc nao
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // trang thai cua khoa
    @Column(name = "status")
    private boolean status = true;

//    @OneToMany(mappedBy = "department")
//    private List<Class> classes;
}
