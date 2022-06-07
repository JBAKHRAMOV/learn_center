package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private Integer id;
    private String name;

    public JwtDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
