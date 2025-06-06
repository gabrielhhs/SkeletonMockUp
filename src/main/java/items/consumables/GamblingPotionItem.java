package items.consumables;

import core.Game;
import core.Player;
import items.Item;
import rooms.Room;

import java.util.Random;

public class GamblingPotionItem implements Item {
    private static final Random random = new Random();

    @Override
    public void use(Game game) {
        Player player = game.getPlayer();

        this.gamble(player);
        player.takeItem(this);
    }

    private void gamble(Player player) {
        switch (random.nextInt(1, 6)) {
            case 1 -> player.kill();
            case 2 -> player.damage(1);
            case 3 -> player.heal(1);
            case 4 -> { player.addScore(1); System.out.println("Enjoy an odd score number ya bozo"); }
            case 5 -> { player.changeHealth(random.nextInt(0, 6)); System.out.println("You feel a change from deep within you"); }
            default -> System.out.println("Nothing happened"); //default option to avoid accidental bound Random.next() bound mistakes
        }
    }

    @Override
    public String getName() {
        return "Potion";
    }
}