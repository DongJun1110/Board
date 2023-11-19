package umc.velog.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "WRITER_ID")
        private Long id;

        @Column(nullable = false)
        private String username;

        @Column(nullable = false)
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
            System.out.println("getAuthorites안 entity " + authorities);
            authorities.add(new SimpleGrantedAuthority(role.toString()));
            System.out.println("getAuthorites안 entity " + authorities);
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
