package banasiewicz.pawel.Unichess.Backend.model;

import jakarta.persistence.*;
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

    @Column(nullable = false, length = 3)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String fen;

    @Column(name = "pgn_moves", nullable = false, unique = true)
    private String pgnMoves;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFen() {
        return fen;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public String getPgnMoves() {
        return pgnMoves;
    }

    public void setPgnMoves(String pgnMoves) {
        this.pgnMoves = pgnMoves;
    }

    public String getUciMoves() {
        return uciMoves;
    }

    public void setUciMoves(String uciMoves) {
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
