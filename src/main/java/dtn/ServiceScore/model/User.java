package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
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
    private LocalDate dateOfBirth;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + getRole().getName().trim()));
        // authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

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
