package capstone.app.api.dto;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CarDto {
    public LocalDateTime date;
    public String licenseNum;

    public Long totalWeight;

    public Long emptyWeight;

    public Long actualWeight;
}
