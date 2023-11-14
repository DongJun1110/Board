package umc.velog.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Comment;
import umc.velog.domain.entity.Member;
import umc.velog.dto.board.BoardDto;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public BoardService(BoardRepository boardRepository, MemberRepository memberRepository) {
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board boardEntity : boardEntities) {
            boardDtoList.add(BoardDto.toDto(boardEntity));
        }
        return boardDtoList;
    }

    @Transactional
    public BoardDto getPost(Long postId) {
        Optional<Board> boardWrapper = boardRepository.findById(postId);
        Board board = boardWrapper.get();

        return BoardDto.toDto(board);
    }

    @Transactional
    public BoardDto savePost(BoardDto boardDto) {
        
        Board entity = Board.toEntity(boardDto);

        Board saveEntity = boardRepository.save(entity);

        BoardDto dto = BoardDto.toDto(saveEntity);

        return dto;
    }

    @Transactional
    public List<BoardDto> getBoardByMemberId(Long memberId) {
        List<Board> boardEntitys = boardRepository.findAllByWriterId(memberId);
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board boardEntity : boardEntitys) {
            boardDtoList.add(BoardDto.toDto(boardEntity));
        }
        System.out.println("boardDtoList = " + boardDtoList);
        return boardDtoList;
    }
}
