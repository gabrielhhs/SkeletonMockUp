package rewards;

public class SpecificReward implements RewardProvider {
    private final String itemID;

    public SpecificReward(String itemID) {
        this.itemID = itemID;
    }

    public String getReward() {
        return this.itemID;
    }
}