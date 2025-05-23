package core;

public enum RoomStatus {
    SELECTING_ROOM(false),
    IN_TASK(false),
    CONFRONTING_QUESTION_MONSTER(false);

    private boolean status;

    RoomStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return this.status;
    }

    //setTrue() will clear all other statuses so you don't have to worry about forgetting to remove the old status
    public void setTrue() {
        for (RoomStatus roomStatus : values()) {
            roomStatus.setStatus(false);
        }
        this.status = true;
    }

    public static RoomStatus getActiveStatus() {
        for (RoomStatus roomStatus : values()) {
            if (roomStatus.getStatus()) {
                return roomStatus;
            }
        }
        return null;
    }

    private void setStatus(boolean status) {
        this.status = status;
    }
}
