package entities;

import dialogue.DialogueManager;
import events.Event;

public class AssistantEntity implements DialogueEntity {
    private DialogueManager dialogueManager = new DialogueManager("[Wooden doll] Hello there", this);
    private Event parent;

    public void consume(String input) {
        this.dialogueManager.consume(input);
    }

    @Override
    public void startDialogue() {
        this.dialogueManager.start();
    }

    @Override
    public void endDialogue() {
        this.parent.end();
    }

    public void setParent(Event parent) {
        this.parent = parent;
    }

    public DialogueManager getDialogueManager() {
        return this.dialogueManager;
    }
}