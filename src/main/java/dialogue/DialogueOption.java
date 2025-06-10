package dialogue;

public class DialogueOption {
    public String description;
    public String nextNodeId;

    public DialogueOption(String description, String nextNodeId) {
        this.description = description;
        this.nextNodeId = nextNodeId;
    }
}