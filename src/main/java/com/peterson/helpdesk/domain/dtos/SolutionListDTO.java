package com.peterson.helpdesk.domain.dtos;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
public class SolutionListDTO {
    @NotNull(message = "Ticket ID cannot be null")
    private Integer ticketID;
    @NotBlank(message = "Title can not be blank")
    private String title;
    @NotBlank(message = "Sort summary can not be blank")
    private String summary;
    private String imageUrl;
}
