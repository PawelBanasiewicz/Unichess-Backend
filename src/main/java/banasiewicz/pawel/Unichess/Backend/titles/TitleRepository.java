package banasiewicz.pawel.Unichess.Backend.titles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TitleRepository extends JpaRepository<Title, Long> {
}
