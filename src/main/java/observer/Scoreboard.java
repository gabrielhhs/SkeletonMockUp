package observer;

import core.Player;

import java.io.PrintStream;

public class Scoreboard implements Player.Observer {
    private final PrintStream output;

    public Scoreboard(PrintStream output) {
        this.output = output;
    }

    @Override
    public void onScoreChange(Player player) {
        output.printf("Your new score is: %d%n", player.getScore());
    }
}