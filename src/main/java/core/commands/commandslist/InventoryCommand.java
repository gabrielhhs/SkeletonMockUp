package core.commands.commandslist;

import core.Game;
import core.Player;
import core.commands.Command;
import core.commands.CommandManager;
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
		for (Map.Entry<Item, Integer> stack : player.getInventory().entrySet()) {
			System.out.printf("%sx %s", stack.getValue(), stack.getKey().getName());
		}
	}

	@Override
	public String getKeyWord() {
		return this.keyWord;
	}
}