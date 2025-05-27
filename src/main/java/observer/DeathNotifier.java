package observer;

import core.Player;

public class DeathNotifier implements Player.Observer {
    public static final DeathNotifier INSTANCE = new DeathNotifier();
    private DeathNotifier() {}

    @Override
    public void onDeath(Player player) {
        System.out.printf("YOU DIED!%nSCORE: %d%n(git gud)", player.getScore());
    }
}