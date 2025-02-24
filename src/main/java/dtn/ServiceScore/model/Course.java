package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ten khoa hoc
    @Column(name = "name", nullable = false)
    private String name;

    // trang thai khoa hoc con hoc khong
    @Column(name = "status")
    private boolean status = true;

//    @OneToMany(mappedBy = "course")
//    private List<Department> departments;
}
