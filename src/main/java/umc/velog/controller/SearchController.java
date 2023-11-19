package umc.velog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import umc.velog.domain.entity.Board;
import umc.velog.dto.search.SearchResponseDto;
import umc.velog.service.SearchService;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    @GetMapping("/boards/search")
    @ResponseBody
    public List<SearchResponseDto> search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<SearchResponseDto> foundList = searchService.search(keyword);
        model.addAttribute("foundList",foundList);
        return foundList;
       }
}
