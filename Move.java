import java.util.*;

public class Move {
        Player player1;
        Player player2;
        Piece p;
        boolean acceptableMove = false;
        int destX;
        int destY;
        boolean specialPiece = (p instanceof specialPiece) ? true : false;
        int finalDestX;
        int finalDestY;
        Piece removed;

        public Move(Player player1,Player player2,Piece p,int destX,int destY){
            this.player1 = player1;
            this.player2 = player2;
            this.p = p;
            this.destX = destX;
            this.destY = destY;
        }

        public boolean checkingIfPieceBelongsToPlayer1(){
            for(Piece pc : player1.pieces){
                if(p == pc){
                    return true;
                }
            }
            return false;
        }

        public boolean checkingIfDestBelongsToPieceColleugues(){
            Player player = (checkingIfPieceBelongsToPlayer1() == true) ? player1 : player2;
            for(Piece pc : player.pieces){
                if(pc.posX == destX && pc.posY == destY && p != pc){
                    return true;
                }
            }
            return false;
        }

        public boolean checkingIfDestIsEmptyPlace(){
            ArrayList<Piece> array = new ArrayList<>();
            array.addAll(player1.pieces);
            array.addAll(player2.pieces);
            for(Piece p : array){
                if(destX == p.posX && destY == p.posY){
                    return false;
                }
            }
            return true;
        }

        public void checkingMoveValidity(){
            if(p.posX != destX && p.posY != destY){ // if diagnoal movement
                acceptableMove = false;
                return;
            }
            else if(p.posX == destX && p.posY == destY){ // if destination == current position 
                acceptableMove = false;
                return; 
            }
            else if(destX > 8 || destX < 1 || destY > 8 || destY < 1){  // if destination outside the grid
                acceptableMove = false;
                return;
            }
            else if(checkingIfDestBelongsToPieceColleugues() == true){ // if destination belongs to colleugues
                acceptableMove = false;
                return;
            }
            else if(checkingIfDestIsEmptyPlace() == false){ // destination is not empty place
                acceptableMove = false;
                return;
            }
            

            else if(specialPiece == false && checkingIfPieceBelongsToPlayer1() == true){

                if(player1.position.equals("front")){
                    if((destX != p.posX && destY != p.posY+1) || (destX != p.posX && destY != p.posY-1) || (destX != p.posX-1 && destY != p.posY)){
                        acceptableMove = false;
                        return;
                    }

                }
            }
            
        }


}
