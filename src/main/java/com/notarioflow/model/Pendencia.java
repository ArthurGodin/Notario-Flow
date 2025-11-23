package com.notarioflow.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Pendencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private ResponsavelPendencia responsavel;

    private boolean resolvida = false;

    private LocalDate dataCriacao = LocalDate.now();

    // --- CONSTRUTOR VAZIO (Só pode ter UM desse) ---
    public Pendencia() {
    }

    // --- Construtor com argumentos (Auxiliar) ---
    public Pendencia(String descricao, ResponsavelPendencia responsavel) {
        this.descricao = descricao;
        this.responsavel = responsavel;
    }

    // --- GETTERS E SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public ResponsavelPendencia getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(ResponsavelPendencia responsavel) {
        this.responsavel = responsavel;
    }

    public boolean isResolvida() {
        return resolvida;
    }

    public void setResolvida(boolean resolvida) {
        this.resolvida = resolvida;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}