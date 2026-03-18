import java.util.*;

public class Move {
        Player player1;
        Player player2;
        Piece p;
        boolean acceptableMove = false;
        boolean keepPlaying = false;
        int destX;
        int destY;
        boolean specialPiece = (p instanceof specialPiece) ? true : false;

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
            Player player = (checkingIfPieceBelongsToPlayer1() == true) ? player1 : player2;
            Player opponent = (player == player1) ? player2 : player1;
            String position = player.position;
            if(p.posX != destX && p.posY != destY){ // if diagnoal movement
                acceptableMove = false;
                System.out.println("Diagonal Move");
                return;
            }
            if(p.posX == destX && p.posY == destY){ // if destination == current position 
                acceptableMove = false;
                System.out.println("Destination Location == Current Location");
                return; 
            }
            if(destX > 8 || destX < 1 || destY > 8 || destY < 1){  // if destination outside the grid
                acceptableMove = false;
                System.out.println("Destination Location is Outside The Grid");
                return;
            }
            if(checkingIfDestBelongsToPieceColleugues() == true){ // if destination belongs to colleugues
                acceptableMove = false;
                System.out.println("Destination Location Belongs to Colleugues");
                return;
            }
            if(checkingIfDestIsEmptyPlace() == false){ // destination is not empty place
                acceptableMove = false;
                System.out.println("Destiation is not empty");
                return;
            }
            if(specialPiece == false){  
                if((position.equals("front") && destX > p.posX) || (position.equals("back") && destX < p.posX) || (Math.abs(destX - p.posX) + Math.abs(destY - p.posY)) > 2.0){ // if normalPiece and move backward or normalPiece and movementDistance > 2
                    acceptableMove = false;
                    System.out.println("Normal Piece and Move Backward || Movement > 2");
                    return;
                }
                if(Math.abs(destX - p.posX) + Math.abs(destY - p.posY) == 2.0){
                    int checkX = p.posX + ((destX - p.posX) / 2);
                    int checkY = p.posY + ((destY - p.posY) / 2);
                    int count = 0;
                    for(Piece pc : player.pieces){ // if colleugue piece between 
                        if(pc.posX == checkX && pc.posY == checkY){
                            acceptableMove = false;
                            System.out.println("Normal Piece , movement == 2 and in between colleugue piece");
                            return;
                        }
                    }
                    for(Piece pc : opponent.pieces){ // if opponent piece in between
                        if(pc.posX == checkX && pc.posY == checkY){
                            count++;
                            opponent.deletePiece(checkX,checkY);
                            keepPlaying = true;
                            p.changeLocation(destX,destY);
                            if((player.position.equals("front") && destX == 1) || (player.position.equals("back") && destX == 8)){
                                player.transitionToSpecialPiece(destX,destY);
                            }
                            acceptableMove = true;
                            return;
                        }
                    }
                    if(count == 0){
                        acceptableMove = false;
                        return;
                    }
                }
                if(Math.abs(destX - p.posX) + Math.abs(destY - p.posY) == 1.0){ // if movement distance == 1
                    p.changeLocation(destX,destY);
                    acceptableMove = true;
                    System.out.println("Successfull Move");
                    return;
                }
            }
            if(specialPiece == true){
                acceptableMove = true;
                if(p.posY == destY){
                    for(Piece pc : player.pieces){
                        if(pc == p){
                            continue;
                        }
                        if(pc.posY == destY && (double) pc.posX > Math.min(p.posX,destX) && (double) pc.posX < Math.max(p.posX,destX)){
                            p.changeLocation(destX,destY);
                            return;
                        }
                    }
                }
                if(p.posX == destX){
                    for(Piece pc : player.pieces){
                        if(pc == p){
                            continue;
                        }
                        if(pc.posX == destX && (double) pc.posY > Math.min(p.posY,destY) && (double) pc.posY < Math.max(p.posY,destY)){
                            p.changeLocation(destX,destY);
                            return;
                        }
                    }
                }
                int delX=1;
                int delY=1;
                int count = 0;
                if(p.posY == destY){
                    for(Piece pc : opponent.pieces){
                        if(pc.posY == destY && (double) pc.posX > Math.min(p.posX,destX) && (double) pc.posX < Math.max(p.posX,destX)){
                            count++;
                            delX = pc.posX;
                            delY = pc.posY;
                        }
                    }
                }
                if(p.posX == destX){
                    for(Piece pc : opponent.pieces){
                        if(pc.posX == destX && (double) pc.posY > Math.min(p.posY,destY) && (double) pc.posY < Math.max(p.posY,destY)){
                            count++;
                            delX = pc.posX;
                            delY = pc.posY;
                        }
                    }
                }
                if(count == 1){
                    p.changeLocation(destX,destY);
                    opponent.deletePiece(delX,delY);
                    keepPlaying = true;
                    return;
                }
                else{
                    p.changeLocation(destX,destY);
                    return;
                }
            }
        }

        public static void main(String[] args){
            Player player1 = new Player("Ralph","front","@");
            Player player2 = new Player("Oliver","back","$");
            player1.fillPieces();
            player2.fillPieces();
            Piece p = player1.returnPieceBasedOnCoords(7,1);
            Move move = new Move(player1,player2,p,6,1);
            move.checkingMoveValidity();


        }

}
