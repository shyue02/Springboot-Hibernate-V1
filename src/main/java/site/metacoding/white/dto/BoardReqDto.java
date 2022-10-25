package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

public class BoardReqDto {

    @Setter
    @Getter
    public static class BoardSaveReqDto { // 게시글쓰기 디티오
        private String title;
        private String content;
        private User user; // 서비스 로직 (서비스Dto만들지말고)
    }

    // DTO는 여기다가 추가로
}
