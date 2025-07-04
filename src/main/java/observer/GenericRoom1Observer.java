package observer;

import core.Player;
import rooms.Room;

public class GenericRoom1Observer implements RoomObserver {
    int endResult;

    @Override
    public void onResultChange (Player player, int result) {
        endResult = this.endResult + result;
        player.setGenericRoom1Result(endResult);
    }

    @Override
    public void displayResult(Player player, int starsAmount) {
        int starsCount = 1 + starsAmount;
        String stars = "*".repeat(starsCount);
        System.out.println("GenericRoom1Observer: " + stars);
    }
}
