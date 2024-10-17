package banasiewicz.pawel.Unichess.Backend.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "titles")
@EntityListeners(AuditingEntityListener.class)
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String abbreviation;

    @Column(name = "elo_threshold", nullable = false)
    private Integer eloThreshold;

    @Column(name = "requires_norm", nullable = false)
    private Boolean requiresNorm;

    @Column(name = "only_female", nullable = false)
    private Boolean onlyFemale;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Integer getEloThreshold() {
        return eloThreshold;
    }

    public void setEloThreshold(Integer eloThreshold) {
        this.eloThreshold = eloThreshold;
    }

    public Boolean getRequiresNorm() {
        return requiresNorm;
    }

    public void setRequiresNorm(Boolean requiresNorm) {
        this.requiresNorm = requiresNorm;
    }

    public Boolean getOnlyFemale() {
        return onlyFemale;
    }

    public void setOnlyFemale(Boolean onlyFemale) {
        this.onlyFemale = onlyFemale;
    }

    public Integer getIntroductionYear() {
        return introductionYear;
    }

    public void setIntroductionYear(Integer introductionYear) {
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
