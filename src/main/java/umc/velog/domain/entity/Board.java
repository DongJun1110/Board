package umc.velog.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import umc.velog.dto.BoardDto;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "WRITER_ID")
    private Member writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate modifiedDate;

    private int likeCount;
    private int viewCount;

    public static Board toEntity(BoardDto boardDto) {
        return Board.builder()
                .id(boardDto.getId())
                .writer(boardDto.getWriter())
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .createdDate(boardDto.getCreatedDate())
                .modifiedDate(boardDto.getModifiedDate())
                .likeCount(boardDto.getLikeCount())
                .viewCount(boardDto.getViewCount())
                .build();

    }
}
