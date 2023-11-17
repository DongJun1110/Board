package umc.velog.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.velog.domain.entity.Comment;
import umc.velog.dto.comment.CommentDto;
import umc.velog.service.CommentService;

import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}/{memberId}")
    //@ResponseBody
    public String saveComment(HttpServletRequest request,
                              @PathVariable Long boardId,
                              @PathVariable Long memberId
            ,@RequestBody CommentDto commentDto) {
        commentService.addCommentToBoard(boardId, memberId, commentDto);
        return request.getRequestURI();
    }

    @GetMapping("/{boardId}")
    @ResponseBody
    public List<Comment> getCommentsByBoardId(@PathVariable Long boardId) {
        return commentService.getCommentByBoardId(boardId);
    }
}