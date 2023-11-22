package umc.velog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Heart;
import umc.velog.domain.entity.Member;
import umc.velog.dto.heart.HeartRequestDto;
import umc.velog.dto.heart.HeartResponseDto;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.HeartRepository;
import umc.velog.repository.MemberRepository;
import umc.velog.security.SecurityUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public HeartResponseDto insert(HeartRequestDto heartRequestDto) throws Exception{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Board board = boardRepository.findById(heartRequestDto.getBoardId())
                .orElseThrow(() -> new Exception("Not found board id : " + heartRequestDto.getBoardId()));

        if (authentication != null && authentication.isAuthenticated()) {

            String userId = SecurityUtil.getCurrentMemberId().getUserId();
            Optional<Member> memberOptional = memberRepository.findByUserId(userId);

            HeartResponseDto heartResponseDto = new HeartResponseDto();

            if (memberOptional.isPresent()) {

                Member member = memberOptional.get();

                heartResponseDto.setUserName(member.getUsername());
                heartResponseDto.setBoardId(board.getId());

                if (heartRepository.findByMemberAndBoard(member, board).isPresent()) {
                    heartResponseDto.setIsPressed(true);
                    return heartResponseDto;
                }else heartResponseDto.setIsPressed(false);

                Heart heart = Heart.builder()
                        .board(board)
                        .member(member)
                        .build();

                board.setLikeCount(board.getLikeCount() + 1);
                heartRepository.save(heart);
                boardRepository.save(board);

                return heartResponseDto;

            } else {
                System.out.println("userId에 해당하는 작성자를 찾을 수 없습니다: " + userId);
            }
        } else {
            // 사용자가 인증되어 있지 않은 경우 처리
            // 예외를 throw하거나 메시지를 기록하는 등의 방법으로 처리할 수 있습니다
            System.out.println("사용자가 인증되어 있지 않습니다");
        }
        return null;
    }

    @Transactional
    public HeartResponseDto delete(HeartRequestDto heartRequestDto) throws Exception {

        String userId = SecurityUtil.getCurrentMemberId().getUserId();
        Optional<Member> memberOptional = memberRepository.findByUserId(userId);
        Member member = memberOptional.get();

        Board board = boardRepository.findById(heartRequestDto.getBoardId())
                .orElseThrow(() -> new Exception("Could not found board id : " + heartRequestDto.getBoardId()));

        Heart heart = heartRepository.findByMemberAndBoard(member, board)
                .orElseThrow(() -> new Exception("Could not found heart id"));

        heartRepository.delete(heart);
        board.setLikeCount(board.getLikeCount() - 1);
        boardRepository.save(board);

        HeartResponseDto heartResponseDto = new HeartResponseDto();
        heartResponseDto.setIsPressed(false);
        heartResponseDto.setUserName(member.getUsername());
        heartResponseDto.setBoardId(board.getId());

        return heartResponseDto;
    }

}
