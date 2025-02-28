package Ours.chat.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("SELECT DISTINCT c.username FROM Conversation c")
    List<String> findDistinctUsernames();

    List<Conversation> findByUsername(String username);
}
