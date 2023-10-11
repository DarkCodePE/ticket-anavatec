package com.peterson.helpdesk.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolutionRequestDTO {
    @NotNull(message = "ticker id is required")
    private Integer ticketId;
    private String title;
    private String summary;
}
