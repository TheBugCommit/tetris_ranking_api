package daaw.tetris_ranking_api.score.dto;

import lombok.Data;

@Data
public class ScoreRequest {
    private String playerName;
    private Integer level;
    private Integer totalLines;
}