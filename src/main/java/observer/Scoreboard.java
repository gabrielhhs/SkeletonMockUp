package observer;

import core.Player;

public class Scoreboard implements Player.Observer {
    public static final Scoreboard INSTANCE = new Scoreboard();
    private Scoreboard() {}

    @Override
    public void onScoreChange(Player player) {
        System.out.printf("Your new score is: %d%n", player.getScore());
    }
}