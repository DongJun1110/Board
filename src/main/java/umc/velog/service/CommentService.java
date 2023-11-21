package umc.velog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Comment;
import umc.velog.domain.entity.Member;
import umc.velog.dto.comment.CommentRequestDto;
import umc.velog.dto.comment.CommentResponseDto;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.CommentRepository;
import umc.velog.repository.MemberRepository;
import umc.velog.security.SecurityUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Comment addCommentToBoard(Long boardId, CommentRequestDto requestCommentDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {

            String userId = SecurityUtil.getCurrentMemberId().getUserId();
            Optional<Member> foundMember = memberRepository.findByUserId(userId);

            Board board = boardRepository.findById(boardId).orElse(null);

            if(foundMember.isPresent()){
                Member writer = foundMember.get();
                if(board != null){
                    Comment comment = new Comment();
                    comment.setContent(requestCommentDto.getContent());
                    comment.setCreatedAt(new Date());
                    comment.setBoard(board);
                    comment.setWriter(writer);
                    return commentRepository.save(comment);
                }
            }
        }
        return null;
    }

    @Transactional
    public List<CommentResponseDto> getCommentByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);

        if (board != null) {

            CommentResponseDto commentResponseDto = new CommentResponseDto();
            List<CommentResponseDto> result = new ArrayList<>();
            List<Comment> comments = board.getComments();

            for (Comment comment : comments) {
                commentResponseDto.setId(comment.getId());
                commentResponseDto.setContent(comment.getContent());
                commentResponseDto.setCreatedAt(comment.getCreatedAt());
                commentResponseDto.setUserName(comment.getWriter().getUsername());
                result.add(commentResponseDto);
            }
            return result;
        }
        return new ArrayList<>();
    }

}