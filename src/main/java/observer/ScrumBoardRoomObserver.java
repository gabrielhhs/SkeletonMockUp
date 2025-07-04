package observer;

import core.Player;

public class ScrumBoardRoomObserver implements RoomObserver {
    int endResult;

    @Override
    public void onResultChange (Player player, int result) {
        endResult = this.endResult + result;
        player.setScrumBoardRoomResult(endResult);
    }

    @Override
    public void displayResult(Player player, int starsAmount) {
        int starsCount = 1 + starsAmount;
        String stars = "*".repeat(starsCount);
        System.out.println("ScrumBoardRoom: " + stars);
    }
}