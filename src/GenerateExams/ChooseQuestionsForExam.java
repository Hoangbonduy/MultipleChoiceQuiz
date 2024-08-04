package GenerateExams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ChooseQuestionsForExam {
    public static int GetNumberOfQuestionsForExam(List<String> idOfAllQuestions) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập số câu hỏi trong bài thi: ");
        int numberOfQuestionsForExam;
        do {
            System.out.print("Nhập số câu hỏi cho bài thi (số nguyên lớn hơn 0 và nhỏ hơn số câu hỏi trong file):");
            while (!scanner.hasNextInt()) {
                System.out.print("Đó không phải là số nguyên. Hãy thử lại:");
                scanner.next();
            }
            numberOfQuestionsForExam = scanner.nextInt();
        } while (numberOfQuestionsForExam <= 0 || numberOfQuestionsForExam > idOfAllQuestions.size());
        return numberOfQuestionsForExam;
    }
    public static List<String> GetIdOfAllQuestions(File cauhoifile) throws IOException {
        List<String> idOfQuestions = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(cauhoifile));
        String line = reader.readLine();
        while (line != null) {
            if (line.startsWith("id=")) {
                int index = line.indexOf("id=") + "id=".length();
                String lineId = line.substring(index);  // Lấy chuỗi sau "id="
                idOfQuestions.add(lineId);
            }
            line = reader.readLine();
        }
        reader.close();
        return idOfQuestions;
    }
    public static List<String> GetQuestionsIdForExam(List<String> idOfAllQuestions,int numberOfQuestionsForExam) {
        Random rand = new Random();
        List<String> idOfQuestionsForExam = new ArrayList<>();
        for (int i=1;i<=numberOfQuestionsForExam;i++) {
            int randomIndex = rand.nextInt(idOfAllQuestions.size());
            idOfQuestionsForExam.add(idOfAllQuestions.get(randomIndex));
            idOfAllQuestions.remove(randomIndex);
        }
        return idOfQuestionsForExam;
    }
}
