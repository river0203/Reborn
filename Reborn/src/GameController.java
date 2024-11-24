import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import utils.QuizGenerator;

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
        int quizIndex = QuizGenerator.getNextAvailableQuizIndex(); // 새 퀴즈 번호 가져오기
        if (quizIndex == -1) {
            JOptionPane.showMessageDialog(frame, "사용 가능한 퀴즈가 없습니다!");
            return;
        }
    
        // 새 폭탄 정보 설정
        int timeLimit = (int) (Math.random() * 50) + 10; // 10~60초
        String question = QuizGenerator.getQuestion(quizIndex);
        String answer = QuizGenerator.getAnswer(quizIndex);
    
        bombs[bombIndex] = new Bomb("Bomb " + (bombIndex + 1), timeLimit, question, answer, quizIndex);
    
        JButton bombButton = bombs[bombIndex].getButton();
        bombButton.setText("Bomb " + (bombIndex + 1));
        bombPanel.add(bombButton);
        bombPanel.revalidate();
        bombPanel.repaint();
    
        // 새로 할당된 폭탄 버튼에 이벤트 추가
        bombButton.addActionListener(e -> handleBombClick(bombs[bombIndex]));
    }
    
    private void handleBombClick(Bomb bomb) {
        if (bomb.isDefused()) {
            JOptionPane.showMessageDialog(frame, bomb.getName() + "는 이미 해제되었습니다!");
            return;
        }
    
        // 퀴즈 팝업 표시
        String userInput = JOptionPane.showInputDialog(frame, bomb.getQuestion(), "퀴즈: " + bomb.getName(), JOptionPane.QUESTION_MESSAGE);
    
        // 정답 확인
        if (userInput != null && userInput.equalsIgnoreCase(bomb.getAnswer())) {
            bomb.defuse();
            bomb.getButton().setText(bomb.getName() + " 해제 완료!");
            JOptionPane.showMessageDialog(frame, bomb.getName() + " 해제 성공!");
    
            // 해제된 퀴즈 번호 해제
            QuizGenerator.releaseQuizIndex(bomb.getQuizIndex());
    
            // 새로운 퀴즈를 폭탄에 할당
            assignQuizToBomb(getBombIndex(bomb));
        } else if (userInput != null) {
            JOptionPane.showMessageDialog(frame, "정답이 아닙니다. 다시 시도하세요.");
        }
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
