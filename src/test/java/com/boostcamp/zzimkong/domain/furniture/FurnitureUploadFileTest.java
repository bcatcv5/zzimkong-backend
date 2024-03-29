package com.boostcamp.zzimkong.domain.furniture;

import com.boostcamp.zzimkong.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FurnitureUploadFileTest {
    @Test
    @DisplayName("가구 업로드 파일을 생성한다.")
    void construct() {
        // given
        User dummyUser = new User("dummy");

        // then
        Assertions.assertDoesNotThrow(() -> new FurnitureUploadFile(
                dummyUser,
                "storeFileName",
                "uploadFileName"
        ));
    }
}
