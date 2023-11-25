package capstone.app.service;

import capstone.app.domain.Deal;
import capstone.app.domain.Measurement;
import capstone.app.domain.Product;
import capstone.app.repository.DealRepository;
import capstone.app.repository.MeasurementRepository;
import capstone.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final UserRepository userRepository;
    @Transactional
    public Long join(Measurement measurement) {

        measurementRepository.save(measurement);
        return measurement.getId();
    }

    @Transactional
    public List<Measurement> findMeasurements() {
        return measurementRepository.findAll();
    }

    @Transactional
    public Long saveMeasurement(LocalDateTime firstTime, LocalDateTime endTime, Long firstWeight, Long endWeight, Long realWeight, Product product){
        Measurement measurement = new Measurement();
        measurement.setFirstTime(firstTime);
        measurement.setEndTime(endTime);
        measurement.setFirstWeight(firstWeight);
        measurement.setEndWeight(endWeight);
        measurement.setRealWeight(realWeight);
        measurement.setProduct(product);
        return join(measurement);
    }

}
