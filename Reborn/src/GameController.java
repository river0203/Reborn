import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import utils.QuizGenerator; // QuizGenerator를 import

public class GameController {
    private JFrame frame;
    private JPanel bombPanel;
    private JButton startButton;
    private Bomb[] bombs;
    private Timer timer;
    private boolean gameOver;

    public GameController() {
        frame = new JFrame("Bomb Defusal Game");
        frame.setSize(1480, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        bombPanel = new JPanel();
        bombPanel.setLayout(new GridLayout(2, 3, 20, 20));
        frame.add(bombPanel, BorderLayout.CENTER);

        startButton = new JButton("게임 시작");
        frame.add(startButton, BorderLayout.SOUTH);

        bombs = new Bomb[6];
        timer = new Timer();
        gameOver = false;

        initializeBombs();
        initializeStartButton();

        frame.setVisible(true);
    }

    private void initializeBombs() {
        for (int i = 0; i < bombs.length; i++) {
            int timeLimit = (int) (Math.random() * 50) + 10; // 10~60초
            String question = QuizGenerator.generateQuestion(i + 1); // QuizGenerator 사용
            String answer = QuizGenerator.generateAnswer(i + 1);     // QuizGenerator 사용
            bombs[i] = new Bomb("Bomb " + (i + 1), timeLimit, question, answer);

            JButton bombButton = bombs[i].getButton();
            bombPanel.add(bombButton);

            bombButton.addActionListener(e -> showQuizDialog(bombs[i]));
        }
    }

    private void initializeStartButton() {
        startButton.addActionListener(e -> startGame());
    }

    public void startGame() {
        startButton.setEnabled(false);

        for (Bomb bomb : bombs) {
            timer.scheduleAtFixedRate(new TimerTask() {
                int remainingTime = bomb.getTimeLimit();

                @Override
                public void run() {
                    if (gameOver) {
                        timer.cancel();
                        return;
                    }
                    if (bomb.isDefused()) {
                        cancel();
                        return;
                    }
                    if (remainingTime <= 0) {
                        gameOver = true;
                        JOptionPane.showMessageDialog(frame, bomb.getName() + " 폭발! 게임 종료!");
                        System.exit(0);
                    } else {
                        bomb.getButton().setText(bomb.getName() + " (" + remainingTime-- + "s)");
                    }
                }
            }, 0, 1000);
        }
    }

    private void showQuizDialog(Bomb bomb) {
        if (bomb.isDefused()) {
            JOptionPane.showMessageDialog(frame, bomb.getName() + "는 이미 해제되었습니다!");
            return;
        }

        String userInput = JOptionPane.showInputDialog(frame, bomb.getQuestion(), "퀴즈: " + bomb.getName(), JOptionPane.QUESTION_MESSAGE);

        if (userInput != null && userInput.equalsIgnoreCase(bomb.getAnswer())) {
            bomb.defuse();
            bomb.getButton().setText(bomb.getName() + " 해제 완료!");
            JOptionPane.showMessageDialog(frame, bomb.getName() + " 해제 성공!");
        } else if (userInput != null) {
            JOptionPane.showMessageDialog(frame, "정답이 아닙니다. 다시 시도하세요.");
        }
    }
}
