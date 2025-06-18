package rewards;

import java.util.Random;

public class RandomRewardProvider implements RewardProvider {
    private final RewardProvider[] pool;
    private final Random random = new Random();

    public RandomRewardProvider(RewardProvider... pool) {
        this.pool = pool;
    }

    public String getReward() {
        return this.pool[this.random.nextInt(pool.length)].getReward();
    }
}