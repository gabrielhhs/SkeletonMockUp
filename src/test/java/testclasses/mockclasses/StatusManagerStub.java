package testclasses.mockclasses;

import core.GameStatus;
import core.StatusManager;

public class StatusManagerStub extends StatusManager {
    public boolean getCalled = false;
    private boolean status;

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean is(GameStatus status) {
        System.out.println("StatusManagerStub.is() in StatusManager called." + status.toString());
        this.getCalled = true;
        return false;
    }
}
