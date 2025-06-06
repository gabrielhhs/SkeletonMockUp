package core;

import rooms.Room;
import util.PathGetter;

import java.io.*;
import java.util.ArrayList;

public class Menu {
    private Game game;

    public Menu(Game game) {
        this.game = game;
    }
    public void options() {
        this.game.getStatusManager().set(GameStatus.IN_OPTION);
        System.out.println("""
                do you want to save, or go to the main menu?
                1. save game
                2. go to main menu
                3. resume
                """);
    }

    public void mainMenu() {
        this.game.getStatusManager().set(GameStatus.IN_MAIN_MENU);
        System.out.println("""
                Scrum-Masters Layer
                
                1. Load from save state
                2. Start new save (your other save will be deleted)
                3. quit game
                """);
    }

    public void saving(Player player) throws IOException {
        ArrayList<Room> roomList = DataSeeder.getRoomList();
        for (Room room : roomList) {
            try (FileWriter fileWriter = new FileWriter(PathGetter.resourcePath() + "/" + room.getName() + ".txt")) {
                fileWriter.write(String.valueOf(room.isCleared()));
            }
        }
        try (FileWriter fileWriter = new FileWriter(PathGetter.resourcePath() + "/positionalInfo.txt")) {
            fileWriter.write(player.getCurrentRoom().getName() + "\n");
            fileWriter.write(this.game.getStatusManager().getPrevious().toString());
        }
    }

    public void loadFromSave() {
        try {
            DataSeeder.setStatus(this.game.getStatusManager());
            DataSeeder.setRoomClears();
            DataSeeder.setPosition(this.game.getPlayer());
            this.game.getPlayer().getCurrentRoom().enter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startNewSave() {
        game.getPlayer().getCurrentRoom().enter();
    }
}
