package com.boostcamp.zzimkong.domain.space;

import com.boostcamp.zzimkong.domain.BaseEntity;
import com.boostcamp.zzimkong.domain.StatusCode;
import com.boostcamp.zzimkong.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "space_model_result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpaceModelResult extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_result_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_space_model_result_user"), nullable = false)
    private User user;

    @Column(name = "message_id", nullable = false)
    private Long messageId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_code", nullable = false, columnDefinition = "VARCHAR(45)")
    private StatusCode statusCode;

    @Column(name = "status_message", length = 255, nullable = true)
    private String statusMessage;

    @Column(name = "store_file_url", length = 255, nullable = true)
    private String storeFileUrl;

    @Column(name = "thumbnail_file_url", length = 255, nullable = true)
    private String thumbnailFileUrl;

    public SpaceModelResult(
            User user,
            Long messageId,
            StatusCode statusCode,
            String statusMessage,
            String storeFileUrl,
            String thumbnailFileUrl
    ) {
        this.user = user;
        this.messageId = messageId;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.storeFileUrl = storeFileUrl;
        this.thumbnailFileUrl = thumbnailFileUrl;
    }

    public static SpaceModelResult of(User user, Long messageId) {
        return new SpaceModelResult(
                user,
                messageId,
                StatusCode.PROCESSING,
                null,
                null,
                null
        );
    }
}
