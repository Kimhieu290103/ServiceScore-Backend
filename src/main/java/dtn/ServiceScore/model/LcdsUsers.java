package dtn.ServiceScore.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "lcds_users")// user thuoc to chuc nao (vd: hoi sinh vien, lien chi doan khoa CNTT)
public class LcdsUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

// to chuc
    @ManyToOne
    @JoinColumn(name = "lcd_id")
    private Lcd lcd;

}
