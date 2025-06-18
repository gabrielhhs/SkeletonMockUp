package observer;

import core.Player;

import java.io.PrintStream;

public class DeathNotifier implements Player.Observer {
    private final PrintStream output;

    public DeathNotifier(PrintStream output) {
        this.output = output;
    }

    @Override
    public void onDeath(Player player) {
        output.printf("YOU DIED!%nSCORE: %d%n(git gud)", player.getScore());
    }
}