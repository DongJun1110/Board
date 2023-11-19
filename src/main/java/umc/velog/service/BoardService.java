package umc.velog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Comment;
import umc.velog.domain.entity.Member;
import umc.velog.dto.board.BoardDto;
import umc.velog.dto.board.BoardRequestDto;
import umc.velog.dto.board.BoardResponseDto;
import umc.velog.dto.member.MemberDto;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.MemberRepository;
import umc.velog.security.SecurityUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final S3Upload s3Upload;

    @Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boardEntities = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (Board boardEntity : boardEntities) {
            BoardDto boardDto = BoardDto.toDto(boardEntity);
            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }


    @Transactional
    public BoardResponseDto getPost(Long postId) {
        Optional<Board> boardWrapper = boardRepository.findById(postId);
        Board board = boardWrapper.get();

        List<Comment> comments = board.getComments();

        BoardResponseDto boardResponseDto = new BoardResponseDto();

        boardResponseDto.setId(board.getId());
        boardResponseDto.setUserName(board.getWriter().getUsername());
        boardResponseDto.setPostImg(board.getPostImg());
        boardResponseDto.setTitle(board.getTitle());
        boardResponseDto.setContent(board.getContent());
        boardResponseDto.setLikeCount(board.getLikeCount());
        boardResponseDto.setCreatedDate(board.getCreatedDate());
        boardResponseDto.setComments(comments);

        Long nextBoardId = (board.getId()+1);
        Optional<Board> nextBoard = boardRepository.findById(nextBoardId);

        if(nextBoard.isPresent()){
            boardResponseDto.setNextBoardId(nextBoardId);
            boardResponseDto.setNextBoardTitle(nextBoard.get().getTitle());
        }else{
            boardResponseDto.setNextBoardId(null);
            boardResponseDto.setNextBoardTitle(null);
        }

        return boardResponseDto;
    }

    @Transactional
    public Long savePost(BoardRequestDto boardRequestDto, MultipartFile image){

        String postImg = null; //url받을 변수를 초기화

        if (!image.isEmpty()) {//매개변수로 받은 값이 있으면
            try {
                postImg = s3Upload.uploadFiles(image, "images");//dir name : images에 mulipartfile을 올린다
                System.out.println(postImg);// 확인 차 로그에 찍기..
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Get the username of the currently authenticated user
            String userId = SecurityUtil.getCurrentMemberId().getUserId();
            System.out.println("savePost안 userId = " + userId);

            // userId를 사용할 수 있게 되었으므로 로직에서 활용
            Optional<Member> writerOptional = memberRepository.findByUserId(userId);
            if (writerOptional.isPresent()) {
                Member writer = writerOptional.get();
                System.out.println("savePost 안 writer = " + writer);

                Board board = new Board();
                board.setTitle(boardRequestDto.getTitle());
                board.setContent(boardRequestDto.getContent());
                board.setCreatedDate(new Date());
                board.setComments(null);
                board.setPostImg(postImg);
                board.setWriter(writer);

                // 글의 작성자를 현재 인증된 사용자로 설정
                boardRepository.save(board);
                return board.getId();
            } else {
                // 작성자를 찾을 수 없는 경우 처리
                System.out.println("userId에 해당하는 작성자를 찾을 수 없습니다: " + userId);
                // 예외를 throw하거나 적절한 방식으로 처리할 수 있습니다
            }
        } else {
            // 사용자가 인증되어 있지 않은 경우 처리
            // 예외를 throw하거나 메시지를 기록하는 등의 방법으로 처리할 수 있습니다
            System.out.println("사용자가 인증되어 있지 않습니다");
        }
        return null;
    }

    @Transactional
    public List<MemberDto> getBoardByUserId(String userId) {
        Optional<Member> memberOptional = memberRepository.findByUserId(userId);
        List<MemberDto> memberDtoList = new ArrayList<>();

        // Optional이 존재하는 경우에만 로직을 수행
        memberOptional.ifPresent(memberEntity -> {
            memberDtoList.add(MemberDto.toDto(memberEntity));
            System.out.println("memberDtoList = " + memberDtoList);
        });

        return memberDtoList;
    }


}
