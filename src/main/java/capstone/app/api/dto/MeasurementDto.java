package capstone.app.api.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class MeasurementDto {
    public String name;
    public Long weight;
    public Double unitPrice;
    public Double totalPrice;
    public LocalDateTime firstTime;
    public LocalDateTime endTime;
}
