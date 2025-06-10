package items.consumables;

import core.Game;
import core.Player;
import items.Item;
import rooms.Room;

public class StaffOfClearingItem implements Item {
    @Override
    public void use(Game game) {
        this.resetGame(game);
    }
    
    private void resetGame(Game game) {
        Player player = game.getPlayer();

        //game.getDataSaver().delete("save");
        player.clearInventory();
        player.setCurrentRoom(game.getInitialRoom());

        for (Room room : game.collectRooms()) room.setCleared(false);
    }

    @Override
    public String getName() {
        return "Staff of Clearing™";
    }
}