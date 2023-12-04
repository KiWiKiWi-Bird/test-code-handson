package baaaaaam.testcode.service;

import baaaaaam.testcode.domain.Member;
import baaaaaam.testcode.exception.MemberException;
import baaaaaam.testcode.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberJpaRepository memberRepository;

    @Transactional
    public void join(Member member) {
        validateBeforeJoin(member);
        memberRepository.save(member);
    }

    private void validateBeforeJoin(Member member) {
        Optional<Member> existsMember = memberRepository.findByEmail((member.getEmail()));
        if (existsMember.isPresent()) {
            throw new MemberException("이미 등록된 이메일입니다.");
        }
    }

    public Member getMember(int memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException("유효하지 않은 회원입니다."));
    }
}
