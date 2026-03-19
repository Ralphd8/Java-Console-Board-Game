import java.util.*;
import java.io.*;

public class Player {
    String name;
    String position; // front or back
    String pieceShape;
    ArrayList<Piece> pieces = new ArrayList<>();
    
    public Player(String name,String position,String pieceShape){
        this.name = name;
        this.position = position;
        this.pieceShape = pieceShape;
    }

    public void fillPieces(){
        if(position.equals("front")){
            for(int i=0;i<16;i++){
                Piece p;
                if(i<8){
                    p = new normalPiece(8,i+1);
                }
                else{
                    p = new normalPiece(7,i+1-8);
                }
                pieces.add(p);
            }
        }
        if(position.equals("back")){
            for(int i=0;i<16;i++){
                Piece p;
                if(i<8){
                    p = new normalPiece(1,i+1);
                }
                else{
                    p = new normalPiece(2,i+1-8);
                }
                pieces.add(p);
            }
        }
    }

    public void transitionToSpecialPiece(int posX,int posY){
        for(Piece p : pieces){
            if(p.posX == posX && p.posY == posY){
                pieces.remove(p);
                System.out.println("Old Normal piece removed!");
                break;
            }
        }
        Piece p = new specialPiece(posX,posY);
        pieces.add(p);
    }

    public void deletePiece(int posX,int posY){ // used when the player loose a piece
        for(Piece p : pieces){
            if(p.posX == posX && p.posY == posY){
                pieces.remove(p);
                break;
            }
        }
    }

    /*public boolean pieceBasedOnCoordsExistence(int posX,int posY){
        for(Piece pc : pieces){
            if(pc.posX == posX && pc.posY == posY){
                return true;
            }
        }
        return false;
    }*/

    public Piece returnPieceBasedOnCoords (int posX,int posY){
        for(Piece pc : pieces){
            if(pc.posX == posX && pc.posY == posY){
                return pc;
            }
        }
        return null;
    }

    public boolean checkForLost(){
        if(pieces.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    public static void main(String[] args){
        Player player = new Player("Ralph","front","@");
        player.fillPieces();
        ArrayList<Piece> arr = player.pieces;
        
        player.transitionToSpecialPiece(player.pieces.get(0).posX,player.pieces.get(0).posY);
        Piece p = player.pieces.get(player.pieces.size()-1);
        System.out.println(p instanceof specialPiece);
        System.out.println(p.posY);


    }

}
