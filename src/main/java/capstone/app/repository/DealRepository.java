package capstone.app.repository;

import capstone.app.domain.Deal;
import capstone.app.domain.User;
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
        return em.createQuery(
                        "select distinct d from Deal d" +
                                " join fetch d.measurements m"
                                , Deal.class)
                .getResultList();
    }

    public List<Deal> findByUsername(String username) {
        return em.createQuery(
                        "select d from Deal d" +
                                " join fetch d.user u" +
                                " where u.username = :username"
                        , Deal.class)
                .setParameter("username", username)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }
}
