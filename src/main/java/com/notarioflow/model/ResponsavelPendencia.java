package com.notarioflow.model;

public enum ResponsavelPendencia {
    CLIENTE("Cliente"),
    CARTORIO("Cartório (Interno)"),
    PREFEITURA("Prefeitura (ITBI)"),
    SEFAZ("Sefaz (ITCMD)"),
    SUPERVISOR("Tabeliã/Supervisor");

    private final String descricao;

    ResponsavelPendencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}