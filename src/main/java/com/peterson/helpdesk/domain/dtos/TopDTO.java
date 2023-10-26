package com.peterson.helpdesk.domain.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TopDTO {
    private Integer total;
    private Integer totalSolved;
    private Integer totalTechnician;
    private List<TopTecnicoDTO> topTechnician;
}
