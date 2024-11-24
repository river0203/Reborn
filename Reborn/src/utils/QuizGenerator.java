package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class QuizGenerator {
    private static final ArrayList<Quiz> quizzes = new ArrayList<>();
    private static final ArrayList<Integer> usedQuizIndices = new ArrayList<>();

    static {
        loadQuizzesFromFile("resources/quizzes.txt"); // 퀴즈 파일 경로
    }

    // 퀴즈와 정답을 파일에서 읽어오기
    private static void loadQuizzesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 3); // 번호, 질문, 정답으로 분리
                if (parts.length == 3) { // 데이터가 정확히 3부분으로 나뉘는지 확인
                    int index = Integer.parseInt(parts[0].trim());
                    String question = parts[1].trim();
                    String answer = parts[2].trim();
                    quizzes.add(new Quiz(index, question, answer));
                } else {
                    System.err.println("잘못된 퀴즈 데이터: " + line);
                }
            }
            System.out.println("퀴즈 로드 완료: 총 " + quizzes.size() + "개");
        } catch (IOException e) {
            System.err.println("퀴즈 파일을 읽는 중 오류 발생: " + e.getMessage());
        }
    }

    // 사용 가능한 퀴즈 번호 찾기
    public static int getNextAvailableQuizIndex() {
        for (int i = 0; i < quizzes.size(); i++) {
            if (!usedQuizIndices.contains(i)) {
                usedQuizIndices.add(i); // 사용된 퀴즈로 추가
                return i; // 유효한 퀴즈 인덱스 반환
            }
        }
        System.err.println("모든 퀴즈가 이미 사용 중입니다.");
        return -1; // 사용 가능한 퀴즈가 없을 경우
    }

    // 사용된 퀴즈 번호 해제
    public static void releaseQuizIndex(int index) {
        usedQuizIndices.remove(Integer.valueOf(index));
    }

    // 퀴즈 반환
    public static String getQuestion(int index) {
        if (index >= 0 && index < quizzes.size()) {
            return quizzes.get(index).getQuestion();
        } else {
            System.err.println("유효하지 않은 퀴즈 인덱스: " + index);
            return "퀴즈를 찾을 수 없습니다.";
        }
    }

    public static String getAnswer(int index) {
        if (index >= 0 && index < quizzes.size()) {
            return quizzes.get(index).getAnswer();
        } else {
            System.err.println("유효하지 않은 퀴즈 인덱스: " + index);
            return "정답을 찾을 수 없습니다.";
        }
    }

    // 내부 Quiz 클래스
    private static class Quiz {
        private final int index;
        private final String question;
        private final String answer;

        public Quiz(int index, String question, String answer) {
            this.index = index;
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }
    }
}
