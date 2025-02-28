package Ours.chat.controller;

import Ours.chat.model.ConversationRepository;
import Ours.chat.model.Conversation;
import Ours.chat.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Affichage de la page de chat
    @GetMapping("/")
    public String showChatForm(Model model) {
        model.addAttribute("conversation", new Conversation());
        return "chat";
    }

    // Traitement du message envoyé
    @PostMapping("/send")
    public String sendMessage(@ModelAttribute Conversation conversation, Model model) {
        // Appel à l'API des quotes
        String apiUrl = "http://localhost:8081/api/quote";
        ResponseEntity<Quote> response = restTemplate.getForEntity(apiUrl, Quote.class);
        Quote quote = response.getBody();

        conversation.setReply((quote != null) ? quote.getText() : "Pas de quote disponible");
        conversation.setDate(LocalDateTime.now());
        conversationRepository.save(conversation);

        model.addAttribute("conversation", conversation);
        return "chat";
    }

    // Liste des utilisateurs ayant posté
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<String> users = conversationRepository.findDistinctUsernames();
        model.addAttribute("users", users);
        return "users";
    }

    // Affichage de la conversation d'un utilisateur
    @GetMapping("/conversation/{username}")
    public String showConversation(@PathVariable String username, Model model) {
        List<Conversation> conversations = conversationRepository.findByUsername(username);
        model.addAttribute("conversations", conversations);
        model.addAttribute("username", username);
        return "conversation";
    }
}
