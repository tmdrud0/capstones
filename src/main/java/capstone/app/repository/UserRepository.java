package capstone.app.repository;

import capstone.app.domain.Company;
import capstone.app.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from Users u", User.class)
                .getResultList();
    }
    public List<User> findByName(String name) {
        return em.createQuery("select u from Users u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Optional<User> findOneWithAuthoritiesByUsername(String username){
       List<User> users  = em.createQuery("select u from users u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
       if(users.isEmpty())  return Optional.empty();
       return Optional.of(users.get(0));
    }
    public Company findCompanyByUserName(String userName){
        User user = em.createQuery("select u from Users u where u.username = :userName", User.class)
                .setParameter("userName", userName)
                .getSingleResult();
        return user.getCompany();
    }

}
