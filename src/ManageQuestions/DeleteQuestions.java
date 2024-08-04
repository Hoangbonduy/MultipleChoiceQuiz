package ManageQuestions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeleteQuestions {
    public static List<Integer> GetDeleteQuestionWithId(String questionId,File cauhoiFile) throws IOException {
        boolean check = false;
        List<Integer> linesToDelete = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(cauhoiFile));
        String line = reader.readLine();
        int count = 0,countLine = 0;
        while (line != null) {
            countLine++;
            if (count == 7) {
                break;
            }
            if (line.startsWith("id=")) {
                int index = line.indexOf("id=") + "id=".length();
                String stringId = line.substring(index);  // Lấy chuỗi sau "id="
                if (stringId.equals(questionId)) {
                    linesToDelete.add(countLine);
                    check = true;
                    count++;
                }
            } else if (line.startsWith("content=") && check) {
                linesToDelete.add(countLine);
                count++;
            } else if (line.startsWith("choice1=") && check) {
                linesToDelete.add(countLine);
                count++;
            } else if (line.startsWith("choice2=") && check) {
                linesToDelete.add(countLine);
                count++;
            } else if (line.startsWith("choice3=") && check) {
                linesToDelete.add(countLine);
                count++;
            } else if (line.startsWith("choice4=") && check) {
                linesToDelete.add(countLine);
                count++;
            } else if (line.startsWith("answer=") && check) {
                linesToDelete.add(countLine);
                count++;
            }
            line = reader.readLine();
        }
        if (linesToDelete.isEmpty()) {
            System.out.println("Không tìm thấy câu hỏi có mã " + questionId);
        }
        return linesToDelete;
    }
    public static void DeleteQuestionWithId(List<Integer> linesToDelete,File cauhoiFile) throws IOException {
        if (linesToDelete.isEmpty()) {
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(cauhoiFile));
        List<String> linesToWrite = new ArrayList<>();
        int countLines = 0;
        String line = reader.readLine();
        while (line != null) {
            countLines++;
            if (!linesToDelete.contains(countLines)) {
                    linesToWrite.add(line);
            }
            line = reader.readLine();
        }
        reader.close();

        System.out.print(linesToWrite);

        BufferedWriter writer = new BufferedWriter(new FileWriter(cauhoiFile)); // Xoá file
        for (String lineToWrite : linesToWrite) {
            writer.write(lineToWrite);
            writer.newLine();
        }
        writer.close();
        System.out.println("Đã xoá thành công!");
    }
}