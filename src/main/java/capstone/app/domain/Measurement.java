package capstone.app.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
public class Measurement {

    @Id
    @GeneratedValue
    @Column(name = "measurement_id")
    private Long id;

    @Column
    private String carNumber;

    @Column
    private LocalDateTime firstTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private Long firstWeight;

    @Column
    private Long endWeight;

    @Column
    private Long realWeight;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "deal_id")
    private Deal deal;
}
