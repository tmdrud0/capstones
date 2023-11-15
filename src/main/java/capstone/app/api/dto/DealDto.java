package capstone.app.api.dto;

import capstone.app.domain.Company;
import capstone.app.domain.Deal;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class DealDto {
    private Long totalWeight;
    private Long totalPrice;
    private Company company;

    private List<MeasurementDto> measurements;
    public DealDto(Deal deal){
        this.totalPrice = deal.getTotalPrice();
        this.totalWeight = deal.getTotalWeight();
        this.company = deal.getCompany();
        this.measurements = deal.getMeasurements().stream()
                .map(m -> new MeasurementDto(m))
                .collect(Collectors.toList());
    }
}