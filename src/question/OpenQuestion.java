package question;

import java.util.Scanner;

public class OpenQuestion implements Question {
    String question;

    public OpenQuestion(String question) {
        this.question = question;
    }

    public String ask() {
        System.out.println(question);
        System.out.print("Answer: ");
        return new Scanner(System.in).nextLine();
    }
}