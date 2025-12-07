package daaw.tetris_ranking_api.ranking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    List<Ranking> findTop10ByOrderByTotalLinesDesc();
    Optional<Ranking> findByPlayerNameAndLevelAndTotalLines(String playerName, Integer level, Integer totalLines);
}