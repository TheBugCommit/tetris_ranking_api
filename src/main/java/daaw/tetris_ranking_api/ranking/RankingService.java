package daaw.tetris_ranking_api.ranking;

import daaw.tetris_ranking_api.ranking.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingService {

    private final RankingRepository rankingRepository;

    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public RankingResponse saveScore(RankingRequest request) {
        if(request.getPlayerName() == null || request.getPlayerName().isEmpty()
                || request.getPlayerName().isBlank()) {
            throw new IllegalArgumentException("PlayerName can't be null or empty");
        }
        if (request.getLevel() < 0) {
            throw new IllegalArgumentException("Level must be positive");
        }
        if (request.getTotalLines() < 0) {
            throw new IllegalArgumentException("Lines must be positive");
        }

        Ranking ranking = new Ranking();
        ranking.setPlayerName(request.getPlayerName());
        ranking.setLevel(request.getLevel());
        ranking.setTotalLines(request.getTotalLines());

        Ranking savedRanking = rankingRepository.save(ranking);

        return mapToResponse(savedRanking);
    }

    public List<RankingResponse> getTopScores() {
        List<Ranking> entities = rankingRepository.findTop10ByOrderByTotalLinesDesc();

        return entities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private RankingResponse mapToResponse(Ranking ranking) {
        RankingResponse response = new RankingResponse();
        response.setPlayerName(ranking.getPlayerName());
        response.setLevel(ranking.getLevel());
        response.setTotalLines(ranking.getTotalLines());
        response.setPlayedAt(ranking.getPlayedAt());
        return response;
    }
}
