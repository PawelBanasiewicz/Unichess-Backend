package banasiewicz.pawel.Unichess.Backend.dto.player;

import banasiewicz.pawel.Unichess.Backend.model.Player;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public class PlayerCreateDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @PastOrPresent
    private LocalDate birthDate;

    private Player.Sex sex;

    @NotBlank
    private String nationality;

    @NotBlank
    private String title;

    @Min(1)
    private Integer eloRating;

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

    public Player.Sex getSex() {
        return sex;
    }

    public void setSex(Player.Sex sex) {
        this.sex = sex;
    }

    public @NotBlank String getNationality() {
        return nationality;
    }

    public void setNationality(@NotBlank String nationality) {
        this.nationality = nationality;
    }

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public @Min(1) Integer getEloRating() {
        return eloRating;
    }

    public void setEloRating(@Min(1) Integer eloRating) {
        this.eloRating = eloRating;
    }
}
