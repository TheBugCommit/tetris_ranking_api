package daaw.tetris_ranking_api.ranking;

import daaw.tetris_ranking_api.ranking.dto.RankingRequest;
import daaw.tetris_ranking_api.ranking.dto.RankingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @PostMapping
    public ResponseEntity<RankingResponse> addRanking(@RequestBody RankingRequest request) {
        RankingResponse savedScore = rankingService.saveRanking(request);
        return new ResponseEntity<>(savedScore, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RankingResponse>> getRanking() {
        return ResponseEntity.ok(rankingService.getTopRanking());
    }
}