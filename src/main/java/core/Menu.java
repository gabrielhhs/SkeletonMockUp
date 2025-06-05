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
        RoomStatus.IN_OPTION.activate();
        System.out.println("""
                do you want to save, or go to the main menu?
                1. save game
                2. go to main menu
                3. resume
                """);
    }

    public void mainMenu() {
        RoomStatus.IN_MAIN_MENU.activate();
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
            fileWriter.write(RoomStatus.getPreviousStatus().toString());
        }
    }

    public void loadFromSave() {
        try {
            DataSeeder.setRoomClears();
            DataSeeder.setPosition(game.getPlayer());
            game.getPlayer().setCurrentRoom(game.getPlayer().getCurrentRoom());
            game.getPlayer().getCurrentRoom().enter();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void startNewSave() {
        game.getPlayer().getCurrentRoom().enter();
    }
}
