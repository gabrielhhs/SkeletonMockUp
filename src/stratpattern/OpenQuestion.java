package stratpattern;

import core.RoomStatus;
import rooms.Room;

public class OpenQuestion implements Task {
    private String question;
    private String answer;
    private Room room;
    private String hint;

    public OpenQuestion(String question, String answer, Room room, String hint) {
        this.question = question;
        this.answer = answer;
        this.room = room;
        this.hint = hint;
    }
    public OpenQuestion(String question, String answer, Room room) {
        this(question, answer, room, null);
    }

    @Override
    public void consume(String input) {
        if (input.equalsIgnoreCase("hint")) {
            System.out.printf("Hint: %s%n", this.hint == null ? "None" : this.hint);
        } else if (input.equalsIgnoreCase(this.answer)) {
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