package org.cameron.project;

import java.util.Scanner;

public class MyProject {
    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";

    public static void main(String[] args) throws InterruptedException {

        Logic logic = new Logic();
        Scanner sc = new Scanner(System.in);

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(GREEN + "Initializing game board...");

        // Loading bar
        String loadingBar =  "";
        int totalSteps = 50;
        for (int i = 0; i <= totalSteps; i++) {
            loadingBar = "=".repeat(i) + " ".repeat(totalSteps - i);
            System.out.print("\r[" + loadingBar + "]");
            int delay = 50 + logic.randomNumber(301);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nDone!\n");
        Thread.sleep(2000);

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(BLUE + "=================================================" + RESET);
        System.out.println(GREEN +
                "  \\ \\      / /__| | ___ ___  _ __ ___   ___   \n" +
                "   \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\  \n" +
                "    \\ V  V /  __/ | (_| (_) | | | | | |  __/  \n" +
                "     \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|  " + RESET);
        System.out.println(BLUE + "=================================================" + RESET);

        System.out.println(GREEN + "         2048  -  System Running");

        System.out.println("\nLet's play a game of 2048!");
        System.out.println("Type 'left', 'right', 'up', 'down' to move tiles.");
        System.out.println( "Type 'exit' to quit the game.");
        System.out.println("\nPress Enter to start...");
        sc.nextLine();

        int[][] board = new int[4][4];
        logic.initializeBoard(board);
        logic.printBoard(board);

        // Game loop
        while (true) {
            System.out.print("Enter move: ");
            String move = sc.nextLine().toLowerCase();

            if (move.equals("exit")) {
                System.out.println("Exiting game.");
                break;
            }

            boolean moved = false;
            switch (move) {
                case "left"  -> moved = logic.moveLeft(board);
                case "right" -> moved = logic.moveRight(board);
                case "up"    -> moved = logic.moveUp(board);
                case "down"  -> moved = logic.moveDown(board);
                default -> System.out.println("Invalid move. Please enter left, right, up, down or exit.");
            }

            if (moved) {
                logic.addRandomTile(board);
                System.out.print("\033[H\033[2J");
                System.out.flush();
                logic.printBoard(board);

                if (logic.isGameOver(board)) {
                    System.out.println("Game Over! Final Score: " + logic.getScore());
                    break;
                }
                if (logic.hasWon(board)) {
                    System.out.println("🎉 You made 2048! You win! Final Score: " + logic.getScore());
                    break;
                }
            }
        }
        sc.close();
    }
}