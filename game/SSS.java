package sss;

import java.util.*;

public class SSS {
    static int[][] goalState; // الحالة الهدف يمكن تغييرها

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // تحديد الحالة الأولية
        int[][] state = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };

        // السماح للمستخدم بإدخال الحالة الهدف
        System.out.println("Enter the goal state (row by row, use 0 for the blank):");
        goalState = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                goalState[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Choose mode:\n1. Solve automatically using search\n2. Play manually");
        int choice = scanner.nextInt();

        if (choice == 1) {
            // حل اللغز باستخدام البحث العام
            solveUsingSearch(state);
        } else {
            // اللعب يدويًا
            playManually(state, scanner);
        }

        scanner.close();
    }

    // اللعب اليدوي
    static void playManually(int[][] state, Scanner scanner) {
        while (true) {
            System.out.println("Current state:");
            printState(state);

            // التحقق من الوصول إلى الحالة الهدف
            if (Arrays.deepEquals(state, goalState)) {
                System.out.println("Congratulations! You solved the puzzle.");
                break;
            }

            System.out.print("Enter move (up, down, left, right): ");
            String move = scanner.next().toLowerCase();

            int[] blank = findBlank(state);
            int newX = blank[0], newY = blank[1];

            switch (move) {
                case "up":
                    newX -= 1;
                    break;
                case "down":
                    newX += 1;
                    break;
                case "left":
                    newY -= 1;
                    break;
                case "right":
                    newY += 1;
                    break;
                default:
                    System.out.println("Invalid move. Please enter up, down, left, or right.");
                    continue;
            }

            if (newX < 0 || newX > 2 || newY < 0 || newY > 2) {
                System.out.println("Invalid move. Out of bounds.");
                continue;
            }

            // تحديث الحالة بعد الحركة
            applyMove(state, new int[]{newX, newY});
        }
    }

    // تطبيق الحركة عن طريق تبديل الخانة الفارغة مع الرقم المجاور
    static void applyMove(int[][] state, int[] move) {
        int[] blank = findBlank(state);
        // تبديل القيم
        int temp = state[blank[0]][blank[1]];
        state[blank[0]][blank[1]] = state[move[0]][move[1]];
        state[move[0]][move[1]] = temp;
    }

    // حل اللغز باستخدام البحث العام
    static void solveUsingSearch(int[][] initialState) {
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(new Node(initialState, null, ""));
        visited.add(Arrays.deepToString(initialState));

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (Arrays.deepEquals(currentNode.state, goalState)) {
                System.out.println("Solution found:");
                printSolutionPath(currentNode);
                return;
            }

            for (String move : new String[]{"up", "down", "left", "right"}) {
                int[][] newState = tryMove(currentNode.state, move);
                if (newState != null && visited.add(Arrays.deepToString(newState))) {
                    queue.add(new Node(newState, currentNode, move));
                }
            }
        }

        System.out.println("No solution found.");
    }

    // تطبيق الحركة
    static int[][] tryMove(int[][] state, String move) {
        int[] blank = findBlank(state);
        int newX = blank[0], newY = blank[1];

        switch (move) {
            case "up":
                newX -= 1;
                break;
            case "down":
                newX += 1;
                break;
            case "left":
                newY -= 1;
                break;
            case "right":
                newY += 1;
                break;
        }

        if (newX < 0 || newX > 2 || newY < 0 || newY > 2) {
            return null; // حركة غير صالحة
        }

        int[][] newState = copyState(state);
        newState[blank[0]][blank[1]] = newState[newX][newY];
        newState[newX][newY] = 0;
        return newState;
    }

    // طباعة المسار إلى الحل
    static void printSolutionPath(Node node) {
        if (node.parent != null) {
            printSolutionPath(node.parent);
        }
        System.out.println("Move: " + node.move);
        printState(node.state);
    }

    // نسخ الحالة
    static int[][] copyState(int[][] state) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(state[i], 0, newState[i], 0, 3);
        }
        return newState;
    }

    // إيجاد موضع الخانة الفارغة
    static int[] findBlank(int[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) return new int[]{i, j};
            }
        }
        return null;
    }

    // طباعة الحالة
    static void printState(int[][] state) {
        for (int[] row : state) {
            for (int n : row) {
                System.out.print(n + " ");
            }
            System.out.println();
        }
    }

    // عقدة البحث
    static class Node {
        int[][] state;
        Node parent;
        String move;

        Node(int[][] state, Node parent, String move) {
            this.state = state;
            this.parent = parent;
            this.move = move;
        }
    }
}
