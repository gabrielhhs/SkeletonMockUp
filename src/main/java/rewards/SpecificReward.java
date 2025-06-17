package rewards;

public class SpecificReward implements RewardProvider {
    private String itemID;

    public SpecificReward(String itemID) {
        this.itemID = itemID;
    }

    public String reward() {
        return this.itemID;
    }
}