package commands.commandslist;

import core.Game;
import core.Player;
import commands.Command;
import commands.CommandManager;
import items.Item;

import java.util.Map;

public class InventoryCommand implements Command {
	private final String keyWord;

	public InventoryCommand(String keyWord) {
		this.keyWord = keyWord;
	}

	@Override
	public void run(CommandManager commandManager, String args) {
		Game game = commandManager.getParent();
		Player player = game.getPlayer();

		System.out.println("Inventory:");
		Map<String, Integer> inventory = player.getInventory();
		if (inventory.isEmpty()) {
			System.out.println("Empty");
			return;
		}
		for (Map.Entry<String, Integer> stack : inventory.entrySet()) {
			System.out.printf("%sx %s", stack.getValue(), commandManager.getParent().ITEMS.get(stack.getKey()).map(Item::getName).get());
		}
	}

	@Override
	public String getId() {
		return this.keyWord;
	}
}