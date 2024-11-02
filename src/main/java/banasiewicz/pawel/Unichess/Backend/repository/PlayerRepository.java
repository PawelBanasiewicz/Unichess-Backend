package banasiewicz.pawel.Unichess.Backend.repository;

import banasiewicz.pawel.Unichess.Backend.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByFirstNameAndLastNameAndBirthDate(final String firstName, final String lastName, final LocalDate birthDate);
}
