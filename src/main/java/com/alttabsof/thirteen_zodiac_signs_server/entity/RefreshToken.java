package com.alttabsof.thirteen_zodiac_signs_server.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name ="token")
    private String token;

    @Column(name = "expired_date")
    private Date expiredDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public RefreshToken(String token, Date expiredDate, User user) {
        this.token = token;
        this.expiredDate = expiredDate;
        this.user = user;
    }

    public RefreshToken() {

    }
}
