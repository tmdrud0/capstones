package capstone.app.repository;

import capstone.app.domain.CompanyProduct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyProductRepository {
    private final EntityManager em;

    public void save(CompanyProduct companyProduct) {
        em.persist(companyProduct);
    }

    public CompanyProduct findOne(Long id) {
        return em.find(CompanyProduct.class, id);
    }

    public List<CompanyProduct> findAll() {
        return em.createQuery("select cp from CompanyProduct cp", CompanyProduct.class)
                .getResultList();
    }
}
