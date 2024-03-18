package com.boostcamp.zzimkong.domain.furniture;

import com.boostcamp.zzimkong.domain.BaseEntity;
import com.boostcamp.zzimkong.domain.StatusCode;
import com.boostcamp.zzimkong.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "FurnitureModelResult")
@Getter
@Table(name = "furniture_model_result")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FurnitureModelResult extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_result_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_furniture_model_result_user"), nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_code", nullable = false, columnDefinition = "VARCHAR(45)")
    private StatusCode statusCode;

    @Column(name = "status_message", length = 255, nullable = true)
    private String statusMessage;

    @Column(name = "status_pushed", nullable = false)
    private boolean statusPushed;

    @Column(name = "store_file_url", length = 255, nullable = true)
    private String storeFileUrl;

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


    public FurnitureModelResult(User user,
                            StatusCode statusCode,
                            String statusMessage,
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
        this.statusPushed = statusPushed;
        this.storeFileUrl = storeFileUrl;
        this.thumbnailFileUrl = thumbnailFileUrl;
        this.rating = rating;
        this.shared = shared;
        this.deleted = deleted;
    }

    public static FurnitureModelResult from(User user) {
        return new FurnitureModelResult(
                user,
                StatusCode.PROCESSING,
                null,
                false,
                null,
                null,
                2,
                false,
                true
        );
    }
}
