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
    private Double totalPrice;
    private Company company;

    public DealDto(Deal deal){
        this.totalPrice = deal.getTotalPrice();
        this.totalWeight = deal.getTotalWeight();
        this.company = deal.getCompany();

    }
}