import java.util.Scanner;

public class Game {

    static Scanner obj = new Scanner(System.in);

    static String player1_name;
    static String player1_position;
    static String player1_pieceShape;

    static String player2_name;
    static String player2_position;
    static String player2_pieceShape;

    static {
        System.out.println("Welcome to the game!");
        System.out.println("Player 1, please enter your name.");
        player1_name = obj.nextLine();

        do {
            System.out.println(player1_name + ", please choose your position.");
            System.out.println("Options to choose: front or back.");
            player1_position = obj.nextLine();
        } while (!player1_position.equals("front") && !player1_position.equals("back"));

        do {
            System.out.println(player1_name + ", please choose your piece shape.");
            System.out.println("Options to choose: @ or &");
            player1_pieceShape = obj.nextLine();
        } while (!player1_pieceShape.equals("@") && !player1_pieceShape.equals("&"));

        System.out.println("Player 2, please enter your name.");
        player2_name = obj.nextLine();

        player2_position = (player1_position.equals("front")) ? "back" : "front";
        player2_pieceShape = (player1_pieceShape.equals("@")) ? "&" : "@";

        System.out.println(
                player2_name + ", you will sit in the " + player2_position
                        + ", and your piece shape is: " + player2_pieceShape
        );
    }

    public static void main(String[] args) {

        Player player1 = new Player(player1_name, player1_position, player1_pieceShape);
        Player player2 = new Player(player2_name, player2_position, player2_pieceShape);

        player1.fillPieces();
        player2.fillPieces();

        Grid grid = new Grid(player1, player2);
        grid.fillGridMainLines();

        Player playerTurn = player1;

        do {

            int posX;
            int posY;
            int destX;
            int destY;

            Piece p;
            Move move;

            do {
                do {
                    grid.clearGrid();
                    grid.fillPlayersPieces();
                    grid.drawGrid();

                    System.out.println(playerTurn.getName() + " it's your turn!");
                    System.out.println(playerTurn.getName() + " please choose the piece you want to move.");

                    System.out.println("Enter X coords:");
                    posX = obj.nextInt();

                    System.out.println("Enter Y coords:");
                    posY = obj.nextInt();

                    p = playerTurn.returnPieceBasedOnCoords(posX, posY);

                } while (playerTurn.returnPieceBasedOnCoords(posX, posY) == null);

                System.out.println(playerTurn.getName() + " please choose where you want to move this piece.");

                System.out.println("Enter X coords:");
                destX = obj.nextInt();

                System.out.println("Enter Y coords:");
                destY = obj.nextInt();

                move = new Move(player1, player2, p, destX, destY);
                move.checkingMoveValidity();

                if (!move.isAcceptableMove()) {
                    System.out.println("Restricted move, please retry again");
                }

            } while (!move.isAcceptableMove());

            if (!move.isKeepPlaying()) {
                playerTurn = (playerTurn == player1) ? player2 : player1;
            }

        } while (!player1.checkForLost() && !player2.checkForLost());

        if (player1.checkForLost()) {
            System.out.println(player2.getName() + " wins!");
        } else {
            System.out.println(player1.getName() + " wins!");
        }
    }
}