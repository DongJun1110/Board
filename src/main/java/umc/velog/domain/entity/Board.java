package umc.velog.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import umc.velog.dto.board.BoardDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BOARD_ID")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Member.class)
    @JsonIgnore
    @JoinColumn(name = "WRITER_ID")
    private Member writer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ColumnDefault("0")
    private int likeCount;

    public static Board toEntity(BoardDto boardDto) {
        return Board.builder()
                .id(boardDto.getId())
                .writer(boardDto.getWriter())
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .createdDate(boardDto.getCreatedDate())
                .likeCount(boardDto.getLikeCount())
                .build();
    }

}
