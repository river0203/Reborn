import java.io.*;
import java.util.*;

public class QuizGenerator {
    private static final List<String> questions = new ArrayList<>();
    private static final List<String> answers = new ArrayList<>();
    private static final List<Integer> usedQuizIndices = new ArrayList<>();

    static {
        loadQuizzes("quiz_data.txt");
    }

    private static void loadQuizzes(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    questions.add(parts[0].trim());
                    answers.add(parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("퀴즈 데이터를 읽는 중 오류 발생: " + e.getMessage());
        }
    }

    public static int getNextAvailableQuizIndex() {
        for (int i = 0; i < questions.size(); i++) {
            if (!usedQuizIndices.contains(i)) {
                usedQuizIndices.add(i);
                return i;
            }
        }
        return -1;
    }

    public static void releaseQuizIndex(int index) {
        usedQuizIndices.remove(Integer.valueOf(index));
    }

    public static String getQuestion(int index) {
        return questions.get(index);
    }

    public static String getAnswer(int index) {
        return answers.get(index);
    }
}
