package umc.velog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.velog.dto.board.BoardDto;
import umc.velog.dto.board.BoardRequestDto;
import umc.velog.dto.board.BoardResponseDto;
import umc.velog.dto.member.MemberDto;
import umc.velog.service.BoardService;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 전체 글 보기 페이지(홈)
    @GetMapping("")
    @ResponseBody
    public List<BoardDto> list() {
        List<BoardDto> boardList = boardService.getBoardList();
        System.out.println("boardList = " + boardList);
        return boardList;
    }

    // 상세 글 보기(게시글 페이지 이동)
    @GetMapping("/{boardId}")
    @ResponseBody
    public BoardResponseDto detail(@PathVariable("boardId") Long boardId) {
        return boardService.getPost(boardId);
    }

    @GetMapping("/write-form")
    public String write() {
        return "board/write-form";
    }

    @PostMapping("/write-form")
    @ResponseBody
    public void write(@RequestPart("data") BoardRequestDto boardRequestDto,
                      @RequestPart(required = false) MultipartFile image){
        boardService.savePost(boardRequestDto, image);
    }

    @GetMapping("/{userId}/profile")
    @ResponseBody
    public List<MemberDto> getBoardByUserId(@PathVariable String userId) {
        return boardService.getBoardByUserId(userId);
    }
}