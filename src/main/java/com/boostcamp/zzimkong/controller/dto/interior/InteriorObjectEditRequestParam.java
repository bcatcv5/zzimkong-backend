package com.boostcamp.zzimkong.controller.dto.interior;


import com.boostcamp.zzimkong.domain.ObjectType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InteriorObjectEditRequestParam {
    private ObjectType objectType;
    private Long objectId;
    private int rating;
}
