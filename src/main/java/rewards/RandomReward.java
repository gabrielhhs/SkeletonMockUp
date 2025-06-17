package rewards;

import java.util.Random;

public class RandomReward {
    private final String[] pool;
    private final Random random = new Random();

    public RandomReward(String[] pool) {
        this.pool = pool;
    }

    public String reward() {
        return this.pool[this.random.nextInt(pool.length)];
    }
}