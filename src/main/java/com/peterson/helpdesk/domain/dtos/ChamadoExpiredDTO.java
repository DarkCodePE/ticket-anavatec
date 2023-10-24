package com.peterson.helpdesk.domain.dtos;

import com.peterson.helpdesk.domain.enums.Prioridade;
import com.peterson.helpdesk.domain.enums.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Getter
public class ChamadoExpiredDTO {
    private Integer id;
    private String titulo;
    private String descricao;
    private String dataAbertura;
    private LocalDate dataFechamento;
    private Status status;
    private Prioridade prioridade;
    private String nomeTecnico;
    private String emailTecnico;
    private String statusName;
    private Integer countDays;
}
