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
    private LocalDateTime date;
    @Column
    private String liscenseNum;
    @Column
    private String client;
    @Column
    private String item;
    @Column
    private Long totalWeight;
    @Column
    private Long emptyWeight;
    @Column
    private Long actualWeight;
    @Column
    private Double unitCost;
    @Column
    private String note;
    @Column
    private String driverName;
    @Column
    private String driverTP;
    @Column
    private LocalDateTime enterTime;
    @Column
    private LocalDateTime departTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "deal_id")
    private Deal deal;
}
