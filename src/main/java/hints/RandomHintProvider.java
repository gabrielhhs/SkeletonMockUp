package hints;

import java.util.Random;

public class RandomHintProvider implements HintProvider {
    private final HintProvider[] providers;

    public RandomHintProvider(HintProvider[] providers) {
        this.providers = providers;
    }

    public String getHint() {
        return this.providers[new Random().nextInt(this.providers.length)].getHint();
    }
}
