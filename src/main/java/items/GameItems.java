package items;

import core.Registry;
import items.consumables.*;

public abstract class GameItems {
	public static Registry<Item> register(Registry<Item> registry) {
		return registry.register(
			new GamblingPotionItem(),
			new HintJoker(),
			new SpareRibsItem(),
			new StaffOfClearingItem(),
			new SwordItem()
		);
	}
}