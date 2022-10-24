package site.metacoding.white.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.BoardReqDto.BoardSaveDto;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController // json 리턴 / 플러터로 뷰 만들거라서 템플릿 엔진 연결할 필요 X
public class BoardApiController {

    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping("/v2/board/{id}")
    public String findByIdV2(@PathVariable Long id) {
        System.out.println("현재 open-in-view는 true 인가 false 인가 생각해보기!!");
        Board boardPS = boardService.findById(id);
        System.out.println("board.id : " + boardPS.getId());
        System.out.println("board.title : " + boardPS.getTitle());
        System.out.println("board.content : " + boardPS.getContent());
        System.out.println("open-in-view가 false이면 Lazy 로딩 못함");

        // 날라감)
        return "ok";
    }

    @PostMapping("/v2/board")
    public String saveV2(@RequestBody BoardSaveDto boardSaveDto) { // json으로 받기 위해서 @RequestBody
        User principal = (User) session.getAttribute("principal");
        // insert into board(title,content,user_id) values(?,?,?)
        boardSaveDto.setUser(principal);
        boardService.save(boardSaveDto); // 서비스의 sava 호출
        return "ok";
    }

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

    // @PostMapping("/board")
    // public String save(@RequestBody Board board) { // json으로 받기 위해서 @RequestBody
    // boardService.save(board); // 서비스의 sava 호출
    // return "ok";
    // }

    @DeleteMapping("/board/{id}")
    public String deleteById(@PathVariable Long id) {
        boardService.deleteById(id);
        return "ok";
    }
}
