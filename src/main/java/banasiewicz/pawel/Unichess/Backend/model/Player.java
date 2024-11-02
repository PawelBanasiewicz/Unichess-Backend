package banasiewicz.pawel.Unichess.Backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "players", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"first_name", "last_name", "birth_date"})
})
@EntityListeners(AuditingEntityListener.class)
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @PastOrPresent
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column
    private Sex sex;

    @NotBlank
    @Column
    private String nationality;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "title_id")
    private Title title;

    @Min(1)
    @Column(name = "elo_rating")
    private Integer eloRating;

    @NotNull
    @Column(name = "insert_date", nullable = false)
    @CreatedDate
    private LocalDateTime insertDate;

    @Column(name = "edit_date")
    @LastModifiedDate
    private LocalDateTime editDate;

    public enum Sex {
        MALE, FEMALE
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank String lastName) {
        this.lastName = lastName;
    }

    public @PastOrPresent LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@PastOrPresent LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public @NotBlank String getNationality() {
        return nationality;
    }

    public void setNationality(@NotBlank String nationality) {
        this.nationality = nationality;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public @Min(1) Integer getEloRating() {
        return eloRating;
    }

    public void setEloRating(@Min(1) Integer eloRating) {
        this.eloRating = eloRating;
    }

    public @NotNull LocalDateTime getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(@NotNull LocalDateTime insertDate) {
        this.insertDate = insertDate;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }
}
