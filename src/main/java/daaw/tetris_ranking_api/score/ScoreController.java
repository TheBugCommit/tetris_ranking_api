package daaw.tetris_ranking_api.score;

import daaw.tetris_ranking_api.score.dto.ScoreRequest;
import daaw.tetris_ranking_api.score.dto.ScoreResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public ResponseEntity<ScoreResponse> addScore(@RequestBody ScoreRequest request) {
        ScoreResponse savedScore = scoreService.saveScore(request);
        return new ResponseEntity<>(savedScore, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScoreResponse>> getRanking() {
        return ResponseEntity.ok(scoreService.getTopScores());
    }
}