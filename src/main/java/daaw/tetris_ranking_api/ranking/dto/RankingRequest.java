package daaw.tetris_ranking_api.ranking.dto;

import lombok.Data;

@Data
public class RankingRequest {
    private String playerName;
    private Integer level;
    private Integer totalLines;
}