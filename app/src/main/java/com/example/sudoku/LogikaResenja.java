package com.example.sudoku;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class LogikaResenja {

     int oznacenRed;
     int oznacenaKol;

    int [][] tabla;
    ArrayList<ArrayList<Object>> praznaCelijaIndex;



    LogikaResenja(){
        oznacenRed = -1;
        oznacenaKol = -1;

        tabla =  new int[9][9];
        for(int r=0; r<9; r++){
            for(int k=0; k<9; k++){
                tabla[r][k]=0;
            }
        }
        praznaCelijaIndex = new ArrayList<>();
    }

    public void getPraznaCelijaIndex(){
        for(int r=0; r<9; r++){
            for(int k=0; k<9; k++){
                if(this.tabla[r][k]==0){
                    this.praznaCelijaIndex.add(new ArrayList<>());
                    this.praznaCelijaIndex.get(this.praznaCelijaIndex.size()-1).add(r);
                    this.praznaCelijaIndex.get(this.praznaCelijaIndex.size()-1).add(k);
                }
            }
        }
    }

    public void setPozicijaBroja(int broj){
        if(this.oznacenaKol != -1 && this.oznacenRed != -1){
            if(this.tabla[this.oznacenRed-1][this.oznacenaKol-1] == broj){
                this.tabla[this.oznacenRed-1][this.oznacenaKol-1] = 0;
            }
            else{
                this.tabla[this.oznacenRed-1][this.oznacenaKol-1] = broj;
            }
        }
    }
    public int[][] getTabla(){
        return this.tabla;
    }
    public ArrayList<ArrayList<Object>> getPraznoPoljeIndex() {
        return this.praznaCelijaIndex;
    }

    public int getOznacenaKol() {
        return oznacenaKol;
    }

    public int getOznacenRed() {
        return oznacenRed;
    }

    public void setOznacenaKol(int k) {
        oznacenaKol = k;
    }

    public void setOznacenRed(int r) {
        oznacenRed = r;
    }


    private boolean proveri(int row, int col){
        if (this.tabla[row][col] > 0){
            for (int i=0; i<9; i++){
                if (this.tabla[i][col] == this.tabla[row][col] && row != i){
                    return false;
                }

                if (this.tabla[row][i] == this.tabla[row][col] && col != i){
                    return false;
                }
            }

            int boxRow = row/3*3;
            int boxCol = col/3*3;

            for (int r=boxRow; r<boxRow + 3; r++){
                for (int c=boxCol; c<boxCol + 3; c++){
                    if (this.tabla[r][c] == this.tabla[row][col] && row != r && col != c){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean resi(Tabla display){
        int row = -1;
        int col = -1;

        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                if (this.tabla[r][c] == 0){
                    row = r;
                    col = c;
                    break;
                }
            }
        }

        if (row == -1 || col == -1){
            return true;
        }

        for (int i=1; i<10; i++){
            this.tabla[row][col] = i;
            display.invalidate();

            if (proveri(row, col)){
                if (resi(display)){
                    return true;
                }
            }

            this.tabla[row][col] = 0;
        }

        return false;

    }
    public void reset(){
        for(int r=0; r<9; r++){
            for(int k=0; k<9; k++){
                tabla[r][k]=0;
            }
        }
        this.praznaCelijaIndex = new ArrayList<>();
    }


}
