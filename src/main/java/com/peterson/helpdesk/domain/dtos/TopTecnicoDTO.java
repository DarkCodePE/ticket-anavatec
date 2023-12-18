package com.peterson.helpdesk.domain.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TopTecnicoDTO {
    private String nome;
    private String email;
    private Integer quantidade;
}
