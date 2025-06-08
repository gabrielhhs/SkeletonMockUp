package events.eventtypes;

import commands.commandslist.AssistCommand;
import entities.AssistantEntity;
import events.Event;

public class AssistantEncounterEvent extends Event {
    private AssistantEntity assistant;

    public AssistantEncounterEvent(AssistantEntity assistant) {
        super(true);
        this.assistant = assistant;
        this.assistant.setParent(this);
    }

    @Override
    public void start() {
        System.out.println("As you enter you see an eerily human looking wooden figure standing in the middle of the room.");
        this.assistant.activate();
    }

    @Override
    public void end() {
        this.parent.getParent().getParent().getCommandManager().registerCommand(new AssistCommand());
        this.parent.getParent().getParent().getStatusManager().revert();
        this.parent.getParent().setCleared();
        this.parent.getParent().enter();
    }

    @Override
    public void consume(String input) {
        this.assistant.consume(input);
    }
}