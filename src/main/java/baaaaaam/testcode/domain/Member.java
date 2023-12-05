package baaaaaam.testcode.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    private String memberName;

    @Column(unique = true)
    private String email;

    public Member(String memberName, String email) {
        this.memberName = memberName;
        this.email = email;
    }
}