package capstone.app.api.dto;

import capstone.app.domain.Measurement;
import capstone.app.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MeasurementDto {

    private LocalDateTime firstTime;

    private LocalDateTime endTime;

    private Long firstWeight;

    private Long endWeight;

    private Long realWeight;

    private Product product;

    public MeasurementDto(Measurement measurement){
        this.firstTime = measurement.getFirstTime();
        this.endTime = measurement.getEndTime();
        this.firstWeight = measurement.getFirstWeight();
        this.endWeight = measurement.getEndWeight();
        this.realWeight = measurement.getRealWeight();
        this.product = measurement.getProduct();
    }
}
