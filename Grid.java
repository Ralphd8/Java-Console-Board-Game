import java.util.*;
import java.io.*;

public class Grid{
    char arrGrid[][] = new char[18][18];

    public void fillGrid(){
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

    public static void main(String[] args){
        Grid obj = new Grid();
        obj.fillGrid();
        obj.drawGrid();
        //System.out.println(obj.arrGrid[0].length);

    }

}