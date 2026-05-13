public abstract class Piece {
    private int posX;
    private int posY;

    public Piece(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void moveTo(int newX, int newY) {
        this.posX = newX;
        this.posY = newY;
    }
}

class NormalPiece extends Piece {
    public NormalPiece(int posX, int posY) {
        super(posX, posY);
    }
}

class SpecialPiece extends Piece {
    public SpecialPiece(int posX, int posY) {
        super(posX, posY);
    }
}


