package com.notarioflow.repository;

import com.notarioflow.model.Pendencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PendenciaRepository extends JpaRepository<Pendencia, Long> {
    // Não precisa escrever nada, o Spring já sabe salvar/deletar/buscar
}