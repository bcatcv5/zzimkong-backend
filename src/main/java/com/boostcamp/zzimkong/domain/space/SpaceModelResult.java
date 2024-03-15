package com.boostcamp.zzimkong.domain.space;

import com.boostcamp.zzimkong.domain.BaseEntity;
import com.boostcamp.zzimkong.domain.StatusCode;
import com.boostcamp.zzimkong.domain.User;
import com.boostcamp.zzimkong.domain.furniture.FurnitureModelResult;
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

    public SpaceModelResult(
            User user,
            StatusCode statusCode,
            String statusMessage,
            String storeFileUrl,
            String uploadFileName,
            Boolean statusPushed
    ) {
        this.user = user;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.storeFileUrl = storeFileUrl;
        this.uploadFileName = uploadFileName;
        this.statusPushed = statusPushed;
    }

    public static SpaceModelResult of(User user, String uploadFileName) {
        return new SpaceModelResult(
                user,
                StatusCode.PROCESSING,
                null,
                null,
                uploadFileName,
                false
        );
    }

    public String getEmail() {
        return "hsb990917@gmail.com";
    }
}
