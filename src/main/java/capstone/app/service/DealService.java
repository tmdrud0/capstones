package capstone.app.service;

import capstone.app.domain.Deal;
import capstone.app.domain.User;
import capstone.app.repository.DealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final MeasurementService measurementService;
    private final UserService userService;
    @Transactional
    public Long join(Deal deal) {

        dealRepository.save(deal);
        return deal.getId();
    }

    @Transactional
    public List<Deal> findDeals() {
        return dealRepository.findAll();
    }

    @Transactional
    public Long saveDeal(){
        Deal deal = new Deal();

        User me = userService.getMyUserWithAuthorities().get();
        deal.setTotalPrice(deal.makeTotalPrice());

        deal.setUser(me);
        deal.setCompany(me.getCompany());

        deal.setMeasurements(measurementService.findMyNotDealMeasurements());
        deal.setTotalPrice(deal.makeTotalPrice());
        deal.setTotalWeight(deal.makeTotalWeight());

        return join(deal);
    }

}
