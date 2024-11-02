package banasiewicz.pawel.Unichess.Backend.repository;

import banasiewicz.pawel.Unichess.Backend.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {

    @Query("SELECT t from Title t " +
            "WHERE LOWER(t.name) = LOWER(:title) " +
            "OR LOWER(t.abbreviation) = LOWER(:title)")
    Optional<Title> findByNameOrAbbreviationIgnoreCase(final @Param("title") String title);
}
