package umc.velog.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.dto.board.BoardDto;
import umc.velog.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
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
}
