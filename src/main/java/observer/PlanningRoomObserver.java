package observer;

import core.Player;
import rooms.Room;

public class PlanningRoomObserver implements RoomObserver {
    int endResult;

    @Override
    public void onResultChange (Player player, int result) {
        endResult = this.endResult + result;
        player.setPlanningRoomResult(endResult);
    }

    @Override
    public void displayResult(Player player, int starsAmount) {
        int starsCount = 1 + starsAmount;
        String stars = "*".repeat(starsCount);
        System.out.println("PlanningRoom: " + stars);
    }
}
