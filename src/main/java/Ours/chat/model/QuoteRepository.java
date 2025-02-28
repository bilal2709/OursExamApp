package Ours.chat.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    // findAll() est déjà disponible pour récupérer toutes les quotes
}
