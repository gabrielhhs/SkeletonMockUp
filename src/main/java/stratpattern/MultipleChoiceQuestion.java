package stratpattern;

import rooms.TaskRoom;

public abstract class MultipleChoiceQuestion implements Task {
    protected final String question;
    protected final String[] options;
    protected final int answer;
    protected TaskRoom parent;

    public MultipleChoiceQuestion(String question, String[] options, int answer, TaskRoom parent) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.parent = parent;
    }

    @Override
    public void consume(String input) {
        if (input.matches("\\d+")) {
            if (Integer.parseInt(input) == this.answer) {
                System.out.println("Well done you may live");
                this.parent.getParent().getPlayer().addScore(10);
                this.giveReward();
                this.setCleared();
            } else {
                System.out.println("You have failed you feel something something being taken away from your soul");
                this.parent.getParent().getPlayer().removeScore(10);
                this.setCleared();
            }
        } else {
            System.out.println("Choose a correct option number or DIE!! (pretty please)");
        }
    }

    protected void giveReward() {
        //ToDo: implement
    }

    protected void setCleared() {
        this.parent.setCleared();
        this.parent.chooseRoom();
    }

    @Override
    public final void start() {
        System.out.println("Mysterious void: ANSWER OR DIE");
        System.out.println(this.question);
        for (int index = 0; index < this.options.length; index++) {
            System.out.println((index + 1) + "." + this.options[index]);
        }
    }

    protected TaskRoom getParent() {
        return this.parent;
    }
}