package stratpattern;

import core.RoomStatus;
import rooms.Room;

public class OpenQuestion implements Task {
    private String question;
    private String answer;
    private Room room;

    public OpenQuestion(String question, String answer, Room room) {
        this.question = question;
        this.answer = answer;
        this.room = room;
    }

    @Override
    public void consume(String input) {
        if (input.equalsIgnoreCase(this.answer)) {
            this.room.setCleared();
            this.room.chooseRoom();
        } else {
            System.out.println("DIE!!");
            RoomStatus.CONFRONTING_QUESTION_MONSTER.activate();
            this.room.getParent().getPlayer().damage(1);
            //ToDo: implement summoning the monster
        }
    }

    @Override
    public void start() {
        System.out.println(this.question);
    }
}