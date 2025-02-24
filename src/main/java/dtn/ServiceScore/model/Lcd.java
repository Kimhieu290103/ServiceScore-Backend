package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lcds") // bang to chuc su kien( vd : lien chi doan khoa CNTT, lien chih doan khoa co khi)
public class Lcd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ten lien chi doan, hoi sinh vien, doan truong
    @Column(name = "name", nullable = false)
    private String name;

//    @OneToMany(mappedBy = "organizingCommittee")
//    private List<Event> events;

//    @ManyToMany
//    @JoinTable(
//            name = "lcds_users",
//            joinColumns = @JoinColumn(name = "lcd_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    private List<User> users;
}
