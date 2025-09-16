import java.util.*;

public class tictactoe {

    static String[] board;
    static String playerTurn;
    static Scanner sc = new Scanner(System.in);

    // Initialize board
    static void initializeBoard() {
        board = new String[9];
        for (int i = 0; i < 9; i++) {
            board[i] = String.valueOf(i + 1);
        }
        playerTurn = "X"; // Player always starts first
    }

    // Print the board
    static void printBoard() {
        System.out.println("|---|---|---|");
        System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
        System.out.println("|---|---|---|");
    }

    // Check winner
    static String checkWinner() {
        String[][] winPatterns = {
                {board[0], board[1], board[2]},
                {board[3], board[4], board[5]},
                {board[6], board[7], board[8]},
                {board[0], board[3], board[6]},
                {board[1], board[4], board[7]},
                {board[2], board[5], board[8]},
                {board[0], board[4], board[8]},
                {board[2], board[4], board[6]}
        };

        for (String[] line : winPatterns) {
            if (line[0].equals(line[1]) && line[1].equals(line[2])) {
                return line[0];
            }
        }

        for (int i = 0; i < 9; i++) {
            if (board[i].equals(String.valueOf(i + 1))) {
                return null; // Game not finished
            }
        }
        return "draw"; // No moves left → draw
    }

    // Minimax algorithm for AI
    static int minimax(String[] newBoard, String currentPlayer) {
        String result = checkWinner();
        if (result != null) {
            if (result.equals("O")) return 10;   // AI win
            else if (result.equals("X")) return -10; // Player win
            else return 0; // Draw
        }

        List<Integer> scores = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (newBoard[i].equals(String.valueOf(i + 1))) {
                newBoard[i] = currentPlayer;
                int score = minimax(newBoard, currentPlayer.equals("O") ? "X" : "O");
                scores.add(score);
                newBoard[i] = String.valueOf(i + 1);
            }
        }

        return (currentPlayer.equals("O")) ? Collections.max(scores) : Collections.min(scores);
    }

    // Best move for AI
    static int bestMove() {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;

        for (int i = 0; i < 9; i++) {
            if (board[i].equals(String.valueOf(i + 1))) {
                board[i] = "O";
                int score = minimax(board, "X");
                board[i] = String.valueOf(i + 1);

                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        return move;
    }

    public static void main(String[] args) {
        boolean playAgain = true;

        while (playAgain) {
            initializeBoard();
            String winner = null;

            System.out.println("Welcome to Advanced Tic-Tac-Toe!");
            System.out.println("You are X. Computer is O.");
            printBoard();

            while (winner == null) {
                if (playerTurn.equals("X")) {
                    System.out.println("Your turn; enter a slot number (1-9): ");
                    int move;
                    try {
                        move = sc.nextInt();
                        if (!(move > 0 && move <= 9)) {
                            System.out.println("Invalid input; try again.");
                            continue;
                        }
                        if (board[move - 1].equals(String.valueOf(move))) {
                            board[move - 1] = "X";
                            playerTurn = "O";
                        } else {
                            System.out.println("Slot already taken; try again.");
                            continue;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input; please enter a number.");
                        sc.nextLine();
                        continue;
                    }
                } else {
                    System.out.println("Computer's turn...");
                    int aiMove = bestMove();
                    board[aiMove] = "O";
                    playerTurn = "X";
                }

                printBoard();
                winner = checkWinner();
            }

            if (winner.equals("draw")) {
                System.out.println("It's a draw!");
            } else {
                System.out.println("Winner: " + winner);
            }

            System.out.println("Do you want to play again? (y/n)");
            char choice = sc.next().toLowerCase().charAt(0);
            playAgain = (choice == 'y');
        }

        System.out.println("Thanks for playing!");
    }
}
