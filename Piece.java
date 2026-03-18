public abstract class Piece {
    int posX;
    int posY;
    
    public Piece(int posX,int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public void changeLocation(int newX,int newY){
        this.posX = newX;
        this.posY = newY;
    }

}

class normalPiece extends Piece{
    public normalPiece(int posX,int posY){
        super(posX,posY);
    }
}

class specialPiece extends Piece{
    public specialPiece(int posX,int posY){
        super(posX,posY);
    }
}


