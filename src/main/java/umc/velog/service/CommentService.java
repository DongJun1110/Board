package umc.velog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Comment;
import umc.velog.domain.entity.Member;
import umc.velog.dto.comment.CommentDto;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.CommentRepository;
import umc.velog.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Comment addCommentToBoard(Long boardId, Long memberId, CommentDto commentDto) {
        Board board = boardRepository.findById(boardId).orElse(null);
        Member writer = memberRepository.findById(memberId).orElse(null);

        if (board != null) {
            Comment comment = new Comment();
            comment.setContent(commentDto.getContent());
            comment.setCreatedAt(new Date());
            comment.setBoard(board);
            comment.setWriter(writer);

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
