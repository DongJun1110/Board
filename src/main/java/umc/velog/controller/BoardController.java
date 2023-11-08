package umc.velog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/board") // "board"인지, "/board"인지
public class BoardController {


    @GetMapping("/write-form")
    public String write() {

    }
}
