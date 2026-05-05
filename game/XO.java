package xo;

import java.util.*;

public class XO {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String initialState = ".........";  // Initial empty board
        String currentState = initialState;
        boolean isPlayerXTurn = true;

        while (true) {
            printBoard(currentState);
            System.out.println("Current Player: " + (isPlayerXTurn ? "X" : "O"));

            // Input from user
            System.out.print("Enter position (1-9): ");
            int position = scanner.nextInt() - 1;

            // Validate the move
            if (position < 0 || position > 8 || currentState.charAt(position) != '.') {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            // Make the move
            char currentPlayer = isPlayerXTurn ? 'X' : 'O';
            currentState = currentState.substring(0, position) + currentPlayer + currentState.substring(position + 1);

            // Check for win or draw
            if (checkWin(currentState, currentPlayer)) {
                printBoard(currentState);
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            }

            if (!currentState.contains(".")) {
                printBoard(currentState);
                System.out.println("It's a draw!");
                break;
            }

            // Switch turn
            isPlayerXTurn = !isPlayerXTurn;
        }
        scanner.close();
    }

    // Print the board in a readable format
    static void printBoard(String state) {
        for (int i = 0; i < 9; i += 3) {
            System.out.println(state.substring(i, i + 3));
        }
    }

    // Check if the current state is a winning state for the given player
    static boolean checkWin(String state, char player) {
        for (int i = 0; i < 3; i++) {
            if (state.charAt(i * 3) == player && state.charAt(i * 3 + 1) == player && state.charAt(i * 3 + 2) == player)
                return true;  // Row win
            if (state.charAt(i) == player && state.charAt(i + 3) == player && state.charAt(i + 6) == player)
                return true;  // Column win
        }
        return (state.charAt(0) == player && state.charAt(4) == player && state.charAt(8) == player) ||
               (state.charAt(2) == player && state.charAt(4) == player && state.charAt(6) == player);
    }
}
