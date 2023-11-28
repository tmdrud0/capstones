package capstone.app.service;

import capstone.app.domain.Measurement;
import capstone.app.domain.User;
import capstone.app.jwt.SecurityUtil;
import capstone.app.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final UserService userService;

    @Transactional
    public Long join(Measurement measurement) {

        measurementRepository.save(measurement);
        return measurement.getId();
    }

    @Transactional
    public List<Measurement> findMeasurements() {
        return measurementRepository.findAll();
    }

    public List<Measurement> findMyNotDealMeasurements() {
        return measurementRepository.findMyNotDeal(SecurityUtil.getCurrentUserId().get());
    }

    @Transactional
    public Long saveMeasurement(Measurement measurement){
        User me = userService.getMyUserWithAuthorities().get();
        measurement.setUser(me);

        return join(measurement);
    }

}
