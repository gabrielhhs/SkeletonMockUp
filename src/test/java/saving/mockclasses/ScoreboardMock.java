package saving.mockclasses;

import core.Player;
import observer.Scoreboard;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

public class ScoreboardMock extends Scoreboard {
    private int startScore;
    public int scoreDifference;

    public ScoreboardMock(PrintStream output, int startScore) {
        super(output);
        this.startScore = startScore;
    }

    @Override
    public void onScoreChange(Player player) {
        super.onScoreChange(player);
        scoreDifference = player.getScore() - startScore;
    }
}