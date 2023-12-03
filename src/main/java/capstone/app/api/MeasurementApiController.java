package capstone.app.api;

import capstone.app.domain.Measurement;
import capstone.app.service.MeasurementService;
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

    //@PostMapping("/api/measurements")
    @PostMapping("/sendWeighData")
    public CreateMeasurementResponse saveMeasurement(@RequestBody @Valid CreateMeasurementRequest request) {
        Long id = measurementService.saveMeasurement(request.toMeasurement());
        return new MeasurementApiController.CreateMeasurementResponse(id);
    }
    @GetMapping("/api/measurements")
    public List<CreateMeasurementRequest> getMeasurements() {
        return measurementService.findMeasurements().stream().map(m ->{
            CreateMeasurementRequest c = new CreateMeasurementRequest(
                    m.getDate(),
                    m.getLiscenseNum(),
                    m.getClient(),
                    m.getItem(),
                    m.getEmptyWeight(),
                    m.getTotalWeight(),
                    m.getActualWeight(),
                    m.getUnitCost(),
                    m.getNote(),
                    m.getDriverName(),
                    m.getDriverTP(),
                    m.getEnterTime(),
                    m.getDepartTime()
            );
            return c;
        }).collect(toList());
    }








    @Data
    @AllArgsConstructor
    static class CreateMeasurementRequest{
        private LocalDateTime date;
        private String liscenseNum;
        private String client;
        private String item;
        private Long totalWeight;
        private Long emptyWeight;
        private Long actualWeight;
        private Double unitCost;
        private String note;
        private String driverName;
        private String driverTP;
        private LocalDateTime enterTime;
        private LocalDateTime departTime;

        public Measurement toMeasurement(){
            Measurement measurement = new Measurement();

            measurement.setDate(LocalDateTime.now());
            measurement.setLiscenseNum(liscenseNum);
            measurement.setClient(client);
            measurement.setItem(item);
            measurement.setTotalWeight(totalWeight);
            measurement.setEmptyWeight(emptyWeight);
            measurement.setActualWeight(actualWeight);
            measurement.setUnitCost(unitCost);
            measurement.setNote(note);
            measurement.setDriverName(driverName);
            measurement.setDriverTP(driverTP);
            measurement.setEnterTime(enterTime);
            measurement.setDepartTime(departTime);

            return measurement;
        }
    }

    @Data
    @AllArgsConstructor
    static class CreateMeasurementResponse{
        private Long id;
    }
}
