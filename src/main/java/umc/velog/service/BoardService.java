package umc.velog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.dto.BoardDto;
import umc.velog.repository.BoardRepository;

import java.util.List;

@Service
public class BoardService {
    private BoardRepository boardRepository;
    @Transactional
    public List<BoardDto> getBoardList() {

    }

    @Transactional
    public BoardDto getPost(Long postId) {

    }

    @Transactional
    public BoardDto savePost(BoardDto boardDto) {
        // Dto to entity
        Board entity = Board.toEntity(boardDto);

        // save
        Board saveEntity = boardRepository.save(entity);

        // Entity To Dto
        BoardDto dto = BoardDto.toDto(saveEntity);

        return dto;

    }

    // 게시글 좋아요 서비스 추가 필요
}
