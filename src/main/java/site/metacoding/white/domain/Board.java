package site.metacoding.white.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // 조회를 위해서만 필요함
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    @Builder
    public Board(Long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    // 변경하는 코드는 의미 있게 메소드로 구현
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
