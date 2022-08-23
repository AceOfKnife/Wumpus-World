public class Cell {

    private int value;
    private boolean wumpus;
    private boolean gold;
    private boolean pit;
    private boolean breeze;
    private boolean stench;

    public Cell() {
        this.value = 0;
        this.wumpus = false;
        this.gold = false;
        this.pit = false;
        this.breeze = false;
        this.stench = false;
    }

    public boolean onWumpus() {
        return wumpus;
    }

    public boolean onGold() {
        return gold;
    }

    public boolean onPit() {
        return pit;
    }

    public boolean isBreeze() {
        return breeze;
    }

    public boolean isStench() {
        return stench;
    }

    public int getVal() {
        return value;
    }

    public void setWumpus() {
        this.wumpus = true;
        this.value = -1000;
    }

    public void setPit() {
        this.pit = true;
        this.value = -1000;
    }

    public void setGold() {
        this.gold = true;
        this.value = 1000;
    }

    public void setStench() {
        if (!onWumpus() && !onPit())
            this.stench = true;
    }

    public void setBreeze() {
        if (!onWumpus() && !onPit())
            this.breeze = true;
    }

    public void moveGold() {
        this.gold = false;
        this.value = 0;
    }

    public void killWumpus() {
        this.wumpus = false;
        this.value = -1;
    }
}
