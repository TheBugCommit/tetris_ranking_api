package daaw.tetris_ranking_api.ranking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findTop10ByOrderByTotalLinesDesc();
    boolean existsByPlayerNameAndLevelAndTotalLines(String playerName, Integer level, Integer totalLines);
}