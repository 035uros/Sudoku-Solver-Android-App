package com.example.sudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class Tabla extends View {
    private final int bojaTable;
    private final int bojaPopunjeneCelije;
    private final int bojaOznaceneKolRed;

    private final int bojaBrojaKorisnik;
    private final int bojaBrojaBot;
    private final int bojaZaSakrivanje;

    private final Paint bojaTableFarba = new Paint();
    private final Paint bojaPopunjeneCelijeFarba = new Paint();
    private final Paint bojaOznaceneKolRedFarba = new Paint();

    private final Paint bojaBrojaKorisnikFarba = new Paint();
    private final Rect bojaBrojaKorisnikOkvir = new Rect();

    private int dimenzijaCelije;

    private final LogikaResenja resavac = new LogikaResenja();

    private int pomoc;

    public Tabla(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Tabla, 0, 0);

        try{
            pomoc=0;
            bojaTable = a.getInteger(R.styleable.Tabla_bojaTable, 0);
            bojaPopunjeneCelije = a.getInteger(R.styleable.Tabla_bojaPopunjeneCelije, 0);
            bojaOznaceneKolRed = a.getInteger(R.styleable.Tabla_bojaOznaceneKolRed, 0);
            bojaBrojaKorisnik = a.getInteger(R.styleable.Tabla_bojaBrojaKorisnik, 0);
            bojaBrojaBot = a.getInteger(R.styleable.Tabla_bojaBrojaBot, 0);
            bojaZaSakrivanje = a.getInteger(R.styleable.Tabla_bojaZaSakrivanje, 0);

        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimenzija = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight()) - 50;
        dimenzijaCelije = dimenzija/9;

        setMeasuredDimension(dimenzija, dimenzija);

    }
    @Override
    protected void onDraw(Canvas canvas){
        bojaTableFarba.setStyle(Paint.Style.STROKE);
        bojaTableFarba.setStrokeWidth(16);
        bojaTableFarba.setColor(bojaTable);
        bojaTableFarba.setAntiAlias(true);

        bojaPopunjeneCelijeFarba.setStyle(Paint.Style.FILL);
        bojaPopunjeneCelijeFarba.setAntiAlias(true);
        bojaPopunjeneCelijeFarba.setColor(bojaPopunjeneCelije);

        bojaOznaceneKolRedFarba.setStyle(Paint.Style.FILL);
        bojaOznaceneKolRedFarba.setAntiAlias(true);
        bojaOznaceneKolRedFarba.setColor(bojaOznaceneKolRed);

        bojaBrojaKorisnikFarba.setStyle(Paint.Style.FILL);
        bojaBrojaKorisnikFarba.setAntiAlias(true);
        bojaBrojaKorisnikFarba.setColor(bojaBrojaKorisnik);

        bojaCelije(canvas, resavac.getOznacenRed(), resavac.getOznacenaKol());
        canvas.drawRect(0,0,getWidth(), getHeight(), bojaTableFarba);
        crtanjeTable(canvas);

        crtajBrojeve(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event){
        boolean isValid;

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            resavac.setOznacenaKol((int) Math.ceil(x/dimenzijaCelije));
            resavac.setOznacenRed((int) Math.ceil(y/dimenzijaCelije));
            isValid = true;
        }
        else{
            isValid=false;
        }


        return isValid;
    }

    private void crtajBrojeve(Canvas canvas){

        bojaBrojaKorisnikFarba.setColor(bojaBrojaKorisnik);
        bojaBrojaKorisnikFarba.setTextSize(dimenzijaCelije-50);

        if(getPomoc() == 0){

            for(int r=0;r<9;r++){
                for(int k=0;k<9;k++){
                    if(resavac.getTabla()[r][k] != 0){
                        String tekst;
                        tekst = Integer.toString(resavac.getTabla()[r][k]);
                        float width, height;

                        bojaBrojaKorisnikFarba.getTextBounds(tekst, 0, tekst.length(), bojaBrojaKorisnikOkvir);
                        width = bojaBrojaKorisnikFarba.measureText(tekst) ;
                        height = bojaBrojaKorisnikOkvir.height();

                        canvas.drawText(tekst, (k*dimenzijaCelije) + ((dimenzijaCelije - width)/2),
                                (r*dimenzijaCelije+dimenzijaCelije) - ((dimenzijaCelije - height)/2),
                                bojaBrojaKorisnikFarba);
                    }
                }
            }

            bojaBrojaKorisnikFarba.setColor(bojaBrojaBot);

            for(ArrayList<Object> broj : resavac.getPraznoPoljeIndex()){
                int r = (int)broj.get(0);
                int k = (int)broj.get(1);

                String tekst = Integer.toString(resavac.getTabla()[r][k]);
                float width, height;

                bojaBrojaKorisnikFarba.getTextBounds(tekst, 0, tekst.length(), bojaBrojaKorisnikOkvir);
                width = bojaBrojaKorisnikFarba.measureText(tekst);
                height = bojaBrojaKorisnikOkvir.height();

                canvas.drawText(tekst, (k*dimenzijaCelije) + ((dimenzijaCelije - width)/2),
                        (r*dimenzijaCelije+dimenzijaCelije) - ((dimenzijaCelije - height)/2),
                        bojaBrojaKorisnikFarba);
            }
        }
        else{
            for(int r=0;r<9;r++){
                for(int k=0;k<9;k++){
                    if(resavac.getTabla()[r][k] != 0){
                        String tekst;
                        tekst = Integer.toString(resavac.getTabla()[r][k]);
                        float width, height;

                        bojaBrojaKorisnikFarba.getTextBounds(tekst, 0, tekst.length(), bojaBrojaKorisnikOkvir);
                        width = bojaBrojaKorisnikFarba.measureText(tekst) ;
                        height = bojaBrojaKorisnikOkvir.height();

                        //canvas.drawText(tekst,(k*dimenzijaCelije) + ((dimenzijaCelije-width)/2), (k*dimenzijaCelije+ dimenzijaCelije) - ((dimenzijaCelije - height)/2), bojaBrojaKorisnikFarba );
                        canvas.drawText(tekst, (k*dimenzijaCelije) + ((dimenzijaCelije - width)/2),
                                (r*dimenzijaCelije+dimenzijaCelije) - ((dimenzijaCelije - height)/2),
                                bojaBrojaKorisnikFarba);
                    }
                }
            }

            bojaBrojaKorisnikFarba.setColor(bojaBrojaBot);

            for(ArrayList<Object> broj : resavac.getPraznoPoljeIndex()){
                int r = (int)broj.get(0);
                int k = (int)broj.get(1);
                String tekst;

                if(r == resavac.getOznacenRed()-1 && k == resavac.getOznacenaKol()-1){
                    bojaBrojaKorisnikFarba.setColor(bojaBrojaBot);
                }
                else{
                    bojaBrojaKorisnikFarba.setColor(bojaZaSakrivanje);
                }
                tekst = Integer.toString(resavac.getTabla()[r][k]);
                float width, height;

                bojaBrojaKorisnikFarba.getTextBounds(tekst, 0, tekst.length(), bojaBrojaKorisnikOkvir);
                width = bojaBrojaKorisnikFarba.measureText(tekst);
                height = bojaBrojaKorisnikOkvir.height();

                canvas.drawText(tekst, (k*dimenzijaCelije) + ((dimenzijaCelije - width)/2),
                        (r*dimenzijaCelije+dimenzijaCelije) - ((dimenzijaCelije - height)/2),
                        bojaBrojaKorisnikFarba);
            }
        }



            /*

        */

    }

    private void bojaCelije(Canvas canvas, int red, int kol){
        if(resavac.getOznacenaKol() != -1 && resavac.getOznacenRed() != -1){
            canvas.drawRect((kol-1)*dimenzijaCelije, 0, kol*dimenzijaCelije, dimenzijaCelije*9, bojaOznaceneKolRedFarba);
            canvas.drawRect(0, (red-1)*dimenzijaCelije, dimenzijaCelije*9, red*dimenzijaCelije, bojaOznaceneKolRedFarba);
            canvas.drawRect((kol-1)*dimenzijaCelije, (red-1)*dimenzijaCelije, kol*dimenzijaCelije, red*dimenzijaCelije, bojaPopunjeneCelijeFarba);
        }

        invalidate();

    }

    private void crtanjeTankeLinije(){
        bojaTableFarba.setStyle(Paint.Style.STROKE);
        bojaTableFarba.setStrokeWidth(4);
        bojaTableFarba.setColor(bojaTable);
    }

    private void crtanjeDebeleLinije(){

        bojaTableFarba.setStyle(Paint.Style.STROKE);
        bojaTableFarba.setStrokeWidth(10);
        bojaTableFarba.setColor(bojaTable);
    }

    private void crtanjeTable(Canvas canvas){

        for(int c=0; c<10; c++){
            if(c%3==0){
                crtanjeDebeleLinije();
            }
            else{
                crtanjeTankeLinije();
            }
            canvas.drawLine(dimenzijaCelije*c,0,dimenzijaCelije*c, getWidth(), bojaTableFarba);
        }
        for(int r=0; r<10; r++){
            if(r%3==0){
                crtanjeDebeleLinije();
            }
            else{
                crtanjeTankeLinije();
            }
            canvas.drawLine(0,dimenzijaCelije*r, getWidth(),dimenzijaCelije*r, bojaTableFarba);

        }
    }

    public LogikaResenja getResavac() {
        return this.resavac;
    }

    public void setPomoc(int broj){
        this.pomoc = broj;
    }

    public int getPomoc(){
        return this.pomoc;
    }


}
