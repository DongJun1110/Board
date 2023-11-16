package umc.velog.dto.board;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {

    private String title;
    private String content;

}
