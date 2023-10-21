package com.peterson.helpdesk.domain.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RecommendationDTO {
    private String description;
    private Integer solutionId;
}
