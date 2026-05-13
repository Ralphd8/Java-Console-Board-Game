import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private String position;
    private String pieceShape;

    List<Piece> pieces = new ArrayList<>();

    public Player(String name, String position, String pieceShape) {
        this.name = name;
        this.position = position;
        this.pieceShape = pieceShape;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getPieceShape() {
        return pieceShape;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void fillPieces() {

        if (position.equals("front")) {

            for (int i = 0; i < 16; i++) {

                Piece p;

                if (i < 8) {
                    p = new NormalPiece(8, i + 1);
                } else {
                    p = new NormalPiece(7, i + 1 - 8);
                }

                pieces.add(p);
            }
        }

        if (position.equals("back")) {

            for (int i = 0; i < 16; i++) {

                Piece p;

                if (i < 8) {
                    p = new NormalPiece(1, i + 1);
                } else {
                    p = new NormalPiece(2, i + 1 - 8);
                }

                pieces.add(p);
            }
        }
    }

    public void transitionToSpecialPiece(int posX, int posY) {

        Piece targetPiece = null;

        for (Piece p : pieces) {

            if (p.getPosX() == posX && p.getPosY() == posY) {
                targetPiece = p;
                break;
            }
        }

        if (targetPiece != null) {
            pieces.remove(targetPiece);
        }

        pieces.add(new SpecialPiece(posX, posY));
    }

    public void deletePiece(int posX, int posY) {

        Piece targetPiece = null;

        for (Piece p : pieces) {

            if (p.getPosX() == posX && p.getPosY() == posY) {
                targetPiece = p;
                break;
            }
        }

        if (targetPiece != null) {
            pieces.remove(targetPiece);
        }
    }

    public Piece returnPieceBasedOnCoords(int posX, int posY) {

        for (Piece p : pieces) {

            if (p.getPosX() == posX && p.getPosY() == posY) {
                return p;
            }
        }

        return null;
    }

    public boolean checkForLost() {
        return pieces.isEmpty();
    }
}