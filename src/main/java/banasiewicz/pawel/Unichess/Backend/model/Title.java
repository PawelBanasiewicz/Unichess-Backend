package banasiewicz.pawel.Unichess.Backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "titles")
@EntityListeners(AuditingEntityListener.class)
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank
    @Size(min = 2, max = 3)
    @Column(nullable = false, unique = true)
    private String abbreviation;

    @NotNull
    @Column(name = "elo_threshold", nullable = false)
    private Integer eloThreshold;

    @NotNull
    @Column(name = "requires_norm", nullable = false)
    private Boolean requiresNorm;

    @NotNull
    @Column(name = "only_female", nullable = false)
    private Boolean onlyFemale;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY)
    private List<Player> players;

    @Min(1)
    @Column(name = "introduction_year", nullable = false)
    private Integer introductionYear;

    @CreatedDate
    @Column(name = "insert_date", nullable = false, updatable = false)
    private LocalDateTime insertDate;

    @LastModifiedDate
    @Column(name = "edit_date")
    private LocalDateTime editDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank @Size(min = 2, max = 3) String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(@NotBlank @Size(min = 2, max = 3) String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public @NotNull Integer getEloThreshold() {
        return eloThreshold;
    }

    public void setEloThreshold(@NotNull Integer eloThreshold) {
        this.eloThreshold = eloThreshold;
    }

    public @NotNull Boolean getRequiresNorm() {
        return requiresNorm;
    }

    public void setRequiresNorm(@NotNull Boolean requiresNorm) {
        this.requiresNorm = requiresNorm;
    }

    public @NotNull Boolean getOnlyFemale() {
        return onlyFemale;
    }

    public void setOnlyFemale(@NotNull Boolean onlyFemale) {
        this.onlyFemale = onlyFemale;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public @Min(1) Integer getIntroductionYear() {
        return introductionYear;
    }

    public void setIntroductionYear(@Min(1) Integer introductionYear) {
        this.introductionYear = introductionYear;
    }

    public LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }
}
