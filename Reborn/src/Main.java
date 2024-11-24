public class Main {
    public static void main(String[] args) {
        // 퀴즈 로드 테스트
        for (int i = 0; i < 5; i++) {
            int index = QuizGenerator.getNextAvailableQuizIndex();
            System.out.println("퀴즈 " + (i + 1) + ": " + QuizGenerator.getQuestion(index) + " / 정답: " + QuizGenerator.getAnswer(index));
        }
    }
}
