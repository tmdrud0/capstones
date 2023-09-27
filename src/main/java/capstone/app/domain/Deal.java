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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @JsonIgnore
    @OneToMany(mappedBy = "deal")
    private List<Measurement> measurements = new ArrayList<>();

}