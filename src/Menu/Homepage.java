package Menu;

import java.util.Objects;
import java.util.Scanner;

public class Homepage {
    public static void DisplayHomepage() {
        System.out.println("CHƯƠNG TRÌNH LÀM BÀI TRẮC NGHIỆM");
        System.out.println("1.1. Thêm câu hỏi");
        System.out.println("1.2. Sửa câu hỏi");
        System.out.println("1.3. Xoá câu hỏi");
        System.out.println("2. Sinh đề thi và thi");
    }
    public static String GetUserChosenHomepage() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập lựa chọn của bạn (là các chỉ mục không bao gồm dấu chấm): ");
        String userChoice = sc.next();
        while (true) {
            if (Homepage.IsValidUserChosenHomepage(userChoice)) {
                return userChoice;
            }
            System.out.print("Nhập lại các lựa chọn: ");
            userChoice = sc.next();
        }
    }
    public static boolean IsValidUserChosenHomepage(String userChosenHomepage) {
        String[] validChoices = {"1.1","1.2","1.3","2"};
        for (String validChoice : validChoices) {
            if (Objects.equals(userChosenHomepage, validChoice)) {
                return true;
            }
        }
        return false;
    }
}
