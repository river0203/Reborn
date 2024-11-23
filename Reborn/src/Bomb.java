public class Bomb {
    private boolean isDefused;

    public Bomb() {
        this.isDefused = false;
    }

    public void defuse() {
        this.isDefused = true;
        System.out.println("폭탄이 해제되었습니다!");
    }

    public boolean isDefused() {
        return isDefused;
    }
}
