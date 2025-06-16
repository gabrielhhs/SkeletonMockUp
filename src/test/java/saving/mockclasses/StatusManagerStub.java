package saving.mockclasses;

import core.GameStatus;
import core.StatusManager;

public class StatusManagerStub extends StatusManager {
    public boolean getCalled = false;

    @Override
    public boolean is(GameStatus status) {
        System.out.println("[MOCK] StatusManagerStub.get() called." + status.toString());
        this.getCalled = true;
        return false;
    }
}
