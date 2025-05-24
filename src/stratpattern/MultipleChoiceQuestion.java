package stratpattern;

import core.RoomStatus;
import rooms.Room;

public class MultipleChoiceQuestion implements Task {
    private String question;
    private String[] options;
    private int answer;
    private final Room room;

    public MultipleChoiceQuestion(String question, String[] options, int answer, Room room) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.room = room;
    }

    private void askQuestion() {
        System.out.println("Mysterious void: ANSWER OR DIE");
        System.out.println(this.question);
        for (int index = 0; index < this.options.length; index++) {
            System.out.println(1 + index + "." + this.options[index]);
        }
    }

    private void handleAnswer(String input) {
        if (input.matches("\\d") && Integer.parseInt(input) == this.answer) {
            System.out.println("Well done you may live");
            this.room.setCleared();
            this.room.chooseRoom();
        } else if (!input.matches("\\d")){
            System.out.println("Choose a correct option number or DIE!! (pretty please)");
        } else {
            System.out.println("DIE!!");
            RoomStatus.CONFRONTING_QUESTION_MONSTER.activate();
            this.room.getParent().getPlayer().damage(1);
            //ToDo: implement summoning the monster

        }
    }

    @Override
    public void consume(String input) {
        this.handleAnswer(input);
    }

    @Override
    public final void start() {
        askQuestion();
    }
}