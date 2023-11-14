    package umc.velog.domain.entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.hibernate.annotations.CreationTimestamp;

    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Member {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "WRITER_ID", nullable = false)
        private Long id;

        @Column(name = "USERNAME", nullable = false)
        private String username;

        @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
        private List<Board> boards = new ArrayList<>();

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String email;

        @Temporal(TemporalType.TIMESTAMP)
        private Date createdDate;

    }
