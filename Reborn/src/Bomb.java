import javax.swing.*; // JButton, JFrame 등을 사용하기 위해 추가

public class Bomb {
    private String name;
    private int timeLimit;
    private String question;
    private String answer;
    private int quizIndex; // 할당된 퀴즈의 인덱스
    private boolean defused;
    private JButton button; // JButton 선언

    public Bomb(String name, int timeLimit, String question, String answer, int quizIndex) {
        this.name = name;
        this.timeLimit = timeLimit;
        this.question = question;
        this.answer = answer;
        this.quizIndex = quizIndex;
        this.defused = false;
        this.button = new JButton(name); // JButton 초기화
    }

    public String getName() {
        return name;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getQuizIndex() {
        return quizIndex;
    }

    public boolean isDefused() {
        return defused;
    }

    public void defuse() {
        this.defused = true;
    }

    public JButton getButton() {
        return button;
    }
}
