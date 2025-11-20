package daaw.tetris_ranking_api.score;

import daaw.tetris_ranking_api.score.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public ScoreResponse saveScore(ScoreRequest request) {
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

        Score score = new Score();
        score.setPlayerName(request.getPlayerName());
        score.setLevel(request.getLevel());
        score.setTotalLines(request.getTotalLines());

        Score savedScore = scoreRepository.save(score);

        return mapToResponse(savedScore);
    }

    public List<ScoreResponse> getTopScores() {
        List<Score> entities = scoreRepository.findTop10ByOrderByTotalLinesDesc();

        return entities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ScoreResponse mapToResponse(Score score) {
        ScoreResponse response = new ScoreResponse();
        response.setPlayerName(score.getPlayerName());
        response.setLevel(score.getLevel());
        response.setTotalLines(score.getTotalLines());
        response.setPlayedAt(score.getPlayedAt());
        return response;
    }
}
