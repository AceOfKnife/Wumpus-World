import java.util.Random;
import java.util.Arrays;

public class World {
    
    private Cell[][] board;
    private int[][] interests;

    public World() {
        Random rand = new Random();
        board = new Cell[4][4];
        interests = new int[][] {{0,0}, {0,0}, {0,0}, {0,0}, {0,0}};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = new Cell();
            }
        }
        for (int i = 0; i < 5; i++) {
            int[] coord;
            do {
                coord = randomise(rand);
            } while(Arrays.equals(coord, interests[(i+1) % 5]) || Arrays.equals(coord, interests[(i+2) % 5]) 
                    || Arrays.equals(coord, interests[(i+3) % 5]) || Arrays.equals(coord, interests[(i+4) % 5]));
            interests[i] = coord;
        }
        board[interests[0][0]][interests[0][1]].setWumpus();
        board[interests[1][0]][interests[1][1]].setPit();
        board[interests[2][0]][interests[2][1]].setPit();
        board[interests[3][0]][interests[3][1]].setPit();
        board[interests[4][0]][interests[4][1]].setGold();

        adjWumpus(interests[0][0], interests[0][1]);
        adjPit(interests[1][0], interests[1][1]);
        adjPit(interests[2][0], interests[2][1]);
        adjPit(interests[3][0], interests[3][1]);
    }

    public int[] getWumpus() {
        return new int[] {interests[0][0], interests[0][1]};
    }

    public void killWumpus() {
        int x = interests[0][0];
        int y = interests[0][1];
        board[x][y].killWumpus();
        removeStench(x, y);
    }

    public void moveGold() {
        board[interests[4][0]][interests[4][1]].moveGold();
    }

    public void dropGold(int[] coord) {
        int x = coord[0];
        int y = coord[1];
        board[x][y].setGold();
    }

    public Cell getCell(int[] coord) {
        int x = coord[0];
        int y = coord[1];
        return board[x][y];
    }

    public boolean[] getInfo(int[] coord) {
        boolean[] info = new boolean[] {false, false, false, false, false};
        int x = coord[0];
        int y = coord[1];
        if (board[x][y].onGold())
            info[0] = true;
        if (board[x][y].isBreeze())
            info[1] = true;
        if (board[x][y].isStench())
            info[2] = true;
        if (board[x][y].onWumpus())
            info[3] = true;
        if (board[x][y].onPit())
            info[4] = true;
        return info;
    }

    public int[] randomise(Random rand) {
        int x;
        int y;
        do {
            x = rand.nextInt(4);
            y = rand.nextInt(4);
        } while(x == 0 && y == 0);

        return new int[] {x, y};
    }

    public void adjWumpus(int x, int y) {
        if(x+1 < 4)
            board[x+1][y].setStench();
        if(x-1 >= 0)
            board[x-1][y].setStench();
        if(y+1 < 4)
            board[x][y+1].setStench();
        if(y-1 >= 0)
            board[x][y-1].setStench();
    }

    public void removeStench(int x, int y) {
        if(x+1 < 4)
            board[x+1][y].removeStench();
        if(x-1 >= 0)
            board[x-1][y].removeStench();
        if(y+1 < 4)
            board[x][y+1].removeStench();
        if(y-1 >= 0)
            board[x][y-1].removeStench();
    }

    public void adjPit(int x, int y) {
        if(x+1 < 4)
            board[x+1][y].setBreeze();
        if(x-1 >= 0)
            board[x-1][y].setBreeze();
        if(y+1 < 4)
            board[x][y+1].setBreeze();
        if(y-1 >= 0)
            board[x][y-1].setBreeze();
    }

    public void printBoard() {
        for (int i = 3; i >= 0; i--) {
            System.out.println("-----------------");
            for (int j = 0; j < 4; j++) {
                System.out.print("| ");
                if (board[j][i].onGold())
                    System.out.print("G");
                else if (board[j][i].onPit())
                    System.out.print("P");
                else if (board[j][i].onWumpus())
                    System.out.print("W");
                else if (board[j][i].isBreeze() && board[j][i].isStench())
                    System.out.print("X");
                else if (board[j][i].isBreeze())
                    System.out.print("B");
                else if (board[j][i].isStench())
                    System.out.print("S");
                else
                    System.out.print(" ");
                System.out.print(" ");
            }
            System.out.print("|\n");
        }
        System.out.println("-----------------");
    }
}
