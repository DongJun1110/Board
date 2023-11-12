package umc.velog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Heart;
import umc.velog.domain.entity.Member;
import umc.velog.dto.heart.HeartDto;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.HeartRepository;
import umc.velog.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public HeartDto insert(HeartDto heartDto) throws Exception{

        Member member = memberRepository.findById(heartDto.getMemberId())
                .orElseThrow(() -> new Exception("Not found member id : " + heartDto.getMemberId()));
        Board board = boardRepository.findById(heartDto.getBoardId())
                .orElseThrow(() -> new Exception("Not found board id : " + heartDto.getBoardId()));

        if (heartRepository.findByMemberAndBoard(member, board).isPresent()) {
            throw new Exception();
        }

        Heart heart = Heart.builder()
                .board(board)
                .member(member)
                .build();
        heartRepository.save(heart);

        board.setLikeCount(board.getLikeCount() + 1);
        boardRepository.save(board);

        return heartDto;
    }

    @Transactional
    public HeartDto delete(HeartDto heartDTO) throws Exception {

        Member member = memberRepository.findById(heartDTO.getMemberId())
                .orElseThrow(() -> new Exception("Could not found member id : " + heartDTO.getMemberId()));

        Board board = boardRepository.findById(heartDTO.getBoardId())
                .orElseThrow(() -> new Exception("Could not found board id : " + heartDTO.getBoardId()));

        Heart heart = heartRepository.findByMemberAndBoard(member, board)
                .orElseThrow(() -> new Exception("Could not found heart id"));

        heartRepository.delete(heart);
        board.setLikeCount(board.getLikeCount() - 1);
        boardRepository.save(board);
        return heartDTO;
    }

}
