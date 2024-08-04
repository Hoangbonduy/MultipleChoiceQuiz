package ManageQuestions;

import GenerateExams.ChooseQuestionsForExam;

import java.io.*;
import java.util.*;

public class ModifyQuestions {
    public static String GetQuestionId(File cauhoifile) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập id của câu hỏi: ");
        String questionId = scanner.nextLine();
        List<String> idOfAllQuestions = ChooseQuestionsForExam.GetIdOfAllQuestions(cauhoifile);
        while (!idOfAllQuestions.contains(questionId)) {
            System.out.print("Mã id cuả câu hỏi không có trong file. Vui lòng nhập lại: ");
            questionId = scanner.nextLine();
        }
        return questionId;
    }
    public static List<String> GetQuestionContentWithID(String questionId, File cauhoifile) throws IOException {
        List<String> contentQuestion = new ArrayList<>();
        List<Integer> questionLines = DeleteQuestions.GetDeleteQuestionWithId(questionId,cauhoifile);
        BufferedReader reader = new BufferedReader(new FileReader(cauhoifile));
        String line = reader.readLine();
        int countLine = 0;
        while (line != null) {
            countLine++;
            if (questionLines.contains(countLine)) {
                int indexEqual = line.indexOf("=");
                String content = line.substring(indexEqual + 1);
                contentQuestion.add(content);
            }
            line = reader.readLine();
        }
        reader.close();
        return contentQuestion;
    }
    public static void ReplaceLineWithIndex(int userChosenIndex,String modifyContent,File cauhoifile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(cauhoifile));
        List<String> linesToWrite = new ArrayList<>();
        int countLines = 0;
        String line = reader.readLine();
        while (line != null) {
            countLines++;
            if (countLines != userChosenIndex) {
                linesToWrite.add(line);
            } else {
                linesToWrite.add(modifyContent);
            }
            line = reader.readLine();
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(cauhoifile));
        for (String lineToWrite : linesToWrite) {
            writer.write(lineToWrite);
            writer.newLine();
        }
        writer.close();
        System.out.println("Đã sửa thành công!");
    }
    public static void ModifyQuestionContentWithId(List<Integer> questionContentWithId, String questionId, File cauhoifile) throws IOException {
        Scanner sc = new Scanner(System.in);
        List<String> validChoices = Arrays.asList("content", "choice1", "choice2", "choice3", "choice4", "answer");
        int minQuestionLines = Collections.min(questionContentWithId);
        System.out.println("Nhập phần mà bạn muốn sửa trong câu hỏi(content,choice1,choice2,choice3,choice4,answer),nhập -1 để thoát:");
        String userChosen;
        userChosen = sc.nextLine();
        while (true) {
            if (validChoices.contains(userChosen)) {
                System.out.println("Nhập nội dung bạn muốn thay đổi: ");
                String modifyContent = sc.nextLine();
                List<String> contentQuestion = ModifyQuestions.GetQuestionContentWithID(questionId,cauhoifile);
                while (true) {
                    if (!contentQuestion.contains("-1") && modifyContent.length() <= AddQuestions.MAXLENGTH) {
                        String beforeContent = "";
                        int userChosenIndex = validChoices.indexOf(userChosen) + 1;
                        if (validChoices.subList(1,5).contains(userChosen)) {
                            beforeContent = contentQuestion.get(userChosenIndex);
                            contentQuestion.set(userChosenIndex,modifyContent);
                            String answerContent = contentQuestion.get(6);
                            if (contentQuestion.subList(2,6).contains(answerContent)) {
                                userChosenIndex = userChosenIndex + minQuestionLines;
                                userChosen = userChosen + "=";
                                modifyContent = userChosen + modifyContent;
                                ModifyQuestions.ReplaceLineWithIndex(userChosenIndex,modifyContent,cauhoifile);
                                return;
                            } else {
                                contentQuestion.set(userChosenIndex,beforeContent);
                                System.out.println("Nội dung quá dài hoặc không khớp với lựa chọn/đáp án. Vui lòng nhập lại");
                                modifyContent = sc.nextLine();
                            }
                        }
                        else if (Objects.equals(userChosen, "answer")){
                            if (contentQuestion.subList(2,6).contains(modifyContent)) {
                                userChosenIndex = userChosenIndex + minQuestionLines;
                                userChosen = userChosen + "=";
                                modifyContent = userChosen + modifyContent;
                                ModifyQuestions.ReplaceLineWithIndex(userChosenIndex,modifyContent,cauhoifile);
                                return;
                            } else {
                                System.out.println("Nội dung quá dài hoặc không khớp với lựa chọn/đáp án. Vui lòng nhập lại");
                                modifyContent = sc.nextLine();
                            }
                        }
                        else if (Objects.equals(userChosen, "content")) {
                            beforeContent = contentQuestion.get(1);
                            if (!modifyContent.equals(beforeContent)) {
                                userChosenIndex = minQuestionLines + 1;
                                userChosen = userChosen + "=";
                                modifyContent = userChosen + modifyContent;
                                ModifyQuestions.ReplaceLineWithIndex(userChosenIndex,modifyContent,cauhoifile);
                                return;
                            } else {
                                System.out.println("Nội dung câu hỏi trùng với nội dung câu hỏi ban đầu. Vui lòng nhập lại:");
                                modifyContent = sc.nextLine();
                            }
                        }
                    }
                }
            } else {
                System.out.print("Bạn đã nhập sai. Vui lòng nhập lại: ");
                userChosen = sc.nextLine();
            }
        }
    }
}
