import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class GameController {
    private JFrame frame;
    private JPanel bombPanel;
    private JButton startButton;
    private Bomb[] bombs;
    private Timer timer;
    private boolean gameOver;

    public GameController() {
        frame = new JFrame("Bomb Defusal Game");
        frame.setSize(720, 1480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        bombPanel = new JPanel();
        bombPanel.setLayout(new GridLayout(3, 2, 20, 20));
        frame.add(bombPanel, BorderLayout.CENTER);

        startButton = new JButton("게임 시작");
        frame.add(startButton, BorderLayout.SOUTH);

        bombs = new Bomb[6];
        timer = new Timer();
        gameOver = false;

        initializeStartButton();

        frame.setVisible(true);
    }

    private void initializeStartButton() {
        startButton.addActionListener(e -> startGame());
    }

    public void startGame() {
        startButton.setEnabled(false);
        initializeBombs();
    }

    private void initializeBombs() {
        // 초기 폭탄 생성
        for (int i = 0; i < bombs.length; i++) {
            assignQuizToBomb(i);
        }

        // 폭탄의 타이머 시작
        for (Bomb bomb : bombs) {
            startBombTimer(bomb);
        }
    }

    private void assignQuizToBomb(int bombIndex) {
        int quizIndex = QuizGenerator.getNextAvailableQuizIndex();
        if (quizIndex == -1) {
            JOptionPane.showMessageDialog(frame, "퀴즈를 모두 사용했습니다!");
            return;
        }

        int timeLimit = (int) (Math.random() * 50) + 10; // 10~60초
        String question = QuizGenerator.getQuestion(quizIndex);
        String answer = QuizGenerator.getAnswer(quizIndex);

        Bomb bomb = new Bomb("Bomb " + (bombIndex + 1), timeLimit, question, answer, quizIndex);
        bombs[bombIndex] = bomb;

        JButton bombButton = bomb.getButton();
        bombPanel.add(bombButton);
        bombPanel.revalidate();
        bombPanel.repaint();

        // 폭탄 버튼 클릭 이벤트
        bombButton.addActionListener(e -> handleBombClick(bomb));
    }

    private void handleBombClick(Bomb bomb) {
        if (bomb.isDefused()) {
            JOptionPane.showMessageDialog(frame, bomb.getName() + "는 이미 해제되었습니다!");
            return;
        }
    
        String userInput = JOptionPane.showInputDialog(frame, bomb.getQuestion(), "퀴즈: " + bomb.getName(), JOptionPane.QUESTION_MESSAGE);
    
        if (userInput != null && userInput.equalsIgnoreCase(bomb.getAnswer())) {
            bomb.defuse();
            JOptionPane.showMessageDialog(frame, bomb.getName() + " 해제 성공!");
            QuizGenerator.releaseQuizIndex(bomb.getQuizIndex());
    
            // 기존 버튼 제거
            int bombIndex = getBombIndex(bomb);
            bombPanel.remove(bomb.getButton());
            bombPanel.revalidate();
            bombPanel.repaint();
    
            // 새로운 폭탄 생성
            Bomb newBomb = createNewBomb();
            bombs[bombIndex] = newBomb; // 배열에서 기존 폭탄 대체
            bombPanel.add(newBomb.getButton(), bombIndex); // 동일 위치에 새로운 버튼 추가
            bombPanel.revalidate();
            bombPanel.repaint();
        } else if (userInput != null) {
            JOptionPane.showMessageDialog(frame, "정답이 아닙니다. 다시 시도하세요.");
        }
    }
    
    // 새로운 폭탄 생성
    private Bomb createNewBomb() {
        int quizIndex = QuizGenerator.getNextAvailableQuizIndex();
        if (quizIndex == -1) {
            JOptionPane.showMessageDialog(frame, "퀴즈를 모두 사용했습니다!");
            return null;
        }
    
        int timeLimit = (int) (Math.random() * 50) + 10; // 10~60초
        String question = QuizGenerator.getQuestion(quizIndex);
        String answer = QuizGenerator.getAnswer(quizIndex);
    
        Bomb newBomb = new Bomb("Bomb", timeLimit, question, answer, quizIndex);
    
        // 버튼 클릭 이벤트 설정
        newBomb.getButton().addActionListener(e -> handleBombClick(newBomb));
        startBombTimer(newBomb);
    
        return newBomb;
    }
    
    
    private void startBombTimer(Bomb bomb) {
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

    private int getBombIndex(Bomb bomb) {
        for (int i = 0; i < bombs.length; i++) {
            if (bombs[i] == bomb) {
                return i;
            }
        }
        return -1;
    }
}
