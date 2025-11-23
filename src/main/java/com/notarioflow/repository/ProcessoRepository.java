package com.notarioflow.repository;

import com.notarioflow.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {

    // Spring Data cria o SQL sozinho baseado no nome do método!
    // Ordenar pelos que não tem contato há mais tempo (os esquecidos)
    List<Processo> findAllByOrderByUltimoContatoAsc();

    // Buscar por nome do cliente (para a barra de busca)
    List<Processo> findByClienteContainingIgnoreCase(String nome);
}