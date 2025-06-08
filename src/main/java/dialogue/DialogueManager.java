package dialogue;


import entities.DialogueEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class DialogueManager {
    private final Map<String, DialogueNode> dialogueTree = new LinkedHashMap<>();
    private String currentNodeId;
    private final DialogueEntity parent;

    public DialogueManager(String startingText, DialogueEntity parent) {
        this.parent = parent;
        this.currentNodeId = "start";
        this.dialogueTree.put(this.currentNodeId, new DialogueNode(startingText));
    }

    public void start() {
        DialogueNode node = this.dialogueTree.get(this.currentNodeId);
        System.out.println(node.text);

        if (node.options.isEmpty()) moveToNextNode();
        else showOptions();
    }

    private void showOptions() {
        DialogueNode node = this.dialogueTree.get(this.currentNodeId);
        for (Map.Entry<String, DialogueOption> entry : node.options.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().description);
        }
    }

    private void moveToNextNode() {
        boolean foundCurrent = false;
        String nextNodeId = null;

        for (String nodeId : this.dialogueTree.keySet()) {
            if (foundCurrent) {
                nextNodeId = nodeId;
                break;
            }
            if (nodeId == null) break;
            if (nodeId.equals(this.currentNodeId)) {
                foundCurrent = true;
            }
        }

        if (nextNodeId != null) {
            this.currentNodeId = nextNodeId;
            start();
        } else {
            endDialogue();
        }
    }

    public void consume(String input) {
        DialogueNode node = this.dialogueTree.get(this.currentNodeId);
        DialogueOption chosenOption = node.options.get(input);

        if (chosenOption != null) {
            if (chosenOption.nextNodeId != null) {
                this.currentNodeId = chosenOption.nextNodeId;
                start();
            } else {
                endDialogue();
            }
        } else {
            System.out.println("That isn't a valid option.");
            showOptions();
        }
    }

    public void addNode(DialogueNode node, String nodeId) {
        this.dialogueTree.put(nodeId, node);
    }

    private void endDialogue() {
        parent.endDialogue();
    }
}