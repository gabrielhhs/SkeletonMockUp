package stratpattern;

import java.util.Scanner;

public class OpenQuestion implements Task {
    private String question;
    private String answer;
    private Scanner scan = new Scanner(System.in);

    public OpenQuestion() {}
    public OpenQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public boolean start() {
        System.out.println(this.question);
        if (scan.nextLine().trim().equalsIgnoreCase(this.answer)) {
            System.out.println("Well done you may live");
            return true;
        } else {
            System.out.println("DIE!!");
            return false;
        }
    }
}