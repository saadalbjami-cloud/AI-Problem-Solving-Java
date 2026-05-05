package mc;

import java.util.Scanner;

public class MC {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        State initialState = new State(2, 2, 0, 0, 0); // Initial state: 2 missionaries, 2 cannibals on the left
        State goalState = new State(0, 0, 1, 2, 2);   // Goal state: all on the right
        State currentState = initialState;

        while (true) {
            System.out.println("Current state:");
            System.out.println(currentState);

            if (currentState.compareTo(goalState)) {
                System.out.println("Congratulations! You solved the puzzle.");
                break;
            }

            System.out.print("Enter the number of missionaries to move (0 or 1): ");
            int m = scanner.nextInt();

            System.out.print("Enter the number of cannibals to move (0 or 1): ");
            int c = scanner.nextInt();

            // Ensure the boat moves with at most one passenger or empty
            if (m + c > 1) {
                System.out.println("Invalid move. The boat can carry only one passenger or be empty.");
                continue;
            }

            State nextState;
            if (currentState.boat == 0) {
                // Boat on the left, move to the right
                nextState = new State(
                        currentState.m_l - m,
                        currentState.c_l - c,
                        1,
                        currentState.m_r + m,
                        currentState.c_r + c
                );
            } else {
                // Boat on the right, move to the left
                nextState = new State(
                        currentState.m_l + m,
                        currentState.c_l + c,
                        0,
                        currentState.m_r - m,
                        currentState.c_r - c
                );
            }

            if (isValid(nextState)) {
                currentState = nextState;
                System.out.println("Move successful.");
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        scanner.close();
    }

    // Check if a state is valid
    static boolean isValid(State state) {
        return state.m_l >= 0 && state.c_l >= 0 && state.m_r >= 0 && state.c_r >= 0
                && (state.m_l == 0 || state.m_l >= state.c_l) // No more cannibals than missionaries on the left
                && (state.m_r == 0 || state.m_r >= state.c_r); // No more cannibals than missionaries on the right
    }

    // State class
    static class State {
        int m_l, c_l, m_r, c_r, boat;

        State(int m_l, int c_l, int boat, int m_r, int c_r) {
            this.m_l = m_l;
            this.c_l = c_l;
            this.boat = boat;
            this.m_r = m_r;
            this.c_r = c_r;
        }

        boolean compareTo(State s) {
            return m_l == s.m_l && c_l == s.c_l && m_r == s.m_r && c_r == s.c_r && boat == s.boat;
        }

        @Override
        public String toString() {
            return m_l + " m " + m_r + "\n"
                    + c_l + " c " + c_r + "\n"
                    + (boat == 0 ? "1 b 0\n" : "0 b 1\n");
        }
    }
}
