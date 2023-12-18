package com.peterson.helpdesk.domain.dtos;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter
public class RecommendationResponseDTO {
    private Integer id;
    private String description;
    private Timestamp created_at;
    private String tecnicoName;
    private String solutionTitle;
    private List<CommentResponseDTO> comments;
}
