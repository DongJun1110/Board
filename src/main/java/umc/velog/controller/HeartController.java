package umc.velog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umc.velog.dto.heart.HeartRequestDto;
import umc.velog.security.SecurityUtil;
import umc.velog.service.HeartService;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/hearts")
public class HeartController {

    private final HeartService heartService;

    @PostMapping
    public HttpStatus insert(@RequestBody HeartRequestDto heartRequestDto) throws Exception {

        if(!SecurityUtil.isLoginStatus()){
            return HttpStatus.UNAUTHORIZED;
        }
        heartService.insert(heartRequestDto);
        return HttpStatus.OK;
    }

    @DeleteMapping
    public HttpStatus delete(@RequestBody HeartRequestDto heartDto) throws Exception {
        heartService.delete(heartDto);
        return HttpStatus.OK;
    }

}
