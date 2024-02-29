package com.multirkh.study_validation_mail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "authority", uniqueConstraints = {
        @UniqueConstraint(
                name = "NAME_UNIQUE",
                columnNames = {"NAME"}
        )
})
public class Authority {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "authority_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "authority")
    private List<UserAuthority> userAuthorityList = new ArrayList<>();

    public Authority(String name) {
        this.name = name;
    }
}
