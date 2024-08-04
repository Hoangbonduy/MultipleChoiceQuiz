import GenerateExams.ChooseQuestionsForExam;
import GenerateExams.Exam;
import ManageQuestions.AddQuestions;
import ManageQuestions.DeleteQuestions;
import ManageQuestions.ModifyQuestions;
import Menu.Homepage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        File cauhoifile = new File("out/production/ChuongTrinhLamBaiTracNghiem/Resources/cauhoi.bin");
        File baithifile = new File("out/production/ChuongTrinhLamBaiTracNghiem/Resources/baithi.bin");
        if (!cauhoifile.exists() || !baithifile.exists()) {
            System.out.println("Không mở được file");
            return;
        }
        Homepage.DisplayHomepage();
        String userChosenHomepage = Homepage.GetUserChosenHomepage();
        if (Objects.equals(userChosenHomepage, "1.1")) {
            String questionId = AddQuestions.AddQuestionID(cauhoifile);
            String questionContent = AddQuestions.AddQuestionContent();
            List<String> questionChoices = AddQuestions.AddQuestionChoices();
            String questionAnswer = AddQuestions.AddQuestionAnswer(questionChoices);
            AddQuestions.AddQuestionToFile(questionContent,questionChoices,questionAnswer,questionId,cauhoifile);
        } else if (Objects.equals(userChosenHomepage, "1.2")) {
            String questionId = ModifyQuestions.GetQuestionId(cauhoifile);
            List<Integer> questionContentWithId = DeleteQuestions.GetDeleteQuestionWithId(questionId,cauhoifile);
            ModifyQuestions.ModifyQuestionContentWithId(questionContentWithId,questionId,cauhoifile);
        } else if (Objects.equals(userChosenHomepage, "1.3")) {
            String questionId = ModifyQuestions.GetQuestionId(cauhoifile);
            List<Integer> linesToDelete = DeleteQuestions.GetDeleteQuestionWithId(questionId,cauhoifile);
            DeleteQuestions.DeleteQuestionWithId(linesToDelete,cauhoifile);
        } else if (Objects.equals(userChosenHomepage, "2")) {
            List<String> idOfAllQuestions = ChooseQuestionsForExam.GetIdOfAllQuestions(cauhoifile);
            int numberOfQuestionsForExam = ChooseQuestionsForExam.GetNumberOfQuestionsForExam(idOfAllQuestions);
            List<String> questionsIdForExam = ChooseQuestionsForExam.GetQuestionsIdForExam(idOfAllQuestions,numberOfQuestionsForExam);
            Exam.DisplayExamEntrance();
            List<List<String>> exam = Exam.TakeExam(questionsIdForExam,cauhoifile);
            Exam.DisplayExamResult(exam);
            if (Exam.IsSaveExamResult()) {
                Exam.SaveExamResult(exam);
            }
        }
    }
}