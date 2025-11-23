package com.notarioflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Processo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    private String tipoAto;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    private LocalDateTime ultimoContato = LocalDateTime.now();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "processo_id")
    private List<Pendencia> pendencias = new ArrayList<>();

    // Construtor Vazio
    public Processo() {
    }

    public Processo(String cliente, String tipoAto) {
        this.cliente = cliente;
        this.tipoAto = tipoAto;
    }

    // --- MUDANÇA AQUI: Usando 'get' para facilitar a vida do Spring ---
    public boolean getConcluido() {
        if (this.pendencias == null || this.pendencias.isEmpty()) {
            return true;
        }
        for (Pendencia p : this.pendencias) {
            if (!p.isResolvida()) {
                return false;
            }
        }
        return true;
    }

    public void adicionarPendencia(String descricao, ResponsavelPendencia responsavel) {
        Pendencia p = new Pendencia(descricao, responsavel);
        this.pendencias.add(p);
    }

    // Getters e Setters Padrão
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public String getTipoAto() { return tipoAto; }
    public void setTipoAto(String tipoAto) { this.tipoAto = tipoAto; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public LocalDateTime getUltimoContato() { return ultimoContato; }
    public void setUltimoContato(LocalDateTime ultimoContato) { this.ultimoContato = ultimoContato; }
    public List<Pendencia> getPendencias() { return pendencias; }
    public void setPendencias(List<Pendencia> pendencias) { this.pendencias = pendencias; }
}