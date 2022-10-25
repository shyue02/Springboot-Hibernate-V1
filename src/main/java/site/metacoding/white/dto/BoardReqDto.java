package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;

public class BoardReqDto {

    @Setter
    @Getter
    public static class BoardSaveReqDto { // 게시글쓰기 디티오
        private String title;
        private String content;
        private SessionUser sessionUser; // 서비스 로직 (서비스Dto만들지말고)
    }

    // DTO는 여기다가 추가로
}
