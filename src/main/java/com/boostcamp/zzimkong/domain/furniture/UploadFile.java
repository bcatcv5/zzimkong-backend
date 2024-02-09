package com.boostcamp.zzimkong.domain.furniture;

import com.boostcamp.zzimkong.domain.BaseEntity;
import com.boostcamp.zzimkong.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "FurnitureUploadFile")
@Getter
@Table(name = "furniture_upload_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_furniture_upload_file_user"), nullable = false)
    private User user;

    @Column(name = "store_file_name", length = 255, nullable = false)
    private String storeFileName;

    @Column(name = "upload_file_name", length = 255, nullable = false)
    private String uploadFileName;

    public UploadFile(
            User user,
            String storeFileName,
            String uploadFileName
    ) {
        this.user = user;
        this.storeFileName = storeFileName;
        this.uploadFileName = uploadFileName;
    }
}
