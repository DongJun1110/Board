package umc.velog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.velog.domain.entity.Comment;
import umc.velog.dto.comment.CommentDto;
import umc.velog.dto.comment.RequestCommentDto;
import umc.velog.security.SecurityUtil;
import umc.velog.service.CommentService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}")
    @ResponseBody
    public String saveComment(HttpServletRequest request,
                              HttpServletResponse response,
                              @PathVariable Long boardId
            ,@RequestBody RequestCommentDto requestcommentDto) throws IOException {

        if(!SecurityUtil.isLoginStatus()){
            response.sendRedirect("/auth/loginPage");
        }

        commentService.addCommentToBoard(boardId, requestcommentDto);
        return request.getRequestURI();
    }

    @GetMapping("/{boardId}")
    @ResponseBody
    public List<Comment> getCommentsByBoardId(@PathVariable Long boardId) {
        return commentService.getCommentByBoardId(boardId);
    }
}