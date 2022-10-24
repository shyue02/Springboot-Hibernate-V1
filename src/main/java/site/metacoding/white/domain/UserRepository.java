package site.metacoding.white.domain;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // -> 붙여야 IoC에 뜬다
public class UserRepository {

    // DI
    private final EntityManager em;

    public void save(User user) {
        // Persistenc Context에 영속화 시키기 -> 자동 flush (트랜잭션 종료 시)
        em.persist(user);
    }

    public User findByUsername(String username) {
        return em.createQuery("select u from User u where u.username=:username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }

}
