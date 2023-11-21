package umc.velog.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.velog.dto.heart.HeartDto;
import umc.velog.security.SecurityUtil;
import umc.velog.service.HeartService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

    private final HeartService heartService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody HeartDto insert(@RequestBody HeartDto heartDto,
                                         HttpServletResponse response) throws Exception {

        if(!SecurityUtil.isLoginStatus()){
            response.sendRedirect("/auth/loginPage");
        }
        return heartService.insert(heartDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody HeartDto delete(@RequestBody HeartDto heartDto) throws Exception {
        return heartService.delete(heartDto);
    }

}
