package com.peterson.helpdesk.domain.dtos;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
@Builder
@Getter
public class CommentResponseDTO {
    private Integer id;
    private String description;
    private Timestamp createdAt;
    private String tecnicoName;
}
