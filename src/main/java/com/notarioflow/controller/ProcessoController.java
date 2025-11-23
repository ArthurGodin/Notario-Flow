package com.notarioflow.controller;

import com.notarioflow.model.Feedback;
import com.notarioflow.model.Processo;
import com.notarioflow.model.ResponsavelPendencia;
import com.notarioflow.repository.FeedbackRepository;
import com.notarioflow.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.notarioflow.model.Pendencia;
import com.notarioflow.repository.PendenciaRepository;
import org.springframework.web.bind.annotation.PathVariable;
import com.notarioflow.service.DiscordService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProcessoController {

    @Autowired
    private ProcessoRepository repository;

    @Autowired
    private PendenciaRepository pendenciaRepository;

    // ROTA 1: Página Inicial (Listagem)
    // Se essa rota sumir, dá o erro 404 que você viu
    @GetMapping("/")
    public String listarProcessos(Model model) {
        List<Processo> lista = repository.findAllByOrderByUltimoContatoAsc();
        model.addAttribute("processos", lista);
        return "home"; // Vai procurar o home.html
    }

    // ROTA 2: Abrir o Formulário
    @GetMapping("/novo")
    public String abrirFormulario() {
        return "formulario"; // Vai procurar o formulario.html
    }





    @PostMapping("/processo/{id}/pendencia")
    public String adicionarPendenciaNoProcesso(@PathVariable Long id,
                                               @RequestParam String descricao,
                                               @RequestParam String responsavel) {

        Processo p = repository.findById(id).orElse(null);

        if (p != null) {
            // Converte String pro Enum
            ResponsavelPendencia respEnum = ResponsavelPendencia.valueOf(responsavel);

            // Usa aquele método auxiliar que criamos na classe Processo
            p.adicionarPendencia(descricao, respEnum);

            repository.save(p);
        }

        return "redirect:/";
    }



    @PostMapping("/processo/{id}/cobrar")
    public String registrarCobranca(@PathVariable Long id) {
        Processo p = repository.findById(id).orElse(null);
        if (p != null) {
            p.setUltimoContato(LocalDateTime.now()); // Atualiza o relógio
            repository.save(p);
        }
        return "redirect:/";
    }



    // ROTA 3: Salvar os dados (Recebe do Formulário)
    @PostMapping("/salvar")
    public String salvarProcesso(@RequestParam String cliente,
                                 @RequestParam String tipoAto,
                                 @RequestParam String observacoes,
                                 @RequestParam(required = false) String pendenciaDescricao,
                                 @RequestParam(required = false) String pendenciaResponsavel) {

        // Cria o processo
        Processo p = new Processo(cliente, tipoAto);
        p.setObservacoes(observacoes);

        // Verifica se tem pendência para adicionar
        if (pendenciaDescricao != null && !pendenciaDescricao.isEmpty() &&
                pendenciaResponsavel != null && !pendenciaResponsavel.isEmpty()) {

            // Converte o texto para o Enum
            ResponsavelPendencia respEnum = ResponsavelPendencia.valueOf(pendenciaResponsavel);
            p.adicionarPendencia(pendenciaDescricao, respEnum);
        }

        repository.save(p);

        return "redirect:/"; // Volta para a tela inicial
    }

    @PostMapping("/pendencia/{id}/resolver")
    public String resolverPendencia(@PathVariable Long id){

        Pendencia pendencia = pendenciaRepository.findById(id).orElse(null);

        if (pendencia != null) {
            pendencia.setResolvida(true);

            pendenciaRepository.save(pendencia);
        }

        return "redirect:/";
    }








    // --- 1. DELETAR PROCESSO ---
    @PostMapping("/processo/{id}/deletar")
    public String deletarProcesso(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    // --- 2. ABRIR TELA DE EDIÇÃO ---
    @GetMapping("/processo/{id}/editar")
    public String abrirEdicao(@PathVariable Long id, Model model) {
        Processo p = repository.findById(id).orElse(null);
        if (p != null) {
            model.addAttribute("processo", p);
            return "editar"; // Vamos criar esse arquivo HTML
        }
        return "redirect:/";
    }

    // --- 3. SALVAR A EDIÇÃO ---
    @PostMapping("/processo/{id}/atualizar")
    public String atualizarProcesso(@PathVariable Long id,
                                    @RequestParam String cliente,
                                    @RequestParam String tipoAto,
                                    @RequestParam String observacoes) {

        Processo p = repository.findById(id).orElse(null);
        if (p != null) {
            p.setCliente(cliente);
            p.setTipoAto(tipoAto);
            p.setObservacoes(observacoes);
            repository.save(p); // O save do JPA atualiza se o ID já existe
        }
        return "redirect:/";
    }





    @Autowired
    private com.notarioflow.repository.FeedbackRepository feedbackRepository;

    @Autowired
    private DiscordService discordService;

    @PostMapping("/feedback/salvar")
    public String salvarFeedback(@RequestParam String texto) {
        if (texto != null && !texto.isEmpty()) {
            // 1. Salva no banco local (Backup)
            feedbackRepository.save(new Feedback(texto));

            // 2. Manda pro seu celular (Discord) em uma Thread separada para não travar a tela dela
            new Thread(() -> discordService.enviarFeedback(texto)).start();
        }
        return "redirect:/";
    }

    // 2. Rota Secreta para VOCÊ ver as melhorias
    @GetMapping("/ideias")
    public String listarIdeias(Model model) {
        model.addAttribute("feedbacks", feedbackRepository.findAll());
        return "ideias"; // Vamos criar esse HTML simples
    }







    
}