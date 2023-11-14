package umc.velog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import umc.velog.dto.board.BoardDto;
import umc.velog.dto.heart.HeartDto;
import umc.velog.dto.member.MemberDto;
import umc.velog.service.BoardService;
import umc.velog.service.CommentService;
import umc.velog.service.HeartService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final HeartService heartService;
    private final CommentService commentService;

    @Autowired
    public BoardController(BoardService boardService, CommentService commentService, HeartService heartService) {
        this.boardService = boardService;
        this.commentService = commentService;
        this.heartService = heartService;
    }


    // 전체 글 보기 페이지(홈)
    @GetMapping("")
    public List<BoardDto> list() {
        List<BoardDto> boardList = boardService.getBoardList();
        System.out.println("boardList = " + boardList);
        return boardList;
    }

    // 상세 글 보기(게시글 페이지 이동)
    @GetMapping("/{boardId}")
    public BoardDto detail(@PathVariable("boardId") Long boardId, Model model) {
        return boardService.getPost(boardId);
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

    @PostMapping("/heart")
    public HeartDto insert(@RequestBody HeartDto heartDto) throws Exception {
        heartService.insert(heartDto);
        return heartDto;
    }

    @DeleteMapping("/heart")
    public HeartDto delete(@RequestBody HeartDto heartDto) throws Exception {
        heartService.delete(heartDto);
        return heartDto;
    }

    @GetMapping("/{memberId}/profile")
    public List<BoardDto> getBoardByMemberId(@PathVariable Long memberId) {
        return boardService.getBoardByMemberId(memberId);
    }


}
