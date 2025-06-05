package core.hints;

import rooms.Room;

import java.util.List;
import java.util.Random;

public class RandomHintProvider {
    List<FunctionalHint> functionalHints;
    List<UselessHint> uselessHints;

    public Hint getHint(Room room) {
        Random random = new Random();
        if (random.nextBoolean()) {
            return uselessHints.get(random.nextInt(uselessHints.size() + 1));
        } else {
            for (FunctionalHint hint : functionalHints) {
                if (hint.getRoom().equals(room)) return hint;
            }
        }
    return null;
    }
}
