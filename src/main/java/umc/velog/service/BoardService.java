package umc.velog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Member;
import umc.velog.dto.board.BoardDto;
import umc.velog.dto.member.MemberDto;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.MemberRepository;

import java.io.IOException;
import java.util.ArrayList;
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
        return boardEntities.stream()
                .map(BoardDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardDto getPost(Long postId) {
        Optional<Board> boardWrapper = boardRepository.findById(postId);
        Board board = boardWrapper.get();

        return BoardDto.toDto(board);
    }

    @Transactional
    public BoardDto savePost(BoardDto boardDto, MultipartFile image) {

        String postImg = null; //url받을 변수를 초기화

        if (!image.isEmpty()) {//매개변수로 받은 값이 있으면
            try {
                postImg = s3Upload.uploadFiles(image, "images");//dir name : images에 mulipartfile을 올린다
                System.out.println(postImg);// 확인 차 로그에 찍기..
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        Board boardEntity = Board.toEntity(boardDto);

        boardEntity.setPostImg(postImg);

        System.out.println("boardEntity.getPostImg() = " + boardEntity.getPostImg());

        boardRepository.save(boardEntity);

        return BoardDto.toDto(boardEntity);
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
