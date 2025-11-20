package daaw.tetris_ranking_api.score.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ScoreResponse {
    private String playerName;
    private Integer level;
    private Integer totalLines;
    private LocalDateTime playedAt;
}