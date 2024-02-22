package com.multirkh.study_validation_mail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Id @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    @Column(unique = true)
    private String name;

    public Authority(String name) {
        this.name = name;
    }
}
