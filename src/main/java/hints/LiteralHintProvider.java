package hints;

public class LiteralHintProvider implements HintProvider {
    private String hint;

    public LiteralHintProvider(String hint) {
        this.hint = hint;
    }

    @Override
    public String getHint() {
        return this.hint;
    }
}