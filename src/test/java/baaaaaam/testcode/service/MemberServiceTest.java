package baaaaaam.testcode.service;

import baaaaaam.testcode.domain.Member;
import baaaaaam.testcode.exception.MemberException;
import baaaaaam.testcode.repository.MemberJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks //의존 Mock 객체를 주입받는 실 객체, @Spy와 비슷
    MemberService memberSerivce;

    @Mock //객체 모킹
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("회원 가입 - 정상")
    void join() {
        //given
        Member member = new Member("재진", "jj@ab.com");

        //when
        memberSerivce.join(member);

        //then
        then(memberJpaRepository).should().save(member);
    }

    @Test
    @DisplayName("회원 가입 - 이메일 중복")
    void join_duplicateEmail() {
        //given
        Member member = new Member("재진", "jj@ab.com");
        given(memberJpaRepository.findByEmail(any())).willReturn(Optional.of(member)); //메서드 모킹

        //when
        ThrowingCallable callable = () -> memberSerivce.join(member);

        //then
        assertThatThrownBy(callable).isInstanceOf(MemberException.class);
    }

    @Test
    @DisplayName("회원 조회 - 결과 있음")
    void getMember() {
        //TODO
    }

    @Test
    @DisplayName("회원 조회 - 결과 없음")
    void getMember_noExists() {
        //TODO
    }
}