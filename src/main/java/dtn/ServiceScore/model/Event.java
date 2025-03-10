package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ten event
    @Column(name = "name", nullable = false)
    private String name;

    // chi tiet
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // ngay to chuc
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    // ngay ket thuc
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    // ngay bat dau dang ky
    @Column(name = "registration_start_date", nullable = false)
    private LocalDateTime registrationStartDate;

    // ngay ket thuc dang ky
    @Column(name = "registration_end_date", nullable = false)
    private LocalDateTime registrationEndDate;

    // ngay ket thuc dang ky
    @Column(name = "status", nullable = false)
    private String status;

    // event thuoc hoc ki nao
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    // do ai to chuc(vd: doan truong, lien chi doan, hoi sinh vien)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // so diem cua event
    @Column(name = "score")
    private Long score;

    // so luong sinh vien dang ki toi da cho su kien
    @Column(name = "max_registrations")
    private Long maxRegistrations;

    // so luong sinh vien hien tai da dang ki
    @Column(name = "current_registrations")
    private Long currentRegistrations;

    @ManyToOne
    @JoinColumn(name = "event_type_id", nullable = false)
    private EventType eventType;

    @Column(name = "location", nullable = false)
    private String location;          // Thêm trường location

    @Column(name = "additional_info", nullable = false)
    private String additionalInfo;


//    @OneToMany(mappedBy = "event")
//    private List<EventImage> eventImages;
//
//    @OneToMany(mappedBy = "event")
//    private List<Registration> registrations;
//
//    @ManyToMany
//    @JoinTable(
//            name = "event_criteria",
//            joinColumns = @JoinColumn(name = "event_id"),
//            inverseJoinColumns = @JoinColumn(name = "criteria_id")
//    )
//    private List<FiveGoodCriteria> fiveGoodCriteria;
//
//    @ManyToMany
//    @JoinTable(
//            name = "event_criteria_lcd",
//            joinColumns = @JoinColumn(name = "event_id"),
//            inverseJoinColumns = @JoinColumn(name = "criteria_id")
//    )
//    private List<FiveGoodCriteriaLcd> fiveGoodCriteriaLcds;

}
