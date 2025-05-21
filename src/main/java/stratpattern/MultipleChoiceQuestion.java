package stratpattern;

import java.util.Scanner;

public class MultipleChoiceQuestion implements Task {
    private String question;
    private String[] options;
    private int answer;
    private Scanner scan = new Scanner(System.in);

    public MultipleChoiceQuestion(String question, String[] options, int answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    private void askQuestion() {
        System.out.println("Mysterious void: ANSWER OR DIE");
        System.out.println(this.question);
        for (var entry : this.options) {
            System.out.println(entry);
        }
    }

    private boolean handleAnswer() {
        int input = this.scan.nextInt();
        if (input == this.answer) {
            System.out.println("Well done you may live");
            return true;
        } else {
            System.out.println("DIE!!");
            return false;
        }
    }

    @Override
    public boolean start() {
        askQuestion();
        return handleAnswer();
    }
}