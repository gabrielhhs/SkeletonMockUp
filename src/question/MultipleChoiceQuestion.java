package question;

import java.util.Scanner;

public class MultipleChoiceQuestion implements Question {
    String question;
    String [] options;

    public MultipleChoiceQuestion(String question, String [] options) {
        this.question = question;
        this.options = options;
    }

    public String ask() {
        System.out.println(this.question);
        for (String each : this.options){
            System.out.println(each);
        }

        System.out.print("Take your best guess: ");
        return new Scanner(System.in).nextLine();
    }
}