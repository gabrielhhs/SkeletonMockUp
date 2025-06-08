package core;

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

    public void menuOptions(String input) {
        switch (input) {
            case "1" -> { this.game.getStatusManager().revert(); this.game.save("save"); this.game.getPlayer().getCurrentRoom().enter(); }
            case "2" -> this.mainMenu();
            case "3" -> this.game.getPlayer().getCurrentRoom().enter();
            default -> System.out.println("please type one of the numbers");
        }
    }

    public void mainMenuOptions(String input) {
        switch (input) {
            case "1" -> this.game.load("save");
            case "2" -> this.game.getPlayer().getCurrentRoom().enter();
            case "3" -> this.game.stop();
            default -> System.out.println("please type one of the numbers");
        }
    }
}
