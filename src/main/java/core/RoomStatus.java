package core;

public enum RoomStatus {
    SELECTING_ROOM,
    IN_TASK,
    IN_OPTION,
    IN_MAIN_MENU,
    IN_HINT;

    private static RoomStatus currentStatus;

    private static RoomStatus previousStatus;

    static void setCurrentStatus(RoomStatus newStatus) {
        currentStatus = newStatus;
    }

    public static RoomStatus getActiveStatus() {
        return currentStatus;
    }

    public boolean isActive() {
        return this == currentStatus;
    }

    public static RoomStatus getPreviousStatus() {
        return previousStatus;
    }

    public void activate() {
        previousStatus = currentStatus;
        currentStatus = this;
    }
}
