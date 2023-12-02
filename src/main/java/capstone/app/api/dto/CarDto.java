package capstone.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CarDto {
    public LocalDateTime date;
    public String licenseNum;

    public Long totalWeight;

    public Long emptyWeight;

    public Long actualWeight;
}
