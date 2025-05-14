package Question;

import java.util.Scanner;

public class OpenQuestion implements Question{
    String question;

    public OpenQuestion(String question){
        this.question = question;
    }

    public String ask(){
        System.out.println(question);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Answer: ");
        String answer = scanner.nextLine();
        return answer;
    }
}
