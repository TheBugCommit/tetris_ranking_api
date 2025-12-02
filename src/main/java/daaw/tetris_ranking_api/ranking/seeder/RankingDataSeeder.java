package daaw.tetris_ranking_api.ranking.seeder;

import daaw.tetris_ranking_api.ranking.RankingService;
import daaw.tetris_ranking_api.ranking.dto.RankingRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
public class RankingDataSeeder implements CommandLineRunner {

    private final RankingService rankingService;

    public RankingDataSeeder(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (rankingService.getTopRanking().isEmpty()) {
            loadTestData();
        }
    }

    private void loadTestData() {
        List<String> players = Arrays.asList(
                "TetrisMaster", "NoobSaibot", "BlockBuster", "LineKing", "SpeedRunner",
                "GlitchUser", "RetroGamer", "Winner123", "CasualPlayer", "HardcoreFan"
        );

        Random random = new Random();

        for (String player : players) {
            RankingRequest request = new RankingRequest();
            request.setPlayerName(player);
            request.setLevel(1 + random.nextInt(10));
            request.setTotalLines(request.getLevel() * 10);
            rankingService.saveRanking(request);
        }
    }
}