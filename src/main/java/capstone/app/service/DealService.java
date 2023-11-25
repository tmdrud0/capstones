package capstone.app.service;

import capstone.app.domain.Company;
import capstone.app.domain.Deal;
import capstone.app.domain.Product;
import capstone.app.domain.User;
import capstone.app.jwt.SecurityUtil;
import capstone.app.repository.DealRepository;
import capstone.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final UserRepository userRepository;
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
    public Long saveDeal(Long totalPrice, Long totalWeight){
        Deal deal = new Deal();
        deal.setTotalPrice(totalPrice);
        deal.setTotalWeight(totalWeight);

        User me = userService.getMyUserWithAuthorities().get();

        deal.setUser(me);
        deal.setCompany(me.getCompany());

        return join(deal);
    }

}
