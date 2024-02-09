package com.boostcamp.zzimkong.support.uuid;

import com.boostcamp.zzimkong.support.UuidHolder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestUuidHolder implements UuidHolder {
    @Override
    public String random() {
        return "1234abcd1234abcd";
    }
}
