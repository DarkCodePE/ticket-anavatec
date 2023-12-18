package com.peterson.helpdesk.domain.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentDTO {
    private String description;
    private String tecnicoEmail;
    private Integer recommendationId;
}
