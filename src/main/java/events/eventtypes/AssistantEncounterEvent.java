package events.eventtypes;

import commands.commandslist.AssistCommand;
import dialogue.DialogueManager;
import dialogue.DialogueNode;
import entities.AssistantEntity;
import entities.DialogueEntity;
import events.Event;

public class AssistantEncounterEvent extends Event {
    private AssistantEntity assistant;

    public AssistantEncounterEvent(AssistantEntity assistant) {
        super(true);
        this.assistant = assistant;
        this.assistant.setParent(this);
        this.generateDialogue(this.assistant);
    }

    @Override
    public void start() {
        System.out.println("As you enter you see an eerily human looking wooden figure standing in the middle of the room.");
        this.assistant.startDialogue();
    }

    @Override
    public void end() {
        this.parent.getParent().getParent().getCommandManager().registerCommand(new AssistCommand(2)); //ToDo: decide on a final default value
        this.parent.getParent().getParent().getStatusManager().revert();
        this.parent.getParent().setCleared();
        this.parent.getParent().enter();
    }

    @Override
    public void consume(String input) {
        this.assistant.consume(input);
    }

    private void generateDialogue(DialogueEntity entity) {
        AssistantEntity assistant = (AssistantEntity) entity;
        DialogueManager manager = assistant.getDialogueManager();
        DialogueNode introNode = new DialogueNode("[Wooden doll] Its rare to see another soul enter this place\n[Assistant] Im known as the assistant");
        introNode.addOption("1", "Introduce yourself", "explanation");
        introNode.addOption("2", "stay silent", "explanation"); //basic etiquette skill cmon man (I need a better sleeping schedule)

        DialogueNode explanationNode = new DialogueNode("[Assistant] I'm here to help you during your travels");
        explanationNode.addOption("1", "Fucking finally took you long enough", "mean0");
        explanationNode.addOption("2", "It's nice to finally speak to someone that doesn't want to kill me", "nice0");

        DialogueNode meanDoll0 = new DialogueNode("[Assistant] Well that's kind of mean but I understand your perspective");
        meanDoll0.addOption("1", "Well? What kind of assistance will you be providing me with?", "mean1");

        DialogueNode niceDoll0 = new DialogueNode("[Assistant] Sadly it wont end here but you're in luck for I am here");
        niceDoll0.addOption("1", "You bring me joy what kind of amazing assistance can I be expecting?", "nice1");

        DialogueNode meanDoll1 = new DialogueNode("""
                [Assistant] You should worry more about your karma rating
                But anyway I will provide my assistance by giving you hints when you are in need
                Simply use '/assist' and I will come to your aid
                """);
        meanDoll1.addOption("1", "I pray you make yourself useful", null);
        meanDoll1.addOption("2", "Sorry about the earlier comments that was mean", "redemption");

        DialogueNode niceDoll1 = new DialogueNode("""
                [Assistant] You flatter me, I will guide you whenever you call upon me by using '/assist'
                Sadly due to rules set by beings beyond our comprehension I can only help you twice, so make sure you decide carefully when you call me
                """); //still lf sleep schedule
        niceDoll1.addOption("1", "Thank you for your help I will make great use of your assistance", null);

        DialogueNode meanDoll2 = new DialogueNode("[Assistant] No problems apology accepted now go forth");
        DialogueNode meanDoll3 = new DialogueNode("[Assistant] I reiterate watch ur karma rating man");

        DialogueNode niceDoll2 = new DialogueNode("[Assistant] I'm please to help");

        manager.addNode(introNode, "intro");
        manager.addNode(explanationNode, "explanation");
        manager.addNode(meanDoll0, "mean0");
        manager.addNode(niceDoll0, "nice0");
        manager.addNode(niceDoll1, "nice1");
        manager.addNode(niceDoll2, null);
        manager.addNode(meanDoll1, "mean1");
        manager.addNode(meanDoll2, "redemption");
        manager.addNode(meanDoll3, null);
    }
}