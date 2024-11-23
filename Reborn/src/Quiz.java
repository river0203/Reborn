public class Quiz {
    private String question;
    private String answer;

    public Quiz(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public boolean checkAnswer(String userAnswer) {
        return answer.equals(userAnswer);
    }

    public String getQuestion() {
        return question;
    }
}
