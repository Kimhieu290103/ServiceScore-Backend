package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "registrations") // bang dang ki su kien
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // id su kien
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // diem danh
    @Column(name = "attendances", nullable = false)
    private boolean attendances = false;

    // dang ki luc nao
    @Column(name = "registered_at")
    private LocalDate registeredAt;

    // trang thai( da gui danh ki, da xac nhan dang ki)
    @Column(name = "status", nullable = false)
    private String status = "pending";

}
