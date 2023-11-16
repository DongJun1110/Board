package umc.velog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.dto.search.SearchResponseDto;
import umc.velog.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final BoardRepository boardRepository;

    List<SearchResponseDto> searchList = new ArrayList<>();
    @Transactional
    public List<SearchResponseDto> search(String keyword) {
        List<Board> byTitleContaining = boardRepository.findByTitleContaining(keyword);
        for (Board board : byTitleContaining) {
            SearchResponseDto result = new SearchResponseDto();
            result.setPostImg(board.getPostImg());
            result.setUserName(board.getWriter().getUsername());
            result.setCommentsCount(board.getComments().size());
            result.setLikeCount(board.getLikeCount());
            result.setCreatedDate(board.getCreatedDate());

            searchList.add(result);
        }
        return searchList;
    }
}
