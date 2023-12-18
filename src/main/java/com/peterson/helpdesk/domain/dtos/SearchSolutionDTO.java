package com.peterson.helpdesk.domain.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SearchSolutionDTO {
    private Integer productId;
    private String title;
}
