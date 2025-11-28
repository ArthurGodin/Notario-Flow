package com.notarioflow.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class DiscordService {

    // Cole a URL que você copiou do Discord aqui dentro das aspas
    private final String WEBHOOK_URL = "https://discord.com/api/webhooks/1444096333983252531/ZiY991fUEfXx7QvvSWdZJztWa1ayrHPPuVLsjJvT1yEFu88c4TWeJGFAGKQa35iIC7UT";

    public void enviarFeedback(String mensagem) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // O Discord espera um JSON assim: { "content": "sua mensagem " }
            Map<String, String> payload = new HashMap<>();
            payload.put("content", "💡 **Nova Ideia do Cartório:**\n" + mensagem);

            // Dispara a mensagem para a internet
            restTemplate.postForEntity(WEBHOOK_URL, payload, String.class);

        } catch (Exception e) {
            // Se der erro (sem internet), apenas imprime no console e não trava o sistema dela
            System.err.println("Erro ao enviar pro Discord: " + e.getMessage());
        }
    }
}