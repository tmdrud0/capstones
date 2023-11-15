package capstone.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
public class Deal {

    @Id
    @GeneratedValue
    @Column(name = "deal_id")
    private Long id;

    @Column
    private Long totalWeight;

    @Column
    private Long totalPrice;

    @Column
    @Embedded
    private Company company;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "deal")
    private List<Measurement> measurements = new ArrayList<>();

}