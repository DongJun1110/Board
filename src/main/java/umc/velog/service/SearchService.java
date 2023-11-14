package umc.velog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.velog.domain.entity.Board;
import umc.velog.repository.BoardRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final BoardRepository boardRepository;
    @Transactional
    public List<Board> search(String keyword) {
        return boardRepository.findByTitleContaining(keyword);
    }
}
