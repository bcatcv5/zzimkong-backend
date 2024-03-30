package com.boostcamp.zzimkong.domain.space;

import com.boostcamp.zzimkong.domain.BaseEntity;
import com.boostcamp.zzimkong.domain.StatusCode;
import com.boostcamp.zzimkong.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.boostcamp.zzimkong.utils.ZzimkongConstant.*;

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

    @Column(name = "message_id", nullable = true)
    private Long messageId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_code", nullable = false, columnDefinition = "VARCHAR(45)")
    private StatusCode statusCode;

    @Column(name = "status_message", length = 255, nullable = true)
    private String statusMessage;

    @Column(name = "store_file_url", length = 255, nullable = true)
    private String storeFileUrl;

    @Column(name = "upload_file_name", length = 255, nullable = false)
    private String uploadFileName;

    @Column(name = "status_pushed", nullable = true)
    private Boolean statusPushed;

    @Column(name = "thumbnail_file_url", length = 255, nullable = true)
    private String thumbnailFileUrl;

    @Column(name = "rating", nullable = false, columnDefinition = "SMALLINT")
    @Setter
    private int rating;

    @Column(name = "shared", nullable = false)
    @Setter
    @Getter
    private boolean shared;

    @Column(name = "deleted", nullable = false)
    @Setter
    private boolean deleted;

    @Column(name = "learned_date", nullable = true)
    private LocalDateTime learnedDate;

    @Column(name = "finished_date", nullable = true)
    private LocalDateTime finishedDate;

    public SpaceModelResult(User user,
                            StatusCode statusCode,
                            String statusMessage,
                            String uploadFileName,
                            boolean statusPushed,
                            String storeFileUrl,
                            String thumbnailFileUrl,
                            int rating,
                            boolean shared,
                            boolean deleted
    ) {
        this.user = user;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.storeFileUrl = storeFileUrl;
        this.uploadFileName = uploadFileName;
        this.statusPushed = statusPushed;
        this.thumbnailFileUrl = thumbnailFileUrl;
        this.rating = rating;
        this.shared = shared;
        this.deleted = deleted;
    }

    public static SpaceModelResult of(User user, String uploadFileName, String storeFileUrl) {
        return new SpaceModelResult(
                user,
                StatusCode.PROCESSING,
                null,
                uploadFileName,
                false,
                getStoreFileName(storeFileUrl),
                null,
                2,
                false,
                false
        );
    }

    public void changeId(Long id) {
        this.messageId = id;
    }

    private static String getStoreFileName(String storeFileUrl) {
        return SPACE_RESULT_URL + storeFileUrl.split(REGEX)[LAST_IDX] + SUFFIX;
    }
}
