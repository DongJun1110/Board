package umc.velog.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import umc.velog.dto.comment.CommentDto;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    //Board하고 Member 엔티티를 참조(둘 다 일대다 관계)
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "WRITER_ID")
    private Member writer;

    public static Comment toEntity(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .createdAt(commentDto.getCreatedAt())
                .board(commentDto.getBoard())
                .writer(commentDto.getWriter())
                .build();
    }

}
