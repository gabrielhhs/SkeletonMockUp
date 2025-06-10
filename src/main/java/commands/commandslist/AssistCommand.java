package commands.commandslist;

import commands.Command;
import commands.CommandManager;
import core.Player;
import items.consumables.HintJoker;

import java.util.Random;

public class AssistCommand implements Command {
    private int uses = 2;
    private static final String[] MOTIVATION_TEXT = { "Im proud of you", "You can do it", "I believe in you" };
    private static final String[] MOTIVATIONAL_PLAN = {"google the answer", "cheat by using \"Up, Up, Down, Down, Left, Right, Left, Right, B, A,\""};
    private static final Random RANDOM = new Random();

    public AssistCommand(int uses) {
        this.uses = uses;
    }

    @Override
    public void run(CommandManager commandManager, String args) {
        if (this.uses > 0) {
            HintJoker jokerItem = new HintJoker();
            if (this.jokerUsed(commandManager, jokerItem)) commandManager.getParent().getPlayer().takeItem(jokerItem);
            else { this.uses-- ; this.printExtraMotivation(); }
        } else this.removeCommand(commandManager);
    }

    private void removeCommand(CommandManager commandManager) {
        commandManager.removeCommand("assist");
        System.out.println("Invalid Command");
    }

    private boolean jokerUsed(CommandManager commandManager, HintJoker jokerItem) {
        Player player = commandManager.getParent().getPlayer();
        int initialCount = player.getInventory().getOrDefault(jokerItem, 0);

        player.giveItem(jokerItem);
        jokerItem.use(commandManager.getParent());

        return player.getInventory().getOrDefault(jokerItem, 0) > initialCount;
    }

    private void printExtraMotivation() {
        System.out.println("[Assistant] " + MOTIVATIONAL_PLAN[RANDOM.nextInt(0, MOTIVATIONAL_PLAN.length)]);
        System.out.println("[Assistant] " + MOTIVATION_TEXT[RANDOM.nextInt(0, MOTIVATION_TEXT.length)]);
    }

    public void setUses(int uses) {
        this.uses = uses;
    }

    @Override
    public String getKeyWord() {
        return "assist";
    }
}