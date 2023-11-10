package umc.velog.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.dto.BoardDto;
import umc.velog.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boardEntitys = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board boardEntity : boardEntitys) {
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
        // Dto to entity
        Board entity = Board.toEntity(boardDto);
        logger.info("To Entity : " + entity.toString());

        // save
        Board saveEntity = boardRepository.save(entity);
        logger.info("save Entity : " + saveEntity.toString());

        // Entity To Dto
        BoardDto dto = BoardDto.toDto(saveEntity);
        logger.info("To dto : " + dto.toString());

        return dto;

    }

    // 게시글 좋아요 서비스 추가 필요
}
