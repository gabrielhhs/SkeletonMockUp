package question;

import java.util.Scanner;

public class OpenQuestion implements Question {
    String question;

    public OpenQuestion(String question) {
        this.question = question;
    }

    public String ask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(question);
        System.out.print("Answer: ");
        String answer = scanner.nextLine();
        return answer;
    }
}