package capstone.app.domain;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
public class Profile {
    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    @Column
    private String carNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
