package commands.commandslist;

import core.Game;
import core.Player;
import commands.Command;
import commands.CommandManager;
import items.Item;

import java.util.Map;

public class UseCommand implements Command {
	@Override
	public void run(CommandManager commandManager, String args) {
		Game game = commandManager.getParent();
		Player player = game.getPlayer();

		if (args == null) {
			System.out.printf("Usage: %s <item>", this.getKeyWord());
			return;
		}

		String itemName = args;
		Map<Item, Integer> inventory = player.getInventory();
		Item item = null;
		for (Item v : inventory.keySet()) {
			if (v.getName().equalsIgnoreCase(itemName)) {
				item = v;
				break;
			}
		}

		if (item == null) {
			System.out.println("You do not have this item!");
			return;
		}

		item.use(player, game.getPlayer().getCurrentRoom());
	}

	@Override
	public String getKeyWord() {
		return "use";
	}
}