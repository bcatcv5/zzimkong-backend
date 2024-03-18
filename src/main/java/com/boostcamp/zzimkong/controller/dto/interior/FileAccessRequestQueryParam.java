package com.boostcamp.zzimkong.controller.dto.interior;

import com.boostcamp.zzimkong.domain.FileType;
import com.boostcamp.zzimkong.domain.ObjectType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileAccessRequestQueryParam {
    private ObjectType objectType;
    private Long objectId;
    private FileType fileType;
}
