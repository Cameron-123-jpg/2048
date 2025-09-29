package org.cameron.project;

import java.util.*;

public class Logic {
    private Random rand = new Random();
    private int score = 0;

    public int randomNumber(int number) {
        return rand.nextInt(number);
    }

    public int getScore() {
        return score;
    }

    public void initializeBoard(int[][] board) {
        for (int r = 0; r < 4; r++) {
            Arrays.fill(board[r], 0);
        }
        addRandomTile(board);
        addRandomTile(board);
    }

    public void addRandomTile(int[][] board) {
        List<int[]> empty = new ArrayList<>();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == 0) empty.add(new int[]{r, c});
            }
        }
        if (empty.isEmpty()) return; 
        int[] pos = empty.get(rand.nextInt(empty.size()));
        board[pos[0]][pos[1]] = (rand.nextInt(10) == 0) ? 4 : 2;
    }

    public void printBoard(int[][] board) {
        System.out.println("Score: " + score);
        System.out.println("+----+----+----+----+");
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == 0) {
                    System.out.print("|    ");
                } else {
                    System.out.printf("|%4d", board[r][c]);
                }
            }
            System.out.println("|");
            System.out.println("+----+----+----+----+");
        }
        System.out.println();
    }

    private int[] compressAndMerge(int[] line) {
        List<Integer> newLine = new ArrayList<>();
        for (int val : line) {
            if (val != 0) newLine.add(val);
        }

        for (int i = 0; i < newLine.size() - 1; i++) {
            if (Objects.equals(newLine.get(i), newLine.get(i + 1))) {
                newLine.set(i, newLine.get(i) * 2);
                score += newLine.get(i);
                newLine.remove(i + 1);
            }
        }

        while (newLine.size() < 4) newLine.add(0);

        return newLine.stream().mapToInt(Integer::intValue).toArray();
    }

    // Movement methods
    public boolean moveLeft(int[][] board) {
        boolean moved = false;
        for (int r = 0; r < 4; r++) {
            int[] newRow = compressAndMerge(board[r]);
            if (!Arrays.equals(board[r], newRow)) moved = true;
            board[r] = newRow;
        }
        return moved;
    }

    public boolean moveRight(int[][] board) {
        boolean moved = false;
        for (int r = 0; r < 4; r++) {
            int[] reversed = new int[4];
            for (int c = 0; c < 4; c++) reversed[c] = board[r][3 - c];
            int[] newRow = compressAndMerge(reversed);
            for (int c = 0; c < 4; c++) board[r][3 - c] = newRow[c];
            if (!Arrays.equals(reversed, newRow)) moved = true;
        }
        return moved;
    }

    public boolean moveUp(int[][] board) {
        boolean moved = false;
        for (int c = 0; c < 4; c++) {
            int[] col = new int[4];
            for (int r = 0; r < 4; r++) col[r] = board[r][c];
            int[] newCol = compressAndMerge(col);
            for (int r = 0; r < 4; r++) board[r][c] = newCol[r];
            if (!Arrays.equals(col, newCol)) moved = true;
        }
        return moved;
    }

    public boolean moveDown(int[][] board) {
        boolean moved = false;
        for (int c = 0; c < 4; c++) {
            int[] col = new int[4];
            for (int r = 0; r < 4; r++) col[r] = board[3 - r][c];
            int[] newCol = compressAndMerge(col);
            for (int r = 0; r < 4; r++) board[3 - r][c] = newCol[r];
            if (!Arrays.equals(col, newCol)) moved = true;
        }
        return moved;
    }

    public boolean isGameOver(int[][] board) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == 0) return false;
            }
        }
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if ((r < 3 && board[r][c] == board[r + 1][c]) ||
                    (c < 3 && board[r][c] == board[r][c + 1])) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasWon(int[][] board) {
    for (int r = 0; r < 4; r++) {
        for (int c = 0; c < 4; c++) {
            if (board[r][c] == 2048) return true;
        }
    }
    return false;
}
}