package items.consumables;

import core.Game;
import core.Player;
import items.Item;
import rooms.Room;

import java.util.Random;

public class SpareRibsItem implements Item {
    private static final Random random = new Random();

    @Override
    public void use(Game game) {
        Player player = game.getPlayer();

        if (random.nextFloat() < 0.2) this.doDamage(player);
        else player.heal(1);
        player.takeItem(this);
    }

    private void doDamage(Player player) {
        if (random.nextFloat() < 0.04) {
            System.out.println("A sharp bones pokes you in the cheek. Ouch..");
            player.damage(1);
        } else {
            System.out.println("You burnt your tongue (wait for it to cool down next time)");
        }
    }

    @Override
    public String getName() {
        return "Spare Ribs";
    }
}