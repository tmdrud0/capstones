package capstone.app.domain;

import capstone.app.api.dto.CarDto;
import capstone.app.api.dto.MeasurementDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static java.lang.Math.max;
import static java.lang.Math.min;

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
    private Double totalPrice;

    @Column
    @Embedded
    private Company company;

    @Column
    private LocalDateTime time;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "deal")
    private List<Measurement> measurements = new ArrayList<>();

    public List<MeasurementDto> getMeasurementDto(){
        List<MeasurementDto> result = new ArrayList<MeasurementDto>();
        measurements.forEach(m -> new MeasurementDto(m.getItem(),m.getActualWeight(),m.getUnitCost(),m.getActualWeight() * m.getUnitCost(),m.getEnterTime(),m.getDepartTime()));
        return result;

    }

    public List<CarDto> getCarDto(){
        List<CarDto> result = new ArrayList<CarDto>();
        measurements.forEach(m->result.stream().filter(r->r.licenseNum.equals(m.getLiscenseNum()))
                .findFirst()
                .ifPresentOrElse(r -> {
                    r.totalWeight = max(r.totalWeight, m.getTotalWeight());
                    r.emptyWeight = min(r.emptyWeight, m.getEmptyWeight());
                    r.actualWeight = r.totalWeight - r.emptyWeight;
                },
        () -> result.add(new CarDto(m.getDate(),m.getLiscenseNum(),m.getTotalWeight(),m.getEmptyWeight(),m.getActualWeight()))));

        return result;
    }

    public Long makeTotalWeight (){
        return getMeasurementDto().stream().map(m->m.weight).reduce(0L, Long::sum);
    }
    public Double makeTotalPrice (){
        return getMeasurementDto().stream().map(m->m.totalPrice).reduce(0.D, Double::sum);
    }
}