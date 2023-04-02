package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 통합 테스트. 통합테스트에는 아래의 두 어노테이션이 필요하다.
@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional // 트랜잭션 상태로 테스트를 하므로 테스트 끝나면 롤백을 해준다. 테스트는 항상 롤백을 해줘야 한다. 테스트는 항상 독립적으로 실행되어야 한다.
// 물론 서비스 등 다른 곳에 @Transactional을 사용하면 롤백을 해주지 않는다.
public class MemberServiceIntegrationTest {

    // 테스트는 그냥 편한 방법을 사용하면 된다. autowire를 사용하면 편하다.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    // transactional을 사용하면 아래 코드가 필요없다.
//    @AfterEach
//    public void afterEach() {
//        memberRepository.clearStore();
//    }

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hello100");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring3");

        Member member2 = new Member();
        member2.setName("spring3");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다.", e.getMessage());

        // then
    }

    @Test
    void findMembers() {

    }

    @Test
    void findOne() {
    }
}
