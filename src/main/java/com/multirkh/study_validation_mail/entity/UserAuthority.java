package com.multirkh.study_validation_mail.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_authority", uniqueConstraints = {
        @UniqueConstraint(
                name = "UNIQUE_USER_AUTHORITY",
                columnNames = {"user_id", "authority_id"}
        )
})
public class UserAuthority {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "user_authority_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id")
    private Authority authority;

    public UserAuthority(User user, Authority authority){
        this.user = user;
        this.authority = authority;
    }
}
