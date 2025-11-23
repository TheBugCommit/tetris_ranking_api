package daaw.tetris_ranking_api.ranking.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RankingResponse {
    private String playerName;
    private Integer level;
    private Integer totalLines;
    private LocalDateTime playedAt;
}a