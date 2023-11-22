package umc.velog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.velog.domain.entity.Comment;
import umc.velog.dto.comment.CommentRequestDto;
import umc.velog.dto.comment.CommentResponseDto;
import umc.velog.security.SecurityUtil;
import umc.velog.service.CommentService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public HttpStatus saveComment(@PathVariable Long boardId
            ,@RequestBody CommentRequestDto commentRequestDto){

        if(!SecurityUtil.isLoginStatus()){
            return HttpStatus.UNAUTHORIZED;
        }

        commentService.addCommentToBoard(boardId, commentRequestDto);
        return HttpStatus.CREATED;
    }

    @GetMapping("/{boardId}")
    public List<CommentResponseDto> getCommentsByBoardId(@PathVariable Long boardId) {
        return commentService.getCommentByBoardId(boardId);
    }
}