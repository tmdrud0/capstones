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
    private LocalDateTime firstTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private Long firstWeight;

    @Column
    private Long endWeight;

    @Column
    private Long realWeight;

    @Enumerated(EnumType.STRING)
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "deal_id")
    private Deal deal;
}
