package umc.velog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
            boardDtoList.add(BoardDto.toDto(boardEntity));
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
    public void savePost(BoardRequestDto boardRequestDto, MultipartFile image, Member writer){

        String postImg = null; //url받을 변수를 초기화

        if (!image.isEmpty()) {//매개변수로 받은 값이 있으면
            try {
                postImg = s3Upload.uploadFiles(image, "images");//dir name : images에 mulipartfile을 올린다
                System.out.println(postImg);// 확인 차 로그에 찍기..
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Board board = new Board();
        board.setWriter(writer);
        board.setTitle(boardRequestDto.getTitle());
        board.setContent(boardRequestDto.getContent());
        board.setCreatedDate(new Date());
        board.setComments(null);
        board.setPostImg(postImg);

        boardRepository.save(board);
    }

    @Transactional
    public List<MemberDto> getBoardByMemberId(Long writeId) {
        List<Member> memberEntities = memberRepository.findAllById(writeId);
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (Member memberEntity : memberEntities) {
            memberDtoList.add(MemberDto.toDto(memberEntity));
        }
        System.out.println("memberDtoList = " + memberDtoList);
        return memberDtoList;
    }

}
