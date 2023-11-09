package umc.velog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import umc.velog.dto.BoardDto;
import umc.velog.service.BoardService;

import java.util.List;

@Controller
@RequestMapping ("/board") // "board"인지, "/board"인지
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    // 전체 글 보기 페이지(홈)
    @GetMapping("")
    public String list(Model model) {
        List<BoardDto> boardList = boardService.getBoardList();
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    // 상세 글 보기(게시글 페이지 이동)
    @GetMapping("/{postId}")
    public String detail(@PathVariable("postId") Long postId) {
        BoardDto boardDto = boardService.getPost(postId);

        return "board/detail";
    }


    // 글쓰기 페이지
    @GetMapping("/write-form")
    public String write() {
        return "board/write-from";
    }

    // 글쓰기 뒤 POST로 DB에 저장
    // 글쓰기 뒤 /list 경로로 리디렉션
    @PostMapping("/write-from")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto); // 게시글 작성 서비스 메소드 호출
        return "redirect:/board/list";
    }
}
