import java.util.*;

public class QuizGenerator {
    private static final int TOTAL_QUIZZES = 50;
    private static final List<Integer> usedQuizIndices = new ArrayList<>();
    private static final String[] questions = new String[TOTAL_QUIZZES];
    private static final String[] answers = new String[TOTAL_QUIZZES];

    static {
        for (int i = 0; i < TOTAL_QUIZZES; i++) {
            questions[i] = "퀴즈 " + (i + 1) + ": 질문 내용";
            answers[i] = "정답" + (i + 1);
        }
    }

    public static int getNextAvailableQuizIndex() {
        for (int i = 0; i < TOTAL_QUIZZES; i++) {
            if (!usedQuizIndices.contains(i)) {
                usedQuizIndices.add(i);
                return i;
            }
        }
        return -1; // 모든 퀴즈가 사용된 경우
    }

    public static void releaseQuizIndex(int index) {
        usedQuizIndices.remove(Integer.valueOf(index));
    }

    public static String getQuestion(int index) {
        return questions[index];
    }

    public static String getAnswer(int index) {
        return answers[index];
    }

}
