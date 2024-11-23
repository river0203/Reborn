import javax.swing.*;

public class Bomb {
    private String name;
    private int timeLimit;
    private String question;
    private String answer;
    private boolean defused;
    private JButton button;

    public Bomb(String name, int timeLimit, String question, String answer) {
        this.name = name;
        this.timeLimit = timeLimit;
        this.question = question;
        this.answer = answer;
        this.defused = false;
        this.button = new JButton(name);
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
