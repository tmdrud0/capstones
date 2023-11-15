package capstone.app.api;

import capstone.app.api.dto.MeasurementDto;
import capstone.app.domain.Measurement;
import capstone.app.domain.Product;
import capstone.app.repository.MeasurementRepository;
import capstone.app.service.MeasurementService;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class MeasurementApiController {

    private final MeasurementService measurementService;

    @GetMapping("/api/measurements")
    public Result measurements() {
        List<Measurement> measurements = measurementService.findMeasurements();
        List<MeasurementDto> result = measurements.stream()
                .map(m -> new MeasurementDto(m))
                .collect(toList());
        return new Result(result);
    }

    @PostMapping("/api/measurements")
    public CreateMeasurementResponse saveDeal(@RequestBody @Valid CreateMeasurementRequest request) {

        Long id = measurementService.saveMeasurement(request.firstTime, request.endTime, request.firstWeight, request.endWeight, request.realWeight, request.product);
        return new MeasurementApiController.CreateMeasurementResponse(id);
    }

    @Data
    @AllArgsConstructor
    static class CreateMeasurementRequest{
        private LocalDateTime firstTime;

        private LocalDateTime endTime;

        private Long firstWeight;

        private Long endWeight;

        private Long realWeight;

        private Product product;
    }

    @Data
    @AllArgsConstructor
    static class CreateMeasurementResponse{
        private Long id;
    }

}
