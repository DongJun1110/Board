package umc.velog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Comment;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.CommentRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Comment addCommentToBoard(Long boardId, String content) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board != null) {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreatedAt(new Date());
            comment.setBoard(board);
            return commentRepository.save(comment);
        }
        return null;
    }

    @Transactional
    public List<Comment> getCommentByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        if (board != null) {
            return board.getComments();
        }
        return new ArrayList<>();
    }

}
