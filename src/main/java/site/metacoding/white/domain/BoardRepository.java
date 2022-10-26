package site.metacoding.white.domain;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository // -> 붙여야 IoC에 뜬다
public class BoardRepository {

    private final EntityManager em;

    public Board save(Board board) {
        em.persist(board); // insert 쿼리가 자동으로 돌면서 insert가 됨 / 아직은 이해하지 못하는 코드
        return board;
    }

    public Optional<Board> findById(Long id) {
        // JPQL 문법
        Optional<Board> boardOP = Optional.ofNullable(em
                .createQuery("select b from Board b where b.id = :id",
                        Board.class)
                .setParameter("id", id) // 매핑되는거 걸기
                .getSingleResult()); // 한 건 받기

        return boardOP;
    }

    public List<Board> findAll() {
        // JPQL 문법
        List<Board> boardList = em.createQuery("select b from Board b", Board.class)
                .getResultList();
        return boardList;
    }

    public void deleteById(Long id) {
        em.createQuery("delete from Board b where b.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }
}
