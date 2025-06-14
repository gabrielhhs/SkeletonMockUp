package saving;

import org.junit.jupiter.api.Test;
import saving.mockclasses.GameStub;
import saving.mockclasses.PlayerStub;
import saving.mockclasses.RoomStub;
import saving.mockclasses.ScoreboardMock;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {
    @Test
    void onScoreChange_shouldIncrease() {
        ByteArrayOutputStream getFuckingJuked = new ByteArrayOutputStream();
        GameStub mockGame = new GameStub();
        RoomStub mockRoom = new RoomStub(mockGame, "testRoom");
        PlayerStub mockPlayer = new PlayerStub(mockRoom);
        ScoreboardMock mockScoreboard = new ScoreboardMock(new PrintStream(getFuckingJuked), mockPlayer.getScore());
        mockPlayer.addObserver(mockScoreboard);

        mockPlayer.addScore(10);

        assertTrue(mockScoreboard.scoreDifference > 0);
    }

    @Test
    void onScoreChange_shouldDecrease() {
        ByteArrayOutputStream getFuckingJuked = new ByteArrayOutputStream();
        GameStub mockGame = new GameStub();
        RoomStub mockRoom = new RoomStub(mockGame, "testRoom");
        PlayerStub mockPlayer = new PlayerStub(mockRoom);
        ScoreboardMock mockScoreboard = new ScoreboardMock(new PrintStream(getFuckingJuked), mockPlayer.getScore());
        mockPlayer.addObserver(mockScoreboard);

        mockPlayer.removeScore(10);

        assertTrue(mockScoreboard.scoreDifference < 0);
    }
}
