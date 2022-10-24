package site.metacoding.white.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController // json 리턴 / 플러터로 뷰 만들거라서 템플릿 엔진 연결할 필요 X
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/board/{id}")
    public Board findById(@PathVariable Long id) {
        return boardService.findById(id);
    }

    @GetMapping("/board")
    public List<Board> findAll() {
        return boardService.findAll();
    }

    @PutMapping("/board/{id}")
    public String updata(@PathVariable Long id, @RequestBody Board board) { // 실제로는 엔티티가 아니라 dto를 적어줘야함!
        boardService.update(id, board);
        return "ok";
    }

    @PostMapping("/board")
    public String save(@RequestBody Board board) { // json으로 받기 위해서 @RequestBody
        boardService.save(board); // 서비스의 sava 호출
        return "ok";
    }

    @DeleteMapping("/board/{id}")
    public String deleteById(@PathVariable Long id) {
        boardService.deleteById(id);
        return "ok";
    }
}
