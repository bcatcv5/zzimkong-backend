package com.boostcamp.zzimkong.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    // TODO: 테스트를 위한 Fake User

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "provider", length = 255, nullable = false)
    private String provider;

    @Column(name = "oauth_id", length = 255, nullable = false)
    private String oauthId;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "photo_url", length = 255, nullable = false)
    private String photoUrl;

    public User(String name) {
        this.name = name;
    }

    public User(String provider, String oauthId, String name, String email, String photoUrl) {
        this.provider = provider;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    public static User dummyUser() {
        return new User("google",
                "11111",
                "dummy",
                "dummy@gmail.com",
                "none"
        );
    }
}
