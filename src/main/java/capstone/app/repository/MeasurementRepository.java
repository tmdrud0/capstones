package capstone.app.repository;

import capstone.app.domain.Deal;
import capstone.app.domain.Measurement;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MeasurementRepository {
    private final EntityManager em;

    public void save(Measurement measurement) {
        em.persist(measurement);
    }

    public Measurement findOne(Long id) {
        return em.find(Measurement.class, id);
    }

    public List<Measurement> findAll() {
        return em.createQuery("select m from Measurement m", Measurement.class)
                .getResultList();
    }

    public List<Measurement> findMyNotDeal(Long userid) {
        return em.createQuery("select m from Measurement m where m.user.id = :userid AND m.deal.id is NULL", Measurement.class)
                .setParameter("userid", userid)
                .getResultList();
    }

    public Measurement findRecent(String username) {
        return em.createQuery(
                        "select m from Measurement m" +
                                " join fetch m.user u" +
                                " where u.username = :username"+
                                "order by m.time enterTime"
                        , Measurement.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}