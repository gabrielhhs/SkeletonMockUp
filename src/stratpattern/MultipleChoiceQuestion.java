package stratpattern;

import core.RoomStatus;
import rooms.Room;

public class MultipleChoiceQuestion implements Task {
    private String question;
    private String[] options;
    private int answer;
    private final Room room;
    private String hint;

    public MultipleChoiceQuestion(String question, String[] options, int answer, Room room, String hint) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.room = room;
        this.hint = hint;
    }
    public MultipleChoiceQuestion(String question, String[] options, int answer, Room room) {
        this(question, options, answer, room, null);
    }

    private void askQuestion() {
        System.out.println("Mysterious void: ANSWER OR DIE");
        System.out.println(this.question);
        for (int index = 0; index < this.options.length; index++) {
            System.out.println((index + 1) + "." + this.options[index]);
        }
    }

    private void handleAnswer(String input) {
        if (input.equalsIgnoreCase("hint")) {
            System.out.printf("Hint: %s%n", this.hint == null ? "None" : this.hint);
        } else if (input.matches("\\d+")) {
            if (Integer.parseInt(input) == this.answer) {
                System.out.println("Well done you may live");
                this.room.setCleared();
                this.room.chooseRoom();
            } else {
                System.out.println("DIE!!");
                RoomStatus.CONFRONTING_QUESTION_MONSTER.activate();
                this.room.getParent().getPlayer().damage(1);
                //ToDo: implement summoning the monster
            }
        } else {
            System.out.println("Choose a correct option number or DIE!! (pretty please)");
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