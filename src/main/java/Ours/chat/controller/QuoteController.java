package Ours.chat.controller;

import Ours.chat.model.Quote;
import Ours.chat.model.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class QuoteController {

    @Autowired
    private QuoteRepository quoteRepository;

    @GetMapping("/quote")
    public ResponseEntity<Quote> getRandomQuote() {
        List<Quote> quotes = quoteRepository.findAll();
        if (quotes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        int randomIndex = new Random().nextInt(quotes.size());
        Quote quote = quotes.get(randomIndex);
        return ResponseEntity.ok(quote);
    }
}
