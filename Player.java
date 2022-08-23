public class Player {
    private int score;
    private boolean holding;
    private int arrow;
    private int[] coord;

    public Player() {
        this.score = 0;
        this.holding = false;
        this.arrow = 1;
        this.coord = new int[] {0,0};
    }

    public void changeScore(int amount) {
        this.score += amount;
    }

    public int getScore() {
        return this.score;
    }

    public void grab() {
        this.holding = true;
    }

    public void release() {
        this.holding = false;
    }

    public int[] getCoord() {
        return this.coord;
    }

    public boolean canShoot() {
        return arrow > 0;
    }

    public boolean canMoveForward() {
        return this.coord[1] + 1 < 4;
    }

    public boolean canMoveBackward() {
        return this.coord[1] - 1 >= 0;
    }

    public boolean canMoveRight() {
        return this.coord[0] + 1 < 4;
    }

    public boolean canMoveLeft() {
        return this.coord[0] - 1 >= 0;
    }

    public void shoot() {
        this.arrow -= 1;
        this.score -= 10;
    }

    public void moveForward() {
        this.coord[1] += 1;
        this.score -= 1;
    }

    public void moveBackward() {
        this.coord[1] -= 1;
        this.score -= 1;
    }

    public void moveRight() {
        this.coord[0] += 1;
        this.score -= 1;
    }

    public void moveLeft() {
        this.coord[0] -= 1;
        this.score -= 1;
    }

    public boolean holding() {
        return this.holding;
    }
}
