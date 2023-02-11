package com.example.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private Tabla igra;
    private LogikaResenja igraIgrac;

    private Button resiBTN;
    private Button pomocBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        igra = findViewById(R.id.sudokuTabla);
        igraIgrac = igra.getResavac();

        resiBTN = findViewById(R.id.button10);
        pomocBTN = findViewById(R.id.button11);

        if(OpenCVLoader.initDebug()) Log.d("TAG", "Uspeh");
    }

    public void DugmeJedan(View view){
        igraIgrac.setPozicijaBroja(1);
        igra.invalidate();
    }
    public void DugmeDva(View view){
        igraIgrac.setPozicijaBroja(2);
        igra.invalidate();
    }
    public void DugmeTri(View view){
        igraIgrac.setPozicijaBroja(3);
        igra.invalidate();
    }
    public void DugmeCetiri(View view){
        igraIgrac.setPozicijaBroja(4);
        igra.invalidate();
    }
    public void DugmePet(View view){
        igraIgrac.setPozicijaBroja(5);
        igra.invalidate();
    }
    public void DugmeSest(View view){
        igraIgrac.setPozicijaBroja(6);
        igra.invalidate();
    }
    public void DugmeSedam(View view){
        igraIgrac.setPozicijaBroja(7);
        igra.invalidate();
    }
    public void DugmeOsam(View view){
        igraIgrac.setPozicijaBroja(8);
        igra.invalidate();
    }
    public void DugmeDevet(View view){
        igraIgrac.setPozicijaBroja(9);
        igra.invalidate();
    }

    public void DugmeResi(View view){
        if(resiBTN.getText().toString().equals(getString(R.string.resi))){

            igra.setPomoc(0);
            resiBTN.setText(getString(R.string.cisti));

            igraIgrac.getPraznaCelijaIndex();

            sudokuThread thread = new sudokuThread();
            new Thread(thread).start();
           // igra.invalidate();
        }
        else{
            resiBTN.setText(getString(R.string.resi));
            pomocBTN.setText(getString(R.string.pomoc));
            igraIgrac.reset();
            igra.invalidate();
        }
    }

    public void DugmePomoc(View view){

        if(pomocBTN.getText().toString().equals(getString(R.string.pomoc))){
            pomocBTN.setText(getString(R.string.cisti));
            igra.setPomoc(1);
            igraIgrac.getPraznaCelijaIndex();

            sudokuThread thread = new sudokuThread();
            new Thread(thread).start();

            //igra.invalidate();
            }else{
            pomocBTN.setText(getString(R.string.pomoc));
            resiBTN.setText(getString(R.string.resi));
            igraIgrac.reset();
            igra.invalidate();
        }
    }

    class sudokuThread implements Runnable{
        @Override
        public void run(){
            igraIgrac.resi(igra);
        }
    }


}