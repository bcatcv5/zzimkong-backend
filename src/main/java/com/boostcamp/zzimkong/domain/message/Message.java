package com.boostcamp.zzimkong.domain.message;

import com.boostcamp.zzimkong.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @Column(name = "object_type")
    private Boolean objectType;

    @Column(name = "model")
    private String model;

    @Column(name = "store_file_url", length = 255, nullable = true)
    private String src;

    public Message(Boolean objectType, String model, String storeFileUrl) {
        this.objectType = objectType;
        this.model = model;
        this.src = storeFileUrl;
    }

    public static Message of(String type, String storeFileUrl) {
        return new Message(
                ObjectType.from(type),
                Model.from(type),
                storeFileUrl
        );
    }

    public boolean isSpace() {
        return getObjectType().equals(false);
    }
}
