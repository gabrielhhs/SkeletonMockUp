package core.hints;

import core.DataSeeder;
import rooms.Room;

import java.util.List;
import java.util.Random;

public class RandomHintProvider {
    List<FunctionalHint> functionalHints = DataSeeder.getFunctionalHints();
    List<UselessHint> uselessHints = DataSeeder.getUselessHints();

    public Hint getHint(Room room) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return uselessHints.get(random.nextInt(uselessHints.size()));
        } else {
            for (FunctionalHint hint : functionalHints) {
                if (hint.getRoom().equals(room)) return hint;
            }
        }
    return null;
    }
}
