package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "classes")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // ten lop
    @Column(name = "name", nullable = false)
    private String name;

    // khoa cua lop
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // trang thai ( lop da ra truong hay chua)
    @Column(name = "status")
    private boolean status = true;

//    @OneToMany(mappedBy = "clazz") // Use a different name to avoid conflict
//    private List<User> users;

}
