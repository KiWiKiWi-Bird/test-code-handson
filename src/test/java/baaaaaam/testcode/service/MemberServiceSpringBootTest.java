package baaaaaam.testcode.service;

import baaaaaam.testcode.domain.Member;
import baaaaaam.testcode.exception.MemberException;
import baaaaaam.testcode.repository.MemberJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;

@SpringBootTest
@Transactional //@SpringBootTest와 함께 사용하면, 기본 동작이 Rollback임
class MemberServiceSpringBootTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberJpaRepository memberRepository;

    //TC(Test Case) - 테스트 케이스
    @Test
    @DisplayName("회원 가입 - 정상")
    void join() {
        //BDD 패턴 - given, when, then
        //given: TC 준비
        Member member = new Member("재진", "jj@ab.com");

        //when: TC 수행
        memberService.join(member);

        //then: TC 검증
        assertThat(member.getMemberId()).isPositive();
        Optional<Member> result = memberRepository.findById(member.getMemberId());
        assertThat(result).isPresent();
        assertThat(result.get().getMemberName()).isEqualTo(member.getMemberName());
        assertThat(result.get().getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("회원 가입 - 이메일 중복")
    void join_duplicateEmail() {
        //given: TC 준비
        Member member = new Member("재진", "jj@ab.com");
        memberRepository.save(member);

        //when: TC 수행
        ThrowingCallable callable = () -> memberService.join(member);

        //then: TC 검증
        assertThatThrownBy(callable).isInstanceOf(MemberException.class);
    }

    @Test
    @DisplayName("회원 조회 - 결과 있음")
    void getMember() {
        //given
        Member member = new Member("재진", "jj@ab.com");
        memberRepository.save(member);

        //when
        Member result = memberService.getMember(member.getMemberId());

        //then
        assertThat(result.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(result.getMemberName()).isEqualTo(member.getMemberName());
    }

    @Test
    @DisplayName("회원 조회 - 결과 없음")
    void getMember_noExists() {
        //when
        int memberId = -999;
        ThrowingCallable callable = () -> memberService.getMember(memberId);

        //then
        assertThatThrownBy(callable).isInstanceOf(MemberException.class);
    }
}