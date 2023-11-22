package umc.velog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.velog.dto.board.BoardDto;
import umc.velog.dto.board.BoardRequestDto;
import umc.velog.dto.board.BoardResponseDto;
import umc.velog.dto.member.MemberDto;
import umc.velog.security.SecurityUtil;
import umc.velog.service.BoardService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    // 전체 글 보기 페이지(홈)
    @GetMapping("")
    public List<BoardDto> list() {
        List<BoardDto> boardList = boardService.getBoardList();
        return boardList;
    }

    // 상세 글 보기(게시글 페이지 이동)
    @GetMapping("/{boardId}")
    public BoardResponseDto detail(@PathVariable("boardId") Long boardId) {
        return boardService.getPost(boardId);
    }

    @PostMapping("/write-form")
    public ResponseEntity<Map<String, Object>> write(@RequestPart("data") BoardRequestDto boardRequestDto,
                                                     @RequestPart(required = false) MultipartFile image) throws IOException {

        if(!SecurityUtil.isLoginStatus()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Long boardId = boardService.savePost(boardRequestDto, image);
        String userId = SecurityUtil.getCurrentMemberId().getUserId();

        Map<String, Object> response = new HashMap<>();
        response.put("boardId", boardId);
        response.put("userId", userId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/profiles")
    public List<MemberDto> getBoardByUserId(@PathVariable String userId) {
        return boardService.getBoardByUserId(userId);
    }
}