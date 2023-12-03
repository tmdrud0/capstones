package capstone.app.service;

import capstone.app.domain.Deal;
import capstone.app.domain.User;
import capstone.app.repository.DealRepository;
import capstone.app.repository.PdfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final MeasurementService measurementService;
    private final UserService userService;
    private final PdfRepository pdfRepository;
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

        deal.setUser(me);
        deal.setCompany(me.getCompany());
        deal.setTime(LocalDateTime.now());

        deal.setMeasurements(measurementService.findMyNotDealMeasurements());
        deal.getMeasurements().forEach(m->m.setDeal(deal));
        deal.setTotalPrice(deal.makeTotalPrice());
        deal.setTotalWeight(deal.makeTotalWeight());
        String client = deal.getMeasurements().get(0).getClient();

        deal.setPdf(deal.getTime().format(DateTimeFormatter.ofPattern("yy년MM월dd일HH시mm분_"))+client+".pdf");

        deal.getMeasurements().stream().forEach(m-> System.out.println(m.getDate()));
        Long id = join(deal);
        pdfRepository.generatePdf(deal);
        return id;
    }

    @Transactional
    public Deal getRecent(){
        return dealRepository.findRecent(userService.getMyUserWithAuthorities().get().getUsername());
    }

    @Transactional
    public List<Deal> getMyDeals(){
        return dealRepository.findByUsername(userService.getMyUserWithAuthorities().get().getUsername());
    }


    @Transactional
    public Deal getById(Long id){
        return dealRepository.findOne(id);
    }
}
