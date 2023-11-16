package umc.velog.dto.search;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class SearchResponseDto {

    private String postImg;
    private String userName;
    private int commentsCount;
    private Date createdDate;
    private int likeCount;

}
