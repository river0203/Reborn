import javax.swing.*;

public class Bomb {
    private String name;
    private int timeLimit;
    private String question;
    private String answer;
    private int quizIndex; // 퀴즈 번호
    private boolean defused;
    private JButton button;
    private static int bombCounter = 1;

public Bomb(String name, int timeLimit, String question, String answer, int quizIndex) {
    this.name = name + " (" + bombCounter++ + ")"; // 고유 ID 추가
    this.timeLimit = timeLimit;
    this.question = question;
    this.answer = answer;
    this.quizIndex = quizIndex;
    this.defused = false;
    this.button = new JButton(this.name);
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
