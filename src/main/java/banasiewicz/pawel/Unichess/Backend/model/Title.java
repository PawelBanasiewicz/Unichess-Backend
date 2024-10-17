package banasiewicz.pawel.Unichess.Backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "titles")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String abbreviation;

    @Column(nullable = false)
    private Integer eloThreshold;

    @Column(nullable = false)
    private Boolean requiresNorm;

    @Column(nullable = false)
    private Boolean onlyFemale;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime insertDate;

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
}
