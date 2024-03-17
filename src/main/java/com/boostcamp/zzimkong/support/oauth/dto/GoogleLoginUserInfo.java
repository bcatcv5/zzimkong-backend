package com.boostcamp.zzimkong.support.oauth.dto;

import com.boostcamp.zzimkong.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@NoArgsConstructor
@ToString
public class GoogleLoginUserInfo {
    private final String provider = "google";
    private String sub;
    private String name;
    private String email;
    private String picture;

    public User toUser() {
        return new User(
                this.getProvider(),
                this.getSub(),
                this.getName(),
                this.getEmail(),
                this.getPicture()
        );
    }
}
