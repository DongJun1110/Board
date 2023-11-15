package umc.velog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import umc.velog.dto.board.BoardDto;
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
    public String list(Model model) {
        List<BoardDto> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        System.out.println("boardList = " + boardList);
        return "board/list";
    }

    // 상세 글 보기(게시글 페이지 이동)
    @GetMapping("/{boardId}")
    public BoardDto detail(@PathVariable("boardId") Long boardId) {
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
    public BoardDto write(@RequestBody BoardDto boardDto) {
        boardService.savePost(boardDto);
        return boardDto;
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
    public List<MemberDto> getBoardByMemberId(@PathVariable Long memberId) {
        return boardService.getBoardByMemberId(memberId);
    }


}
