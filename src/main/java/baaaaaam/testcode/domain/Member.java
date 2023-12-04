package baaaaaam.testcode.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    private Integer memberId;

    private String memberName;
    private String email;

    public Member(String memberName, String email) {
        this.memberName = memberName;
        this.email = email;
    }
}