package hints;

public class UselessHint implements Hint {
    private String hint;

    public UselessHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String getHint() {
        return this.hint;
    }
}
