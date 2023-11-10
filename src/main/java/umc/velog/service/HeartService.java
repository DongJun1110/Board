package umc.velog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Heart;
import umc.velog.domain.entity.Member;
import umc.velog.dto.heart.HeartRequestDto;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.HeartRepository;
import umc.velog.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void insert(HeartRequestDto heartRequestDto) throws Exception{

        Member member = memberRepository.findById(heartRequestDto.getMemberId())
                .orElseThrow(() -> new Exception("Not found member id : " + heartRequestDto.getMemberId()));
        Board board = boardRepository.findById(heartRequestDto.getBoardId())
                .orElseThrow(() -> new Exception("Not found board id : " + heartRequestDto.getBoardId()));

        if (heartRepository.findByMemberAndBoard(member, board).isPresent()) {
            throw new Exception();
        }

        Heart heart = Heart.builder()
                .board(board)
                .member(member)
                .build();
        heartRepository.save(heart);
    }

    @Transactional
    public void delete(HeartRequestDto heartRequestDTO) throws Exception {

        Member member = memberRepository.findById(heartRequestDTO.getMemberId())
                .orElseThrow(() -> new Exception("Could not found member id : " + heartRequestDTO.getMemberId()));

        Board board = boardRepository.findById(heartRequestDTO.getBoardId())
                .orElseThrow(() -> new Exception("Could not found board id : " + heartRequestDTO.getBoardId()));

        Heart heart = heartRepository.findByMemberAndBoard(member, board)
                .orElseThrow(() -> new Exception("Could not found heart id"));

        heartRepository.delete(heart);
    }

}
