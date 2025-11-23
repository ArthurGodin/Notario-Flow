package com.notarioflow.config;

import com.notarioflow.model.Processo;
import com.notarioflow.model.ResponsavelPendencia;
import com.notarioflow.repository.ProcessoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(ProcessoRepository repository) {
        return args -> {
            // Criando um processo de Inventário Travado
            Processo p1 = new Processo("Família Souza", "Inventário");
            p1.setObservacoes("Caso complicado, muitos herdeiros.");
            p1.adicionarPendencia("Pagar ITCMD", ResponsavelPendencia.SEFAZ);
            p1.adicionarPendencia("Enviar Certidão de Óbito", ResponsavelPendencia.CLIENTE);

            // Criando um processo de Compra e Venda quase pronto
            Processo p2 = new Processo("Construtora X e Pedro", "Compra e Venda");
            p2.adicionarPendencia("Emitir Guia ITBI", ResponsavelPendencia.CARTORIO);

            repository.save(p1);
            repository.save(p2);

            System.out.println("--- DADOS INICIAIS CARREGADOS COM SUCESSO ---");
        };
    }
}