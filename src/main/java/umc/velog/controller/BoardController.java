package umc.velog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import umc.velog.domain.entity.Comment;
import umc.velog.dto.board.BoardDto;
import umc.velog.service.BoardService;
import umc.velog.service.CommentService;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @Autowired
    public BoardController(BoardService boardService, CommentService commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
    }

    // 전체 글 보기 페이지(홈)
    @GetMapping("")
    public String list(Model model) {
        List<BoardDto> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        System.out.println("boardList = " + boardList);
        return "board/list";
    }

    // 상세 글 보기(게시글 페이지 이동)
    @GetMapping("/{postId}")
    public String detail(@PathVariable("postId") Long postId, Model model) {
        BoardDto boardDto = boardService.getPost(postId);
        model.addAttribute("board", boardDto);

        return "board/detail";
    }

    // 글쓰기 페이지
    @GetMapping("/write-form")
    public String write() {
        return "board/write-from";
    }

    // 글쓰기 뒤 POST로 DB에 저장
    // 글쓰기 뒤 /list 경로로 리디렉션
    @PostMapping("/write-form")
    @ResponseBody
    public String write(@RequestBody BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/board/list";
    }

    @PostMapping("/{boardId}/comments")
    @ResponseBody
    public Comment addCommentToBoard(@PathVariable Long boardId,
                                     @RequestBody String content) {
        return commentService.addCommentToBoard(boardId, content);
    }

    @GetMapping("/{boardId}/comments")
    @ResponseBody
    public List<Comment> getCommentsByBoardId(@PathVariable Long boardId) {
        return commentService.getCommentByBoardId(boardId);
    }

}
