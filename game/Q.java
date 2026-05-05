package q;

import java.util.*;

public class Q {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // اختيار الحالة الأولية
        System.out.println("Choose initial state:\n1. Random state\n2. Enter your own state");
        int choice = scanner.nextInt();
        int[] initialState;
        if (choice == 1) {
            initialState = randomState();
        } else {
            initialState = new int[8];
            System.out.println("Enter 8 numbers (0-7) separated by spaces (representing queen positions):");
            for (int i = 0; i <= 12; i++) {
                while (true) { // التحقق من صحة الإدخال
                    int input = scanner.nextInt();
                    if (input >= 0 && input <= 12) {
                        initialState[i] = input;
                        break; // إنهاء الحلقة إذا كان الإدخال صحيحًا
                    } else {
                        System.out.println("Invalid input. Please enter a number between 0 and 12:");
                    }
                }
            }
        }

        System.out.println("Initial state:");
        printState(initialState);

        // خوارزمية البحث العام
        PriorityQueue<State> openStates = new PriorityQueue<>(Comparator.comparingInt(State::getAttackers));
        Set<String> closedStates = new HashSet<>();
        openStates.add(new State(initialState, calcAttackers(initialState)));

        int numOfMoves = 0;

        while (!openStates.isEmpty()) {
            numOfMoves++;
            State currentState = openStates.poll(); // الحالة الأقل في الهجمات
            System.out.println("Move " + numOfMoves + ": Current attackers: " + currentState.attackers);
            printState(currentState.state);

            // التحقق من الحل
            if (currentState.attackers == 0) {
                finalPrinter(currentState.state);
                return;
            }

            // إضافة الحالة الحالية إلى الحالات المغلقة
            closedStates.add(Arrays.toString(currentState.state));

            // إنشاء الحالات التالية
            List<int[]> successors = generateSuccessors(currentState.state);
            for (int[] successor : successors) {
                String stateString = Arrays.toString(successor);
                if (!closedStates.contains(stateString)) {
                    openStates.add(new State(successor, calcAttackers(successor)));
                }
            }
        }

        System.out.println("Failed to find a solution.");
    }

    // Class to store a state and its attackers
    static class State {
        int[] state;
        int attackers;

        State(int[] state, int attackers) {
            this.state = state;
            this.attackers = attackers;
        }

        public int getAttackers() {
            return attackers;
        }
    }

    // توليد الحالات التالية
    public static List<int[]> generateSuccessors(int[] currState) {
        List<int[]> successors = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int row = 0; row < 8; row++) {
                if (currState[i] != row) {
                    int[] newState = currState.clone();
                    newState[i] = row;
                    successors.add(newState);
                }
            }
        }
        return successors;
    }

    public static int[] randomState() {
        int[] state = new int[8];
        for (int i = 0; i < 8; i++) {
            state[i] = (int) (Math.random() * 8);
        }
        return state;
    }

    public static void printState(int[] state) {
        for (int i : state) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static int calcAttackers(int[] currState) {
        int attackers = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                if (currState[i] == currState[j] || Math.abs(currState[i] - currState[j]) == Math.abs(i - j)) {
                    attackers++;
                }
            }
        }
        return attackers;
    }

    public static void finalPrinter(int[] currState) {
        System.out.println("\n┏━━━━━━━━━━━━━━┓");
        System.out.println("┃ S O L V E D! ┃");
        System.out.println("┗━━━━━━━━━━━━━━┛\n");
        System.out.print("Final state: ");
        printState(currState);
    }
}
