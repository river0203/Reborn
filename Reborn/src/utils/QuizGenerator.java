package utils;

public class QuizGenerator {
    public static String generateQuestion(int number) {
        return "퀴즈 " + number + ": 이 문제는 난이도가 " + number + "입니다.";
    }

    public static String generateAnswer(int number) {
        return "정답" + number;
    }
}
