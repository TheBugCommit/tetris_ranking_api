package daaw.tetris_ranking_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import daaw.tetris_ranking_api.score.Score;
import daaw.tetris_ranking_api.score.ScoreRepository;
import daaw.tetris_ranking_api.score.dto.ScoreRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ScoreIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        scoreRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/ranking")
    void shouldPersistScoreInRealDatabase() throws Exception {
        ScoreRequest request = new ScoreRequest();
        request.setPlayerName("JugadorReal");
        request.setLevel(2);
        request.setTotalLines(40);

        mockMvc.perform(post("/api/ranking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerName").value("JugadorReal"));

        assertEquals(1, scoreRepository.count());
        assertEquals("JugadorReal", scoreRepository.findAll().get(0).getPlayerName());
    }

    @Test
    @DisplayName("POST /api/ranking (Error)")
    void shouldTriggerRealServiceLogicAndReturnError() throws Exception {
        ScoreRequest request = new ScoreRequest();
        request.setPlayerName("Hacker");
        request.setLevel(1);
        request.setTotalLines(-100);

        mockMvc.perform(post("/api/ranking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()) // Esperamos 400 Bad Request
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Lines must be positive"));

        assertEquals(0, scoreRepository.count());
    }

    @Test
    @DisplayName("GET /api/ranking")
    void shouldReturnRankingOrderedByScore() throws Exception {
        Score scoreBajo = new Score();
        scoreBajo.setPlayerName("Novato");
        scoreBajo.setLevel(1);
        scoreBajo.setTotalLines(100);
        scoreRepository.save(scoreBajo);

        Score scoreAlto = new Score();
        scoreAlto.setPlayerName("Pro");
        scoreAlto.setLevel(10);
        scoreAlto.setTotalLines(9000);
        scoreRepository.save(scoreAlto);

        mockMvc.perform(get("/api/ranking")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].playerName").value("Pro"))
                .andExpect(jsonPath("$[0].totalLines").value(9000))
                .andExpect(jsonPath("$[1].playerName").value("Novato"))
                .andExpect(jsonPath("$[1].totalLines").value(100));
    }
}