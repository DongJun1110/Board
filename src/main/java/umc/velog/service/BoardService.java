package umc.velog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Member;
import umc.velog.dto.board.BoardDto;
import umc.velog.dto.member.MemberDto;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

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
    public BoardDto getPost(Long postId) {
        Optional<Board> boardWrapper = boardRepository.findById(postId);
        Board board = boardWrapper.get();

        return BoardDto.toDto(board);
    }

    @Transactional
    public BoardDto savePost(BoardDto boardDto) {
        
        Board entity = Board.toEntity(boardDto);

        boardRepository.save(entity);

        return BoardDto.toDto(entity);
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
