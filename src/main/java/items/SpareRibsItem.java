package items;

import core.Player;
import rooms.Room;

import java.util.Random;

public class SpareRibsItem implements Item {
    private static final Random random = new Random();

    @Override
    public void use(Player player, Room room) {
        if (random.nextInt(1, 6) == 5) doDamage(player);
        else player.heal(1);
        player.takeItem(this);
    }

    private void doDamage(Player player) {
        if (random.nextInt(1, 26) == 25) {
            System.out.println("A sharp bones pokes you in the cheeck. Ouch..");
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