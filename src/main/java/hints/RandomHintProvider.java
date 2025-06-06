package hints;

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
            return this.uselessHints.get(random.nextInt(this.uselessHints.size()));
        } else {
            for (FunctionalHint hint : this.functionalHints) {
                if (hint.getRoom().equals(room)) return hint;
            }
        }
    return null;
    }
}
