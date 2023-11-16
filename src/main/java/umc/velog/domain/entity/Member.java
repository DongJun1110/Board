package umc.velog.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "WRITER_ID", nullable = false)
        private Long id;

        @Column(name = "USERNAME", nullable = false)
        private String username;

        @Column
        private String userId;

        @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
        private List<Board> boards = new ArrayList<>();

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String email;

        @Temporal(TemporalType.TIMESTAMP)
        private Date createdDate;

        @Enumerated(EnumType.STRING)
        private Role role;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            System.out.println("entity " + authorities);
            authorities.add(new SimpleGrantedAuthority(role.toString()));
            System.out.println("entity " + authorities);
            return authorities;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }

    }
