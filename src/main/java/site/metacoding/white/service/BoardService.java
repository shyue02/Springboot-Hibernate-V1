package site.metacoding.white.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;

@RequiredArgsConstructor // 생성자 주입 / 없으면 디폴트생성자
@Service // -> 붙여야 IoC에 뜬다
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional // -> 트랜잭션을 걸어줘야 된다
    public void save(Board board) {
        boardRepository.save(board); // 얘가 save를 board로 한다면 컨트롤러한테 보드 받아야한다, 컨트롤러는 클라이언트한테 받아야 한다
    }

    public Board findById(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional // select가 아니니까 트랜잭션을 걸어줘야
    public void update(Long id, Board board) {// board-클라이언트한테 넘겨받은 데이터 (원래는 엔티티가 아니라 Dto 적어야함)
        Board boardPS = boardRepository.findById(id); // boardPs-db에서 select해서 조회한 데이터
        boardPS.setTitle(board.getTitle());
        boardPS.setContent(board.getContent());
        boardPS.setAuthor(board.getAuthor()); // 핵심 - 영속화된 데이터(boardPS)를 클라이언트한테 받은 데이터로 수정
    } // 트랜잭션 종료시 -> 더티체킹을 함 / 더티체킹:더러운 것들을 모아뒀다가 한 번에 flush

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}
