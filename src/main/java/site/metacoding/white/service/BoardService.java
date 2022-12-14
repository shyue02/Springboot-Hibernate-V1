package site.metacoding.white.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardReqDto.BoardUpdateReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardAllRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardDetailRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardUpdateRespDto;

// 트랜잭션 관리
// DTO 변환해서 컨트롤러에게 돌려줘야함

@Slf4j
@RequiredArgsConstructor // 생성자 주입 / 없으면 디폴트생성자
@Service // -> 붙여야 IoC에 뜬다
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional // -> 트랜잭션을 걸어줘야 된다
    public BoardSaveRespDto save(BoardSaveReqDto boardSaveReqDto) {
        // 핵심 로직
        Board boardPS = boardRepository.save(boardSaveReqDto.toEntity());

        // DTO 전환
        BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto(boardPS);

        return boardSaveRespDto;
    } // DB 커넥션을 종료

    @Transactional(readOnly = true) // 트랜잭션을 걸면 OSIV가 false여도 디비 커넥션이 유지됨.
    public BoardDetailRespDto findById(Long id) {
        Optional<Board> boardOP = boardRepository.findById(id);// 오픈 인뷰가 false니까 조회후 세션 종료
        if (boardOP.isPresent()) {
            BoardDetailRespDto boardDetailRespDto = new BoardDetailRespDto(boardOP.get());
            return boardDetailRespDto; // Lazy 로딩됨. (근데 Eager이면 이미 로딩되서 select 두번된다. /필드까지 때려라 가능하다
        } else {
            throw new RuntimeException("해당 " + id + "로 상세보기를 할 수 없습니다.");
        }
    }

    @Transactional // select가 아니니까 트랜잭션을 걸어줘야
    public BoardUpdateRespDto update(BoardUpdateReqDto boardUpdateReqDto) {
        Long id = boardUpdateReqDto.getId();
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            Board boardPS = boardOP.get();
            boardPS.update(boardUpdateReqDto.getTitle(), boardUpdateReqDto.getContent());
            return new BoardUpdateRespDto(boardPS);
        } else {
            throw new RuntimeException("해당 " + id + "로 수정을 할 수 없습니다.");
        }
        // 핵심 - 영속화된 데이터(boardPS)를 클라이언트한테 받은 데이터로 수정
    } // 트랜잭션 종료시 -> 더티체킹을 함 / 더티체킹:더러운 것들을 모아뒀다가 한 번에 flush

    @Transactional(readOnly = true)
    public List<BoardAllRespDto> findAll() {
        // 1. List의 크기만큼 for문 돌리기
        // 2. Board -> Dto로 옮겨야 함
        // 3. Dto를 List에 담기
        List<Board> boardList = boardRepository.findAll();

        List<BoardAllRespDto> boardAllRespDtoList = new ArrayList<>();
        for (Board board : boardList) {
            boardAllRespDtoList.add(new BoardAllRespDto(board));
        }

        return boardAllRespDtoList;
    }

    // return boardRepository.findAll()
    // .stream().map((board) -> new
    // BoardAllRespDto(board)).collect(Collectors.toList());

    // delete는 리턴 안함.

    @Transactional
    public void deleteById(Long id, Long userId) {
        Optional<Board> boardOP = boardRepository.findById(id);
        log.debug("보드서비스 : 1");
        if (boardOP.isPresent()) {
            Board boardPS = boardOP.get();
            log.debug("보드서비스 : 2");
            if (boardPS.getUser().getId() != userId) {
                throw new RuntimeException("해당 게시글을 삭제할 권한이 없습니다.");
            }
            log.debug("보드서비스 : 3");
            boardRepository.deleteById(id);
            log.debug("보드서비스 : 4");
        } else {
            throw new RuntimeException("해당 " + id + "로 삭제를 할 수 없습니다.");
        }
    }
}
