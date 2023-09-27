package capstone.app.repository;

import capstone.app.domain.Profile;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProfileRepository {
    private final EntityManager em;

    public void save(Profile profile) {
        em.persist(profile);
    }

    public Profile findOne(Long id) {
        return em.find(Profile.class, id);
    }

    public List<Profile> findAll() {
        return em.createQuery("select p from Profile p", Profile.class)
                .getResultList();
    }
}
