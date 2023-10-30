package com.peterson.helpdesk.domain.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
public class ProfileRequestDTO {
    private Integer id;
    private String phone;
    private String address;
    private String resume;
    private LocalDate birthDate;
    private Integer tecnicoId;
}
