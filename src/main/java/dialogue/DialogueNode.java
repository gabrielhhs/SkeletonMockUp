package dialogue;

import java.util.LinkedHashMap;
import java.util.Map;

public class DialogueNode {
    public String text;
    public Map<String, DialogueOption> options = new LinkedHashMap<>();

    public DialogueNode(String text) {
        this.text = text;
    }

    public void addOption(String key, String description, String nextNodeId) {
        options.put(key, new DialogueOption(description, nextNodeId));
    }
}