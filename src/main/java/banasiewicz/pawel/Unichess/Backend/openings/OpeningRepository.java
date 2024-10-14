package banasiewicz.pawel.Unichess.Backend.openings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningRepository extends JpaRepository<Opening, Long> {
}
