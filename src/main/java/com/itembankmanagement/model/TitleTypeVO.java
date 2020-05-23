package com.itembankmanagement.model;

import com.itembankmanagement.dao.TitleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TitleTypeVO {

    private Long id;
    private String name;

    public TitleTypeVO(TitleType type){
        id=type.getId();
        name=type.getName();
    }

}
