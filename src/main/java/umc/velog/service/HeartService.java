package umc.velog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.domain.entity.Heart;
import umc.velog.domain.entity.Member;
import umc.velog.dto.heart.HeartDto;
import umc.velog.dto.user.UserInfoDto;
import umc.velog.repository.BoardRepository;
import umc.velog.repository.HeartRepository;
import umc.velog.repository.MemberRepository;
import umc.velog.security.SecurityUtil;

import java.security.Security;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public HeartDto insert(HeartDto heartDto) throws Exception{

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Board board = boardRepository.findById(heartDto.getBoardId())
                .orElseThrow(() -> new Exception("Not found board id : " + heartDto.getBoardId()));

        if (authentication != null && authentication.isAuthenticated()) {

            String userId = SecurityUtil.getCurrentMemberId().getUserId();

            Optional<Member> memberOptional = memberRepository.findByUserId(userId);
            if (memberOptional.isPresent()) {

                Member member = memberOptional.get();

                if (heartRepository.findByMemberAndBoard(member, board).isPresent()) {
                    return null;
                }

                Heart heart = Heart.builder()
                        .board(board)
                        .member(member)
                        .build();

                board.setLikeCount(board.getLikeCount() + 1);
                heartRepository.save(heart);
                boardRepository.save(board);

                return heartDto;

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
    public HeartDto delete(HeartDto heartDTO) throws Exception {

        String userId = SecurityUtil.getCurrentMemberId().getUserId();
        Optional<Member> memberOptional = memberRepository.findByUserId(userId);
        Member member = memberOptional.get();

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
