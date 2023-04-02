package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// transactional 어노테이션을 붙이면 스프링이 JPA를 사용할 때 트랜잭션을 시작하고, 커밋을 해준다.
// 이렇게 하면 개발자가 직접 트랜잭션을 관리할 필요가 없다.
@Transactional
public class JPAMemberRepository implements MemberRepository {

    // JPA는 EntityManager라는 것으로 모든 것을 동작시킨다.
    // 내부적으로 DB 커넥션을 가져오고, 트랜잭션을 가져오고, DB에 쿼리를 날리고,
    // 결과를 받아서 엔티티 객체로 변환하는 등의 모든 것을 관리한다.
    private final EntityManager em;

    public JPAMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    // JPQL은 SQL과 문법이 비슷하다.
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public void clearStore() {
        em.createQuery("delete from Member").executeUpdate();
    }
}
