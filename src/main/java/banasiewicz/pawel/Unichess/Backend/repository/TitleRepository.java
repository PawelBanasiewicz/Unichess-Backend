package banasiewicz.pawel.Unichess.Backend.repository;

import banasiewicz.pawel.Unichess.Backend.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
}
