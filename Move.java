import java.util.ArrayList;

public class Move {

    private Player player1;
    private Player player2;
    private Piece p;

    private boolean acceptableMove = false;
    private boolean keepPlaying = false;

    private int destX;
    private int destY;

    private boolean specialPiece;

    public Move(Player player1, Player player2, Piece p, int destX, int destY) {
        this.player1 = player1;
        this.player2 = player2;
        this.p = p;
        this.destX = destX;
        this.destY = destY;
        this.specialPiece = (p instanceof SpecialPiece);
    }

    public boolean isAcceptableMove() {
        return acceptableMove;
    }

    public boolean isKeepPlaying() {
        return keepPlaying;
    }

    public boolean checkingIfPieceBelongsToPlayer1() {
        for (Piece pc : player1.getPieces()) {
            if (p == pc) {
                return true;
            }
        }
        return false;
    }

    public boolean checkingIfDestBelongsToPieceColleugues() {
        Player player = checkingIfPieceBelongsToPlayer1() ? player1 : player2;

        for (Piece pc : player.getPieces()) {
            if (pc.getPosX() == destX && pc.getPosY() == destY && p != pc) {
                return true;
            }
        }

        return false;
    }

    public boolean checkingIfDestIsEmptyPlace() {
        ArrayList<Piece> array = new ArrayList<>();
        array.addAll(player1.getPieces());
        array.addAll(player2.getPieces());

        for (Piece piece : array) {
            if (destX == piece.getPosX() && destY == piece.getPosY()) {
                return false;
            }
        }

        return true;
    }

    public void checkingMoveValidity() {
        Player player = checkingIfPieceBelongsToPlayer1() ? player1 : player2;
        Player opponent = (player == player1) ? player2 : player1;

        if (isDiagonalMove()) {
            acceptableMove = false;
            System.out.println("Diagonal Move");
            return;
        }

        if (isSamePosition()) {
            acceptableMove = false;
            System.out.println("Destination Location == Current Location");
            return;
        }

        if (isOutsideGrid()) {
            acceptableMove = false;
            System.out.println("Destination Location is Outside The Grid");
            return;
        }

        if (checkingIfDestBelongsToPieceColleugues()) {
            acceptableMove = false;
            System.out.println("Destination Location Belongs to Colleugues");
            return;
        }

        if (!checkingIfDestIsEmptyPlace()) {
            acceptableMove = false;
            System.out.println("Destiation is not empty");
            return;
        }

        if (!specialPiece) {
            handleNormalPieceMove(player, opponent);
        }

        if (specialPiece) {
            handleSpecialPieceMove(player, opponent);
        }
    }

    private boolean isDiagonalMove() {
        return p.getPosX() != destX && p.getPosY() != destY;
    }

    private boolean isSamePosition() {
        return p.getPosX() == destX && p.getPosY() == destY;
    }

    private boolean isOutsideGrid() {
        return destX > 8 || destX < 1 || destY > 8 || destY < 1;
    }

    private void handleNormalPieceMove(Player player, Player opponent) {
        String position = player.getPosition();

        if ((position.equals("front") && destX > p.getPosX())
                || (position.equals("back") && destX < p.getPosX())
                || getMovementDistance() > 2.0) {

            acceptableMove = false;
            System.out.println("Normal Piece and Move Backward || Movement > 2");
            return;
        }

        if (getMovementDistance() == 2.0) {
            handleNormalPieceJump(player, opponent);
            return;
        }

        if (getMovementDistance() == 1.0) {
            p.moveTo(destX, destY);
            acceptableMove = true;

            if (shouldPromote(player)) {
                player.transitionToSpecialPiece(destX, destY);
            }

            System.out.println("Successfull Move");
        }
    }

    private void handleNormalPieceJump(Player player, Player opponent) {
        int checkX = p.getPosX() + ((destX - p.getPosX()) / 2);
        int checkY = p.getPosY() + ((destY - p.getPosY()) / 2);

        int count = 0;

        for (Piece pc : player.getPieces()) {
            if (pc.getPosX() == checkX && pc.getPosY() == checkY) {
                acceptableMove = false;
                System.out.println("Normal Piece , movement == 2 and in between colleugue piece");
                return;
            }
        }

        for (Piece pc : opponent.getPieces()) {
            if (pc.getPosX() == checkX && pc.getPosY() == checkY) {
                count++;
                opponent.deletePiece(checkX, checkY);
                keepPlaying = true;
                p.moveTo(destX, destY);

                if (shouldPromote(player)) {
                    System.out.println("Transitioned to special piece");
                    player.transitionToSpecialPiece(destX, destY);
                }

                acceptableMove = true;
                return;
            }
        }

        if (count == 0) {
            acceptableMove = false;
        }
    }

    private void handleSpecialPieceMove(Player player, Player opponent) {
        acceptableMove = true;

        if (p.getPosY() == destY) {
            for (Piece pc : player.getPieces()) {
                if (pc == p) {
                    continue;
                }

                if (pc.getPosY() == destY
                        && (double) pc.getPosX() > Math.min(p.getPosX(), destX)
                        && (double) pc.getPosX() < Math.max(p.getPosX(), destX)) {

                    p.moveTo(destX, destY);
                    return;
                }
            }
        }

        if (p.getPosX() == destX) {
            for (Piece pc : player.getPieces()) {
                if (pc == p) {
                    continue;
                }

                if (pc.getPosX() == destX
                        && (double) pc.getPosY() > Math.min(p.getPosY(), destY)
                        && (double) pc.getPosY() < Math.max(p.getPosY(), destY)) {

                    p.moveTo(destX, destY);
                    return;
                }
            }
        }

        int delX = 1;
        int delY = 1;
        int count = 0;

        if (p.getPosY() == destY) {
            for (Piece pc : opponent.getPieces()) {
                if (pc.getPosY() == destY
                        && (double) pc.getPosX() > Math.min(p.getPosX(), destX)
                        && (double) pc.getPosX() < Math.max(p.getPosX(), destX)) {

                    count++;
                    delX = pc.getPosX();
                    delY = pc.getPosY();
                }
            }
        }

        if (p.getPosX() == destX) {
            for (Piece pc : opponent.getPieces()) {
                if (pc.getPosX() == destX
                        && (double) pc.getPosY() > Math.min(p.getPosY(), destY)
                        && (double) pc.getPosY() < Math.max(p.getPosY(), destY)) {

                    count++;
                    delX = pc.getPosX();
                    delY = pc.getPosY();
                }
            }
        }

        if (count == 1) {
            p.moveTo(destX, destY);
            opponent.deletePiece(delX, delY);
            keepPlaying = true;
            return;
        }

        p.moveTo(destX, destY);
    }

    private double getMovementDistance() {
        return Math.abs(destX - p.getPosX()) + Math.abs(destY - p.getPosY());
    }

    private boolean shouldPromote(Player player) {
        return (player.getPosition().equals("front") && destX == 1)
                || (player.getPosition().equals("back") && destX == 8);
    }
}