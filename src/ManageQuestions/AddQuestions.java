package ManageQuestions;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class AddQuestions {
    public static final int MAXLENGTH = 200;
    public static String AddQuestionID(File cauhoifile) throws IOException {
        String questionId = "-1";

        BufferedReader reader = new BufferedReader(new FileReader(cauhoifile));
        String line = reader.readLine();
        while (line != null) {
            if (line.startsWith("id=")) {
                int index = line.indexOf("id=") + "id=".length();
                questionId = line.substring(index);  // Lấy chuỗi sau "id="
            }
            line = reader.readLine();
        }
        reader.close();

        if (!questionId.equals("-1")) {
            BigInteger bigId = new BigInteger(questionId);
            BigInteger big1 = new BigInteger("1");
            BigInteger bigNextId = bigId.add(big1);

            return bigNextId.toString();
        }
        return "1";
    }
    public static String AddQuestionContent() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập câu hỏi vào đây: ");
        String questionContent = sc.nextLine();
        while (true) {
            int questionContentLength = questionContent.length();
            if (questionContentLength > AddQuestions.MAXLENGTH) {
                System.out.print("Câu hỏi quá dài. Vui lòng nhập lại: ");
                questionContent = sc.nextLine();
            }
            else if (questionContentLength > 0){
                break;
            }
        }
        return questionContent;
    }
    public static List<String> AddQuestionChoices() {
        Scanner sc = new Scanner(System.in);
        List<String> questionChoices = new ArrayList<>();
        String[] questionChoiceSymbols = {"A", "B", "C", "D"};
        for (String questionChoiceSymbol : questionChoiceSymbols) {
            while (true) {
                System.out.print("Nhập phương án " + questionChoiceSymbol + " vào đây: ");
                String questionChoice = sc.nextLine();
                if (questionChoice.length() > AddQuestions.MAXLENGTH) {
                    System.out.print("Phương án quá dài. Vui lòng nhập lại: ");
                    questionChoice = sc.nextLine();
                } else if (!questionChoice.isEmpty()) {
                    questionChoices.add(questionChoice);
                    break;
                }
            }
        }
        return questionChoices;
    }
    public static String AddQuestionAnswer(List<String> questionChoices) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập phương án của câu hỏi vào đây: ");
        String questionAnswer = sc.nextLine();
        while (true) {
            if (questionAnswer.length() > AddQuestions.MAXLENGTH) {
                System.out.print("Phương án quá dài. Vui lòng nhập lại: ");
                questionAnswer = sc.nextLine();
            } else if (!questionAnswer.isEmpty()) {
                for (String questionChoice : questionChoices) {
                    if (Objects.equals(questionAnswer, questionChoice)) {
                        return questionAnswer;
                    }
                }
                System.out.print("Đáp án không trùng khớp. Vui lòng nhập lại: ");
                questionAnswer = sc.nextLine();
            }
        }
    }
    public static void AddQuestionToFile(String questionContent,List<String> questionChoices,String questionAnswer,String questionId,File cauhoifile) throws IOException {
        int questionChoicesLength = questionChoices.size();

        questionId = "id=" + questionId + "\n";
        questionContent = "content=" + questionContent + "\n";
        questionAnswer = "answer=" + questionAnswer + "\n";

        BufferedWriter writer = new BufferedWriter(new FileWriter(cauhoifile,true));
        writer.write(questionId);
        writer.write(questionContent);
        for (int i=1;i<=questionChoicesLength;i++) {
            String questionChoice = "choice" + i + "=" + questionChoices.get(i-1) + "\n";
            writer.write(questionChoice);
        }
        writer.write(questionAnswer);
        writer.close();
        System.out.println("Đã ghi câu hỏi thành công!");
    }
}