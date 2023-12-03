package capstone.app.api;

import capstone.app.api.dto.DealDto;
import capstone.app.api.dto.MeasurementDto;
import capstone.app.domain.Deal;
import capstone.app.repository.DealRepository;
import capstone.app.service.DealService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DealApiController {

    private final DealService dealService;
    private final DealRepository dealRepository;

    //@PostMapping("/api/deals")
    @PostMapping("/api/done")
    public Long saveDeal() {

        Long id = dealService.saveDeal();
        return id;
    }

    @GetMapping("/api/deals")
    public List<DealDto> getDeals() {
        List<Deal> findDeals = dealService.findDeals();
        List<DealDto> collect = findDeals.stream()
                .map(d -> new DealDto(d))
                .collect(Collectors.toList());

        return collect;
    }

    @GetMapping("/api/myDeals")
    public List<DealDto> getMyDeals() {
        List<Deal> findDeals = dealService.getMyDeals();
        List<DealDto> collect = findDeals.stream()
                .map(d -> new DealDto(d))
                .collect(Collectors.toList());
        return collect;
    }
    //딜 페이징, 날짜순

    //딜 검색



    @Data
    static class DealResponse {
        private LocalDateTime date;
        private List<String> licenseNum;
        private List<SimpleMeasurement> measurements;

        @Getter
        static class SimpleMeasurement{
            private String name;
            private Long weight;
            private Double unitPrice;
            private Double totalPrice;


            public static SimpleMeasurement toSimpleMeasurement(MeasurementDto measurementDto){
                SimpleMeasurement result = new SimpleMeasurement();
                result.name = measurementDto.name;
                result.weight = measurementDto.weight;
                result.unitPrice = measurementDto.unitPrice;
                result.totalPrice = measurementDto.totalPrice;
                return result;
            }
        }

        static public DealResponse toCreateDealResponse(Deal deal){
            DealResponse result = new DealResponse();

            result.date = deal.getTime();
            result.licenseNum = deal.getCarDto().stream().map(c -> c.licenseNum).collect(Collectors.toList());
            result.measurements = deal.getMeasurementDto().stream().map(SimpleMeasurement::toSimpleMeasurement).collect(Collectors.toList());

            return result;
        }
    }

}
