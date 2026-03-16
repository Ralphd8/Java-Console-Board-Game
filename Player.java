import java.util.*;
import java.io.*;

public class Player {
    String name;
    String position; // front or back
    ArrayList<Piece> pieces = new ArrayList<>();
    
    public Player(String name,String position){
        this.name = name;
        this.position = position;
    }

    public void fillPieces(){
        if(position.equals("front")){
            for(int i=0;i<16;i++){
                if(i<8){
                    Piece p = new normalPiece(8,i+1);
                }
            }
        }
    }
    


}
