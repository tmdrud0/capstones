package capstone.app.repository;

import capstone.app.domain.Deal;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DealRepository {
    private final EntityManager em;

    public void save(Deal deal) {
        em.persist(deal);
    }

    public Deal findOne(Long id) {
        return em.find(Deal.class, id);
    }

    public List<Deal> findAll() {
        return em.createQuery("select d from Deal d", Deal.class)
                .getResultList();
    }
}
