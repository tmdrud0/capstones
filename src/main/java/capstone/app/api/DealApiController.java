package capstone.app.api;

import capstone.app.api.dto.DealDto;
import capstone.app.domain.Deal;
import capstone.app.service.DealService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DealApiController {

    private final DealService dealService;

    @PostMapping("/api/deals")
    public CreateDealResponse saveDeal(@RequestBody @Valid CreateDealRequest request) {

        Long id = dealService.saveDeal(request.totalPrice, request.totalWeight);
        return new CreateDealResponse(id);
    }

    @GetMapping("/api/deals")
    public Result deal() {

        List<Deal> findDeals = dealService.findDeals();
        System.out.println(findDeals);
        List<DealDto> collect = findDeals.stream()
                .map(d -> new DealDto(d))
                .collect(Collectors.toList());

        return new Result(collect);
    }
    // 측정목록에서 딜 생성

    //딜 페이징, 날짜순

    //딜 검색

    @Data
    @AllArgsConstructor
    static class CreateDealRequest{
        private Long totalWeight;
        private Long totalPrice;
    }

    @Data
    @AllArgsConstructor
    static class CreateDealResponse{
        private Long id;
    }

}
