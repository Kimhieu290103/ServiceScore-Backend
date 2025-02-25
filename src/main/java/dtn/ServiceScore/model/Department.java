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



//    @OneToMany(mappedBy = "department")
//    private List<Class> classes;
}
