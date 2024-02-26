package com.boostcamp.zzimkong.domain.space;

import com.boostcamp.zzimkong.domain.BaseEntity;
import com.boostcamp.zzimkong.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "space_upload_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_upload_file_user"), nullable = false)
    private User user;

    @Column(name = "store_file_url", length = 255, nullable = false)
    private String storeFileUrl;

    @Column(name = "upload_file_name", length = 255, nullable = false)
    private String uploadFileName;

    public UploadFile(
            User user,
            String storeFileUrl,
            String uploadFileName
    ) {
        this.user = user;
        this.storeFileUrl = storeFileUrl;
        this.uploadFileName = uploadFileName;
    }
}