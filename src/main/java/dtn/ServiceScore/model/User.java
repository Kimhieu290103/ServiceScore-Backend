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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(name = "address")
    private String address;

    @Column(name = "password", nullable = false)
    private String password;

    // an hien trang thai
    @Column(name = "is_active")
    private boolean isActive = false;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "email", length = 30)
    private String email;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class clazz; // Doi ten thanh clazz de tranh xung dot voi class java


    // cai nay dung de dang nhap
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // noi role
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

//    @OneToMany(mappedBy = "user")
//    private List<DisciplinaryPoint> disciplinaryPoints;
//
//    @OneToMany(mappedBy = "user")
//    private List<ExternalEvent> externalEvents;
//
//    @OneToMany(mappedBy = "user")
//    private List<Registration> registrations;
//
//    @OneToMany(mappedBy = "user")
//    private List<LcdCriteria> lcdCriteria;
//
//    @OneToMany(mappedBy = "student")
//    private List<StudentCriteria> studentCriteria;
}
