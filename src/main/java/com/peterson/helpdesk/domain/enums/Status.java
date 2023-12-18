package com.peterson.helpdesk.domain.enums;

public enum Status {

    ABERTO(0, "ABIERTO"), ANDAMENTO(1, "EN PROCESO"), ENCERRADO(2, "CERRADO");

    private Integer codigo;
    private String descricao;

    Status(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Status toEnum(Integer cod) {
        if(cod == null) {
            return null;
        }
        for(Status x : Status.values()){
            if(cod.equals(x.getCodigo())) {
                return x;
            }
        }
        throw new IllegalArgumentException("Status Inválido");
    }
}
