package core;

public enum RoomStatus {
    SELECTING_ROOM,
    IN_TASK;

    private static RoomStatus currentStatus;

    static void setCurrentStatus(RoomStatus newStatus) {
        currentStatus = newStatus;
    }

    public static RoomStatus getActiveStatus() {
        return currentStatus;
    }

    public boolean isActive() {
        return this == currentStatus;
    }

    public void activate() {
        currentStatus = this;
    }
}
