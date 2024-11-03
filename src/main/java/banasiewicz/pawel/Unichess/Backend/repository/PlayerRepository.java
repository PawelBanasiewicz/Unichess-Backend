package banasiewicz.pawel.Unichess.Backend.repository;

import banasiewicz.pawel.Unichess.Backend.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    boolean existsByFirstNameAndLastNameAndBirthDate(final String firstName, final String lastName, final LocalDate birthDate);

    @Query("SELECT COUNT(p) > 0 FROM Player p " +
            "WHERE p.firstName = :firstName " +
            "AND p.lastName = :lastName " +
            "AND p.birthDate = :birthDate " +
            "AND p.id <> :id")
        boolean existsByFirstNameAndLastNameAndBirthDateExcludingId(
            @Param("id") final Long id,
            @Param("firstName") final String firstName,
            @Param("lastName") final String lastName,
            @Param("birthDate") final LocalDate birthDate);
}
