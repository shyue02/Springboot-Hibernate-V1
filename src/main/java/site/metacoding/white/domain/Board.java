package site.metacoding.white.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Board {
    @Id // id가 pk라는 것을 알려준다
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 오토 인크리드먼트 / 자바 코드로 테이블을 자동으로 생성한다
    private Long id; // pk
    private String title;
    @Column(length = 1000) // 길이 지정
    private String content;

    // FK가 만들어짐. user_id
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

}
