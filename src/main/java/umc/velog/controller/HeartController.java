package umc.velog.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.velog.dto.heart.HeartRequestDto;
import umc.velog.dto.heart.HeartResponseDto;
import umc.velog.security.SecurityUtil;
import umc.velog.service.HeartService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

    private final HeartService heartService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody HeartResponseDto insert(@RequestBody HeartRequestDto heartRequestDto,
                                                 HttpServletResponse response) throws Exception {

        if(!SecurityUtil.isLoginStatus()){
            response.sendRedirect("/auth/loginPage");
        }

        return heartService.insert(heartRequestDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody HeartResponseDto delete(@RequestBody HeartRequestDto heartDto) throws Exception {
        return heartService.delete(heartDto);
    }

}
