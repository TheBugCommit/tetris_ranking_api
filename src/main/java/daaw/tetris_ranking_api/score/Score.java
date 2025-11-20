package daaw.tetris_ranking_api.score;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "scores")
@Data
@NoArgsConstructor
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String playerName;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer totalLines;

    private LocalDateTime playedAt;

    @PrePersist
    public void prePersist() {
        this.playedAt = LocalDateTime.now();
    }
}