package observer;

import core.Player;

public interface RoomObserver {
    public void onResultChange(Player player, int result);
    public void displayResult(Player player, int starsAmount);
}
