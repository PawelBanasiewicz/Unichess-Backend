package banasiewicz.pawel.Unichess.Backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "openings")
@EntityListeners(AuditingEntityListener.class)
public class Opening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(nullable = false, length = 3)
    private String code;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String fen;

    @NotBlank
    @Column(name = "pgn_moves", nullable = false, unique = true)
    private String pgnMoves;

    @NotBlank
    @Column(name = "uci_moves", nullable = false, unique = true)
    private String uciMoves;

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

    public @NotBlank @Size(min = 3, max = 3) String getCode() {
        return code;
    }

    public void setCode(@NotBlank @Size(min = 3, max = 3) String code) {
        this.code = code;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getFen() {
        return fen;
    }

    public void setFen(@NotBlank String fen) {
        this.fen = fen;
    }

    public @NotBlank String getPgnMoves() {
        return pgnMoves;
    }

    public void setPgnMoves(@NotBlank String pgnMoves) {
        this.pgnMoves = pgnMoves;
    }

    public @NotBlank String getUciMoves() {
        return uciMoves;
    }

    public void setUciMoves(@NotBlank String uciMoves) {
        this.uciMoves = uciMoves;
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
