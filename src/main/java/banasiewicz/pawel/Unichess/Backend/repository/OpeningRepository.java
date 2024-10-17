package banasiewicz.pawel.Unichess.Backend.repository;

import banasiewicz.pawel.Unichess.Backend.model.Opening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningRepository extends JpaRepository<Opening, Long> {
}
