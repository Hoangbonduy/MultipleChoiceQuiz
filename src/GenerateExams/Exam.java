package GenerateExams;

import ManageQuestions.ModifyQuestions;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Exam {
    private static final int TIMEFOREACHQUESTION = 180; // seconds
    public static List<String> GetTime() {
        List<String> timeExam = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        timeExam.add(now.format(formatter));
        return timeExam;
    }
    public static void DisplayQuestionWithId(List<String> contentQuestion,int questionIndexInExam) {
        System.out.println("Câu hỏi " + questionIndexInExam + ": " + contentQuestion.get(1));
        System.out.println("A: " + contentQuestion.get(2));
        System.out.println("B: " + contentQuestion.get(3));
        System.out.println("C: " + contentQuestion.get(4));
        System.out.println("D: " + contentQuestion.get(5));
    }
    public static String GetUserChosen() {
        System.out.print("Nhập câu trả lời của ban vào đây (A,B,C,D): ");
        Scanner sc = new Scanner(System.in);
        String userChosen = sc.nextLine();
        while (!Objects.equals(userChosen, "A") && !Objects.equals(userChosen, "B") && !Objects.equals(userChosen, "C") && !Objects.equals(userChosen, "D")) {
            System.out.print("Bạn đã nhập sai. Vui lòng nhập lại: ");
            userChosen = sc.nextLine();
        }
        return userChosen;
    }
    public static boolean IsUserChosenCorrect(List<String> contentQuestion,String userChosen) {
        String questionAnswer = contentQuestion.get(6);
        if ((userChosen.equals("A") && Objects.equals(contentQuestion.get(2), questionAnswer))
        || (userChosen.equals("B") && Objects.equals(contentQuestion.get(3), questionAnswer))
        || (userChosen.equals("C") && Objects.equals(contentQuestion.get(4), questionAnswer))
        || (userChosen.equals("D") && Objects.equals(contentQuestion.get(5), questionAnswer))) {
            return true;
        }
        return false;
    }
    public static void DisplayExamEntrance() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Mỗi câu hỏi sẽ có " + TIMEFOREACHQUESTION + " để trả lời");
        System.out.print("Bấm B để bắt đầu bài thi: ");
        String userInput = sc.nextLine();
        while (!Objects.equals(userInput, "B")) {
            System.out.print("Vui lòng nhập lại: ");
            userInput = sc.nextLine();
        }
    }
    public static List<List<String>> TakeExam(List<String> questionsIdForExam,File cauhoifile) throws IOException {
        List<List<String>> exam = new ArrayList<>();
        double totalTimeTaken = 0.0;
        int userScore = 0;
        exam.add(Exam.GetTime());
        for (int i=1;i<=questionsIdForExam.size();i++) {
            List<String> contentQuestion = ModifyQuestions.GetQuestionContentWithID(questionsIdForExam.get(i-1),cauhoifile);
            Exam.DisplayQuestionWithId(contentQuestion,i);
            Instant start = Instant.now();
            String userChosen = GetUserChosen();
            boolean isUserChosenCorrect = Exam.IsUserChosenCorrect(contentQuestion,userChosen);
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            double seconds = timeElapsed.toMillis() / 1000.0; // chuyển đổi sang giây
            totalTimeTaken += seconds;
            boolean isValidTime = seconds > 180;
            if (isValidTime) {
                System.out.println("Bạn đã hoàn thành quá giờ nên câu hỏi sẽ không được tính điểm");
            } else if (isUserChosenCorrect) {
                userScore++;
            }
            List<String> contentQuestionCopy = contentQuestion;
            contentQuestionCopy.add(userChosen);
            contentQuestionCopy.add(String.valueOf(seconds));
            exam.add(contentQuestionCopy);
        }
        List<String> examResult = new ArrayList<>();
        examResult.add(String.valueOf(userScore));
        examResult.add(String.valueOf(questionsIdForExam.size()));
        examResult.add(String.valueOf(totalTimeTaken));
        exam.add(examResult);
        return exam;
    }
    public static void DisplayExamResult(List<List<String>> exam) {
        int examLength = exam.size();
        List<String> examResult = exam.get(examLength-1);
        System.out.println("Kết quả của bạn là:");
        System.out.println("Số câu đúng: " + examResult.get(0));
        System.out.println("Số câu hỏi: " + examResult.get(1));
        System.out.println("Tổng thời gian làm bài(tính cả các câu làm quá giờ): " + examResult.get(2) + " giây");
    }
    public static boolean IsSaveExamResult() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Bạn có muốn lưu dữ liệu bài làm vào file? Bấm Y để lưu và N để thoát: ");
        String userInput = scanner.nextLine();
        while (!Objects.equals(userInput, "Y") && !Objects.equals(userInput, "N")) {
            System.out.print("Bạn đã nhập sai. Vui lòng nhập lại: ");
            userInput = scanner.nextLine();
        }
        if (userInput.equals("Y")) return true;
        return false;
    }
    public static void SaveExamResult(List<List<String>> exam) throws IOException {
        int examLength = exam.size();
        File file = new File("out/production/ChuongTrinhLamBaiTracNghiem/Resources/baithi.bin");
        if (!file.exists()) {
            System.out.println("Không lưu được");
            return;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        List<String> time = exam.get(0);
        writer.write("Ngày giờ làm bài: " + time.get(0));
        writer.newLine();
        for (int i=1;i<examLength-1;i++) {
            List<String> contentQuestionWithAnswer = exam.get(i);
            writer.write("Nội dung câu hỏi " + i + " :" + contentQuestionWithAnswer.get(1));
            writer.newLine();
            writer.write("Phương án 1: " + contentQuestionWithAnswer.get(2));
            writer.newLine();
            writer.write("Phương án 2: " + contentQuestionWithAnswer.get(3));
            writer.newLine();
            writer.write("Phương án 3: " + contentQuestionWithAnswer.get(4));
            writer.newLine();
            writer.write("Phương án 4: " + contentQuestionWithAnswer.get(5));
            writer.newLine();
            writer.write("Câu trả lời: " + contentQuestionWithAnswer.get(7));
            writer.newLine();
            writer.write("Đáp án: " + contentQuestionWithAnswer.get(6));
            writer.newLine();
            writer.write("Thời gian làm bài cho câu hỏi: " + contentQuestionWithAnswer.get(8));
            writer.newLine();
        }
        List<String> examResult = exam.get(examLength-1);
        writer.write("Số câu trả lời đúng: " + examResult.get(0));
        writer.newLine();
        writer.write("Tổng số câu hỏi: " + examResult.get(1));
        writer.newLine();
        writer.write("Tổng thời gian làm bài: " + examResult.get(2));
        writer.newLine();

        writer.close();
        System.out.println("Đã lưu thành công!");
    }
}
