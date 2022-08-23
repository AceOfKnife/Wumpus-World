import java.util.Scanner;
import java.util.Arrays;

public class Game {

    private static boolean playing;
    private static Player player;
    private static World world;
    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Wumpus' World!");
        while (true) {
            Thread.sleep(1000);
            System.out.println("Begin Game? y/n");
            String confirm = filterInput(input.nextLine());
            if (confirm.equals("y"))
                break;
            else if (confirm.equals("n"))
                return;
            else
                System.out.println("Invalid input.");
        }
        playing = true;
        world = new World();
        player = new Player();
        boolean[] info = world.getInfo(player.getCoord());
        processInfo(info);
        do {
            if (player.getCoord()[0] == 0 && player.getCoord()[1] == 0 && player.holding()) {
                Thread.sleep(1000);
                System.out.println("You've made your way to the exit! Release the gold to win!");
            }
            Thread.sleep(1000);
            System.out.println("What will be your next move?");
            Thread.sleep(1000);
            System.out.println("W: Move Forward\nA: Move Left\nS: Move Backward\nD: Move Right\nG: Grab Gold\nH: Release Gold\nR: Shoot");
            String move = getMove(input);
            if (move.equals("w")) {
                if (!player.canMoveForward()) {
                    Thread.sleep(1000);
                    System.out.print("You proceed forwards and...");
                    Thread.sleep(1000);
                    System.out.print(" ouch! You bump into a wall. Try another direction.\n");
                    continue;
                }
                player.moveForward();
            } else if (move.equals("a")) {
                if (!player.canMoveLeft()) {
                    Thread.sleep(1000);
                    System.out.print("You try turning left and...");
                    Thread.sleep(1000);
                    System.out.print(" bump! There is a wall! Try another direction.\n");
                    continue;
                }
                player.moveLeft();
            } else if (move.equals("s")) {
                if (!player.canMoveBackward()) {
                    Thread.sleep(1000);
                    System.out.print("You try going backwards and...");
                    Thread.sleep(1000);
                    System.out.print(" nope! A deadend. Try another direction.\n");
                    continue;
                }
                player.moveBackward();
            } else if (move.equals("d")) {
                if (!player.canMoveRight()) {
                    Thread.sleep(1000);
                    System.out.print("You try turning right and...");
                    Thread.sleep(1000);
                    System.out.print(" boop! You've reached the walls of the cave. Try another direction.\n");
                    continue;
                }
                player.moveRight();
            } else if (move.equals("g")) {
                if (player.holding()) {
                    Thread.sleep(1000);
                    System.out.println("You are already holding the gold!");
                    Thread.sleep(1000);
                    continue;
                }
                if (!info[0]) {
                    Thread.sleep(1000);
                    System.out.print("You scour around and find...");
                    Thread.sleep(1000);
                    System.out.print(" dirt.");
                    Thread.sleep(1000);
                    System.out.print(" There is no gold here.\n");
                    continue;
                }
                player.grab();
                world.moveGold();
                Thread.sleep(1000);
                System.out.print("You scour around and find...");
                Thread.sleep(1000);
                System.out.print(" gold!");
                Thread.sleep(1000);
                System.out.print(" You are now carrying the gold. Make your way back to the exit!\n");
                continue;
            } else if (move.equals("h")) {
                if (!player.holding()) {
                    Thread.sleep(1000);
                    System.out.println("You are not holding anything.");
                    Thread.sleep(1000);
                    continue;
                }
                player.release();
                world.dropGold(player.getCoord());
                if (player.getCoord()[0] == 0 && player.getCoord()[1] == 0) {
                    Thread.sleep(1000);
                    System.out.print("Congratulations!");
                    Thread.sleep(1000);
                    System.out.print(" You have won.\n");
                    playing = false;
                    continue;
                }
                Thread.sleep(1000);
                System.out.println("You drop the gold in the room.");
                Thread.sleep(1000);
                continue;
            } else if (move.equals("r")) {
                if (!player.canShoot()) {
                    Thread.sleep(1000);
                    System.out.print("You try to reach for your arrow...");
                    Thread.sleep(1000);
                    System.out.print(" but you've run out!\n");
                    Thread.sleep(1000);
                    continue;
                }
                Thread.sleep(1000);
                System.out.println("Choose a direction to shoot in:");
                Thread.sleep(1000);
                System.out.println("W: Straight\nA: Left\nS: Behind\nD: Right\nC: Cancel");
                int res = shoot(input);
                Thread.sleep(1000);
                if (res == 2)
                    continue;
                System.out.println("You take aim and fire your arrow.");
                if (res == -1) {
                    Thread.sleep(1000);
                    System.out.println("Your arrow bounces on the wall and falls to the floor...");
                    Thread.sleep(1000);
                    System.out.println("You retrieve your arrow.");
                    continue;
                }
                if (res == 1) {
                    Thread.sleep(1000);
                    System.out.print("AHHHHHHHHHHHH!!!");
                    Thread.sleep(1000);
                    System.out.print(" The scream of the Wumpus can be heard throughout the cave.\n");
                    Thread.sleep(1000);
                    System.out.println("You have killed the Wumpus!");
                    world.killWumpus();
                } else if (res == 0) {
                    Thread.sleep(1000);
                    System.out.print("... no sound can be heard.");
                    Thread.sleep(1000);
                    System.out.print(" You have missed the Wumpus!\n");
                }
                player.shoot();
                continue;
            }
            Thread.sleep(1000);
            info = world.getInfo(player.getCoord());
            player.changeScore(world.getCell(player.getCoord()).getVal());
            processInfo(info);
        } while (playing);
        System.out.println("Player Score: " + player.getScore());
    }

    private static int shoot(Scanner input) {
        int[] wumpusLocation = world.getWumpus();
        int[] playerLocation = player.getCoord();
        String direction;
        boolean valid = false;
        do {
            direction = filterInput(input.nextLine());
            valid = (direction.equals("w") || direction.equals("a") || direction.equals("s") 
            || direction.equals("d") || direction.equals("c"));
            if (!valid)
                System.out.println("Invalid move. Try again.");
        } while (!valid);
        if (direction.equals("w")) {
            if (player.canMoveForward()) {
                if (playerLocation[0] == wumpusLocation[0] && playerLocation[1] + 1 == wumpusLocation[1])
                    return 1;
                return 0;
            }
            return -1;
        } else if (direction.equals("a")) {
            if (player.canMoveLeft()) {
                if (playerLocation[0] - 1 == wumpusLocation[0] && playerLocation[1] == wumpusLocation[1])
                    return 1;
                return 0;
            }
            return -1;
        } else if (direction.equals("s")) {
            if (player.canMoveBackward()) {
                if (playerLocation[0] == wumpusLocation[0] && playerLocation[1] - 1 == wumpusLocation[1])
                    return 1;
                return 0;
            }
            return -1;
        }  else if (direction.equals("d")) {
            if (player.canMoveRight()) {
                if (playerLocation[0] + 1 == wumpusLocation[0] && playerLocation[1] == wumpusLocation[1])
                    return 1;
                return 0;
            }
            return -1;
        } else if (direction.equals("c")) {
            return 2;
        }
        return 0;
    }

    private static void processInfo(boolean[] info) throws InterruptedException {
        if (info[3]) {
            Thread.sleep(1000);
            System.out.println("You walked into Wumpus' cave! Game Over.");
            Thread.sleep(1000);
            playing = false;
        } else if (info[4]) {
            Thread.sleep(1000);
            System.out.print("You proceed to the next room and...");
            Thread.sleep(1000);
            System.out.print(" AHHHHHHH!!! You fell into a pit! Game Over.\n");
            Thread.sleep(1000);
            playing = false;
        } else if (info[0] || info[1] || info[2]) {
            if (info[0]) {
                Thread.sleep(1000);
                System.out.println("There is something glittering in the room you are in!");
            }
            if (info[1]) {
                Thread.sleep(1000);
                System.out.println("You feel a slight breeze coming from one of the adjacent rooms...");
            } 
            if (info[2]) {
                Thread.sleep(1000);
                System.out.println("Ew! There is a pungent smell nearby!");
            }
        } else {
            Thread.sleep(1000);
            System.out.println("There is nothing of interest here.");
        }
    }

    private static String filterInput(String input) {
        return input.toLowerCase().trim();
    }

    private static String getMove(Scanner input) {
        String move;
        boolean valid = false;
        do {
            move = filterInput(input.nextLine());
            valid = (move.equals("w") || move.equals("a") || move.equals("s") 
            || move.equals("d") || move.equals("g") || move.equals("h") || move.equals("r"));
            if (!valid)
                System.out.println("Invalid move. Try again.");
        } while (!valid);
        return move;
    }
}