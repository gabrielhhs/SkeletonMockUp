package commands.commandslist;

import core.Game;
import core.Player;
import commands.Command;
import commands.CommandManager;
import items.Item;

import java.util.Map;
import java.util.Optional;

public class UseCommand implements Command {
	@Override
	public void run(CommandManager commandManager, String args) {
		Game game = commandManager.getParent();
		Player player = game.getPlayer();

		if (args == null) {
			System.out.printf("Usage: %s <item>", this.getId());
			return;
		}

		String targetItemString = args;
		Map<String, Integer> inventory = player.getInventory();
		Item item = null;
		for (String itemId : inventory.keySet()) {
			Optional<Item> v = game.ITEMS.get(itemId);
			if (v.isPresent() && (v.get().getName().equalsIgnoreCase(targetItemString) || v.get().getId().equalsIgnoreCase(targetItemString))) {
				item = v.get();
				break;
			}
		}

		if (item == null) {
			System.out.println("You do not have this item!");
			return;
		}

		item.use(game);
	}

	@Override
	public String getId() {
		return "use";
	}
}