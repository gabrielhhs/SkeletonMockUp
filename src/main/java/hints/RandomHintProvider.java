package hints;

import core.DataSeeder;
import rooms.Room;
import java.util.List;
import java.util.Random;

public class RandomHintProvider {
    private List<FunctionalHintProvider> functionalHints = DataSeeder.getFunctionalHints();
    private List<LiteralHintProvider> uselessHints = DataSeeder.getUselessHints();

    public HintProvider getHint(Room room) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return this.uselessHints.get(random.nextInt(this.uselessHints.size()));
        } else {
            for (FunctionalHintProvider hint : this.functionalHints) {
                if (hint.getRoom().equals(room)) return hint;
            }
        }
        return null;
    }
}
