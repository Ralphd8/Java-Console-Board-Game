import java.util.*;
import java.io.*;

public class Grid{
    char arrGrid[][] = new char[18][18];
    Player player1;
    Player player2;

    public Grid(Player player1,Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public static int[] convertCoords(int posX,int posY){ // used to convert coods from 8*8 grid , to the the real bigGrid
        int[] res = new int[2];
        res[0] = 2*posX;
        res[1] = 2*posY;
        return res;
    }

    public void fillGridMainLines(){   // grid is empty now
        for(int i=0;i<arrGrid.length;i++){
            for(int j=0;j<arrGrid[0].length;j++){
                if(i==0){
                    if(j % 2 == 0 && j != 0){
                        arrGrid[i][j] = (char) ((j/2) + '0');
                    }
                }
                else if(i % 2 == 1){
                    if(j % 2 == 0 && j != 0){
                        arrGrid[i][j] = '-';
                    }
                }
                else{
                    if(j == 0){
                        arrGrid[i][j] = (char) ((i/2) + '0');
                    }
                    else{
                        if(j % 2 == 1){
                            arrGrid[i][j] = '|';
                        }
                    }
                }
            }
        }
    }

    public void fillPlayersPieces(){
        for(Piece p : player1.pieces){
            int[] correspondantPos = convertCoords(p.posX,p.posY);
            arrGrid[correspondantPos[0]][correspondantPos[1]] = player1.pieceShape.charAt(0);
        }
        for(Piece p : player2.pieces){
            int[] correspondantPos = convertCoords(p.posX,p.posY);
            arrGrid[correspondantPos[0]][correspondantPos[1]] = player2.pieceShape.charAt(0);
        }
    }

    public void clearGrid(){
        for(int i=2;i<=16;i=i+2){
            for(int j=2;j<=16;j=j+2){
                arrGrid[i][j] = 0;
            }
        }
    }

    public void drawGrid(){
        for(int i=0;i<arrGrid.length;i++){
            for(int j=0;j<arrGrid[0].length;j++){
                if(j== arrGrid[0].length-1){
                    if(arrGrid[i][j]== 0){
                        System.out.println(" ");
                    }
                    else{
                        System.out.println(arrGrid[i][j]);
                    }
                }
                else{
                    if(arrGrid[i][j]== 0){
                        System.out.print(" ");
                    }
                    else{
                        System.out.print(arrGrid[i][j]);
                    }
                }
            }
        }
    }

    /*public static void main(String[] args){
        Player player1 = new Player("Ralph","front","@");
        player1.fillPieces();
        Player player2 = new Player("Oliver","back","$");
        player2.fillPieces();
        Grid obj = new Grid(player1,player2);
        obj.fillGridMainLines();
        obj.fillPlayersPieces();
        obj.drawGrid();

    }*/

}