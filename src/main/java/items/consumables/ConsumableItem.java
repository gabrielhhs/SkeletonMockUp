package items.consumables;

import core.Player;
import items.Item;

public abstract class ConsumableItem implements Item {
    protected void consumeItem(Player player) {
        player.takeItem(this);
    }
}