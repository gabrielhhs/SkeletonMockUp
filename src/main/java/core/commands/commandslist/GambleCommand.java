package core.commands.commandslist;

import core.Player;
import core.commands.Command;
import core.commands.CommandManager;

import java.util.Random;

public class GambleCommand implements Command {
    private final String keyWord;
    private final Random random = new Random();
    private Player player;

    public GambleCommand(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void run(CommandManager commandManager) {
        this.player = commandManager.getParent().getPlayer();
        gamble();
    }

    private void gamble() {
        switch (random.nextInt(1, 6)) {
            case 1 -> this.player.kill();
            case 2 -> this.player.damage(1);
            case 3 -> this.player.heal(1);
            case 4 -> { this.player.addScore(1); System.out.println("Enjoy an odd score number ya bozo"); }
            case 5 -> { this.player.changeHealth(random.nextInt(0, 6)); System.out.println("You feel a change from deep within you"); }
            default -> System.out.println(); //default option to avoid accidental bound Random.next() bound mistakes
        }
    }

    @Override
    public String getKeyWord() {
        return this.keyWord;
    }
}