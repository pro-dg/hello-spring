package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// extends JpaRepository<Member, Long>를 통해 JpaRepository를 상속받아서
// MemberRepository를 구현한 것이다.
// JpaRepository를 상속받으면 기본적인 CRUD 메소드가 자동으로 생성된다.
// JpaRepository를 상속받으면 Member 엔티티와 Long 타입의 id를 가지고 있는 엔티티를 관리할 수 있다.
// JpaRepository를 상속받으면 spring bean으로 자동 등록된다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member,
        Long>, MemberRepository {
        Optional<Member> findByName(String name);
}
