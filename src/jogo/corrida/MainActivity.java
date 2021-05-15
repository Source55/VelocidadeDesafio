package jogo.corrida;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import android.graphics.Matrix;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.graphics.Point;
import android.content.Intent;
import android.widget.Toast;
import android.graphics.Typeface;

public class MainActivity extends Activity{
	ViewDirigindo vd;
	Bitmap prev,prox,logo,botaocw,botaoccw,carro[]=new Bitmap[36],pista[]=new Bitmap[21],numeros[]=new Bitmap[6],estrela,semestrela,pistamini[]=new Bitmap[21];
	float maxx=800,maxy=444,px,py,vx,vy,tamanhotela,tamanhominiaturas;
	boolean apertandocw=false,apertandoccw=false,pisoufora;
	int angulo3,tempo=-5,fase=0,fasemax=0,qtdfases=24,japassou[][]=new int[100][2],tamjapassou;
	int minestrela[]={1,1,2,1,1,6,3,3,2,3,2,3,2,3,3,4,6,3,6,3,3,4,3,4};
	int estrelas[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	int vmax[]={8,9,10,10,11,11,11,12,12,12,13,13,13,14,14,14,14,15,15,15,15,16,16,16};
	int angulosiniciais[]={0,56,28,82,0,56,56,28,56,56,28,28,28,28,0,56,28,56,82,56,82,56,0,0};
	int tempodafase[]={740,540,800,710,980,980,760,1030,1020,1300,1080,1700,1080,620,1570,1180,1410,1900,1460,840,2000,1580,2160,1560};
	int ciniciais[][]={{6,2},{0,2},{4,2},{0,4},{2,0},{4,0},{0,4},{2,0},{0,4},{0,6},{0,0},{6,8},{0,0},{4,0},{4,10},{2,0},{12,0},{0,8},{4,4},{4,4},{0,10},{0,0},{4,14},{12,0}};
	int cfinais[][]={{6,0},{0,0},{2,2},{3,2},{4,0},{2,0},{0,6},{0,0},{0,6},{0,4},{2,0},{6,6},{2,0},{2,0},{6,10},{0,0},{10,0},{0,10},{6,4},{4,6},{2,10},{0,2},{6,14},{4,4}};
	int qtascasasprecisa[]={15,12,21,18,28,28,21,32,32,45,37,61,37,21,60,44,52,75,55,34,79,66,92,67};
	int tracado[][][]={
		{{12,4,6},{2,0,1},{2,0,1},{2,0,1},{2,0,1},{2,0,1},{19,0,16}},
		{{14,0,13},{1,0,2},{1,0,2},{1,0,2},{1,0,2},{9,4,7}},
		{{11,3,3,3,5},{1,0,0,0,2},{1,0,20,4,7},{1,0,0,0,0},{1,0,15,3,5},{1,0,0,0,2},{9,4,4,4,7}},
		{{12,4,4,4,17},{2,0,0,0,0},{2,0,0,0,0},{2,0,20,4,6},{2,0,0,0,1},{2,0,0,0,1},{10,3,3,3,8}},
		{{11,3,5,0,11,3,5},{1,0,2,0,1,0,2},{16,0,10,3,8,0,2},{0,0,0,0,0,0,2},{14,0,12,4,6,0,2},{1,0,2,0,1,0,2},{9,4,7,0,9,4,7}},
		{{12,4,6,0,12,4,6},{2,0,1,0,2,0,1},{19,0,9,4,7,0,1},{0,0,0,0,0,0,1},{13,0,11,3,5,0,1},{2,0,1,0,2,0,1},{10,3,8,0,10,3,8}},
		{{0,0,0,0,13,0,14},{0,0,0,0,2,0,1},{0,0,12,4,7,0,1},{0,0,2,0,0,0,1},{12,4,7,0,11,3,8},{2,0,0,0,1,0,0},{10,3,3,3,8,0,0}},
		{{20,4,4,4,4,4,4,6},{0,0,0,0,0,0,0,1},{15,3,3,3,3,5,0,1},{0,0,0,0,0,2,0,1},{12,4,4,4,4,7,0,1},{2,0,0,0,0,0,0,1},{10,3,3,3,3,3,3,8}},
		{{12,4,6,0,13,0,14,0,12,4,6},{2,0,1,0,2,0,1,0,2,0,1},{2,0,9,4,7,0,9,4,7,0,1},{2,0,0,0,0,0,0,0,0,0,1},{10,3,3,3,3,3,3,3,3,3,8}},
		{{11,3,5,0,14,0,13,0,11,3,5},{1,0,2,0,1,0,2,0,1,0,2},{1,0,10,3,8,0,10,3,8,0,2},{1,0,0,0,0,0,0,0,0,0,2},{1,0,12,4,6,0,12,4,6,0,2},{1,0,2,0,1,0,2,0,1,0,2},{9,4,7,0,9,4,7,0,9,4,7}},
		{{15,3,5,0,11,3,3,3,5},{0,0,2,0,1,0,0,0,2},{14,0,2,0,9,4,6,0,2},{1,0,2,0,0,0,1,0,2},{1,0,10,3,3,3,8,0,2},{1,0,0,0,0,0,0,0,2},{9,4,4,4,4,4,4,4,7}},
		{{12,4,4,4,6,0,12,4,6,0,12,4,4,4,6},{2,0,0,0,1,0,2,0,1,0,2,0,0,0,1},{2,0,11,3,8,0,2,0,1,0,10,3,5,0,1},{2,0,1,0,0,0,2,0,1,0,0,0,2,0,1},{2,0,9,4,4,4,7,0,9,4,4,4,7,0,1},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,1},{10,3,3,3,3,3,18,0,15,3,3,3,3,3,8}},
		{{15,3,5,0,11,3,5},{0,0,2,0,1,0,2},{14,0,2,0,1,0,2},{1,0,2,0,1,0,2},{1,0,2,0,1,0,2},{1,0,2,0,1,0,2},{1,0,10,3,8,0,2},{1,0,0,0,0,0,2},{9,4,4,4,4,4,7}},
		{{0,0,12,4,6,0,0},{0,0,2,0,1,0,0},{20,4,7,0,9,4,6},{0,0,0,0,0,0,1},{15,3,5,0,11,3,8},{0,0,2,0,1,0,0},{0,0,10,3,8,0,0}},
		{{0,0,12,4,4,4,4,4,6,0,0},{0,0,2,0,0,0,0,0,1,0,0},{12,4,7,0,11,3,5,0,9,4,6},{2,0,0,0,1,0,2,0,0,0,1},{10,3,3,3,8,0,10,3,5,0,16},{0,0,0,0,0,0,0,0,2,0,0},{12,4,4,4,6,0,12,4,7,0,14},{2,0,0,0,1,0,2,0,0,0,1},{10,3,5,0,9,4,7,0,11,3,8},{0,0,2,0,0,0,0,0,1,0,0},{0,0,10,3,3,3,3,3,8,0,0}},
		{{20,4,4,4,4,4,4,4,4,4,4,4,4,4,6},{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},{13,0,11,3,5,0,11,3,5,0,11,3,5,0,1},{2,0,1,0,2,0,1,0,2,0,1,0,2,0,1},{10,3,8,0,10,3,8,0,10,3,8,0,10,3,8}},
		{{12,4,4,4,4,4,6},{2,0,0,0,0,0,1},{2,0,11,3,5,0,1},{2,0,1,0,2,0,1},{2,0,1,0,10,3,8},{2,0,1,0,0,0,0},{2,0,9,4,4,4,6},{2,0,0,0,0,0,1},{10,3,3,3,5,0,1},{0,0,0,0,2,0,1},{20,4,4,4,7,0,1},{0,0,0,0,0,0,1},{15,3,3,3,3,3,8}},
		{{12,4,4,4,4,4,6,0,13,0,14},{2,0,0,0,0,0,1,0,2,0,1},{2,0,11,3,5,0,1,0,2,0,1},{2,0,1,0,2,0,1,0,2,0,1},{2,0,1,0,2,0,1,0,2,0,1},{2,0,1,0,2,0,1,0,2,0,1},{2,0,1,0,2,0,1,0,2,0,1},{2,0,1,0,2,0,1,0,2,0,1},{2,0,1,0,2,0,1,0,2,0,1},{2,0,1,0,2,0,9,4,7,0,1},{2,0,1,0,2,0,0,0,0,0,1},{10,3,8,0,10,3,3,3,3,3,8}},
		{{12,4,4,4,4,4,4,4,6},{2,0,0,0,0,0,0,0,1},{2,0,11,3,3,3,5,0,1},{2,0,1,0,0,0,2,0,1},{2,0,9,4,17,0,2,0,1},{2,0,0,0,0,0,2,0,1},{10,3,3,3,18,0,2,0,1},{0,0,0,0,0,0,2,0,1},{12,4,4,4,4,4,7,0,1},{2,0,0,0,0,0,0,0,1},{10,3,3,3,3,3,3,3,8}},
		{{0,0,0,0,11,3,5,0,0,0,0},{0,0,0,0,1,0,2,0,0,0,0},{0,0,11,3,8,0,10,3,5,0,0},{0,0,1,0,0,0,0,0,2,0,0},{11,3,8,0,13,0,14,0,10,3,5},{1,0,0,0,2,0,1,0,0,0,2},{9,4,4,4,7,0,9,4,4,4,7}},
		{{12,4,4,4,4,4,4,4,4,4,17},{2,0,0,0,0,0,0,0,0,0,0},{2,0,11,3,3,3,3,3,3,3,18},{2,0,1,0,0,0,0,0,0,0,0},{2,0,1,0,12,4,4,4,4,4,6},{2,0,1,0,2,0,0,0,0,0,1},{2,0,1,0,2,0,11,3,5,0,1},{2,0,1,0,2,0,1,0,2,0,1},{2,0,1,0,10,3,8,0,2,0,1},{2,0,1,0,0,0,0,0,2,0,1},{2,0,9,4,4,4,4,4,7,0,1},{2,0,0,0,0,0,0,0,0,0,1},{10,3,3,3,3,3,3,3,3,3,8}},
		{{13,0,14,0,0,0,0,0,0,0,0,0,0,0},{2,0,1,0,0,0,0,0,0,0,0,0,0,0},{2,0,1,0,0,12,4,4,4,4,4,4,4,6},{2,0,1,0,0,2,0,0,0,0,0,0,0,1},{2,0,9,4,4,7,0,0,11,3,3,3,3,8},{2,0,0,0,0,0,0,0,1,0,0,0,0,0},{10,3,3,3,5,0,0,0,9,4,6,0,0,0},{0,0,0,0,2,0,0,0,0,0,1,0,0,0},{12,4,4,4,7,0,11,3,5,0,1,0,0,0},{2,0,0,0,0,0,1,0,2,0,1,0,0,0},{10,3,3,3,3,3,8,0,2,0,1,0,0,0},{0,0,0,0,0,0,0,0,2,0,1,0,0,0},{0,0,0,0,0,0,0,0,10,3,8,0,0,0}},
		{{12,4,4,4,6,0,12,4,6,0,12,4,4,4,6},{2,0,0,0,1,0,2,0,1,0,2,0,0,0,1},{2,0,11,3,8,0,2,0,1,0,10,3,5,0,1},{2,0,1,0,0,0,2,0,1,0,0,0,2,0,1},{2,0,9,4,4,4,7,0,9,4,4,4,7,0,16},{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0},{2,0,11,3,3,3,5,0,11,3,3,3,5,0,14},{2,0,1,0,0,0,2,0,1,0,0,0,2,0,1},{2,0,9,4,6,0,2,0,1,0,12,4,7,0,1},{2,0,0,0,1,0,2,0,1,0,2,0,0,0,1},{10,3,3,3,8,0,10,3,8,0,10,3,3,3,8}},
		{{12,4,6,0,12,4,4,4,6},{2,0,1,0,2,0,0,0,1},{2,0,9,4,7,0,11,3,8},{2,0,0,0,0,0,1,0,0},{10,3,5,0,14,0,9,4,6},{0,0,2,0,1,0,0,0,1},{12,4,7,0,1,0,11,3,8},{2,0,0,0,1,0,1,0,0},{10,3,3,3,8,0,9,4,6},{0,0,0,0,0,0,0,0,1},{11,3,5,0,11,3,5,0,1},{1,0,2,0,1,0,2,0,1},{16,0,10,3,8,0,10,3,8}}
	};
	final Handler handler=new Handler();
	Timer timer=new Timer();
	SharedPreferences valorp;
	final Runnable runnable=new Runnable(){public void run(){vd.invalidate();}};
	@Override public void onBackPressed(){
		if(tempo==-1)super.onBackPressed();
		else tempo=-1;
	}
	@Override public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		vd=new ViewDirigindo(this);
		valorp=PreferenceManager.getDefaultSharedPreferences(this);
		for(int e=0;e<qtdfases;e++)if(valorp.contains("fase"+String.valueOf(e)))estrelas[e]=valorp.getInt("fase"+String.valueOf(e),0);
		fasemax=0;
		int contaestrela=0;
		for(int e=0;e<qtdfases;e++)contaestrela+=estrelas[e];
		for(int e=0;contaestrela>=0&&e<qtdfases;e++){
			fasemax=e;
			contaestrela-=minestrela[e];
		}
		fase=-1;
		for(int f=0;fase==-1&&f<3;f++)for(int e=0;fase==-1&&e<qtdfases;e++)if(estrelas[e]==f)fase=e;
		if(fase==-1)fase=0;
		Point size=new Point();
		getWindowManager().getDefaultDisplay().getSize(size);
		maxx=Math.max(size.x,size.y);//800
		maxy=Math.min(size.x,size.y);//444
		tamanhotela=maxy/600;
		Resources r=getResources();
		logo=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.logo),(int)Math.ceil(645*tamanhotela),(int)Math.ceil(450*tamanhotela),true);
		botaoccw=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.ccw),(int)Math.ceil(120*tamanhotela),(int)Math.ceil(120*tamanhotela),true);
		botaocw=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.cw),(int)Math.ceil(120*tamanhotela),(int)Math.ceil(120*tamanhotela),true);
		prev=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.prev),(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),true);
		prox=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.prox),(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),true);
		carro[0]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.carro),(int)Math.ceil(72*tamanhotela),(int)Math.ceil(72*tamanhotela),true);
		numeros[1]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.n1),(int)Math.ceil(240*tamanhotela),(int)Math.ceil(240*tamanhotela),true);
		numeros[2]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.n2),(int)Math.ceil(240*tamanhotela),(int)Math.ceil(240*tamanhotela),true);
		numeros[3]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.n3),(int)Math.ceil(240*tamanhotela),(int)Math.ceil(240*tamanhotela),true);
		numeros[4]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.n4),(int)Math.ceil(240*tamanhotela),(int)Math.ceil(240*tamanhotela),true);
		numeros[5]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.n5),(int)Math.ceil(240*tamanhotela),(int)Math.ceil(240*tamanhotela),true);
		Matrix matrix;
		for(int x=1;x<36;x++){
			matrix=new Matrix();
			matrix.postRotate(x*10);
			carro[x]=Bitmap.createBitmap(carro[0],0,0,(int)Math.ceil(72*tamanhotela),(int)Math.ceil(72*tamanhotela),matrix,true);
		}
		pista[1]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.linha),(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),true);
		matrix=new Matrix();
		matrix.postRotate(180);
		pista[2]=Bitmap.createBitmap(pista[1],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(90);
		pista[3]=Bitmap.createBitmap(pista[1],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(270);
		pista[4]=Bitmap.createBitmap(pista[1],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		pista[5]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.curvahorario),(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),true);
		pista[6]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.curvaantihorario),(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),true);
		matrix=new Matrix();
		matrix.postRotate(90);
		pista[7]=Bitmap.createBitmap(pista[5],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(90);
		pista[8]=Bitmap.createBitmap(pista[6],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(180);
		pista[9]=Bitmap.createBitmap(pista[5],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(180);
		pista[10]=Bitmap.createBitmap(pista[6],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(270);
		pista[11]=Bitmap.createBitmap(pista[5],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(270);
		pista[12]=Bitmap.createBitmap(pista[6],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		pista[13]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.inicio),(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),true);
		pista[14]=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r,R.drawable.chegada),(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),true);
		matrix=new Matrix();
		matrix.postRotate(270);
		pista[15]=Bitmap.createBitmap(pista[13],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(180);
		pista[16]=Bitmap.createBitmap(pista[13],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(90);
		pista[17]=Bitmap.createBitmap(pista[13],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(90);
		pista[18]=Bitmap.createBitmap(pista[14],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(180);
		pista[19]=Bitmap.createBitmap(pista[14],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		matrix=new Matrix();
		matrix.postRotate(270);
		pista[20]=Bitmap.createBitmap(pista[14],0,0,(int)Math.ceil(200*tamanhotela),(int)Math.ceil(200*tamanhotela),matrix,true);
		estrela=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.star),(int)(50*tamanhotela),(int)(50*tamanhotela),true);
		semestrela=Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.notstar),(int)(50*tamanhotela),(int)(50*tamanhotela),true);
		tamanhominiaturas=(maxx-(250*tamanhotela))/22<maxy/17?(maxx-(250*tamanhotela))/22:maxy/17;
		for(int x=1;x<21;x++)pistamini[x]=Bitmap.createScaledBitmap(pista[x],(int)tamanhominiaturas,(int)tamanhominiaturas,true);
		setContentView(vd);
		timer.schedule(new TimerTask(){
			int[] fatorx={0,-1,-2,-3,-4,-5,-6,-7,-8,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8,9,8,7,6,5,4,3,2,1};
			int[] fatory={9,8,7,6,5,4,3,2,1,0,-1,-2,-3,-4,-5,-6,-7,-8,-9,-8,-7,-6,-5,-4,-3,-2,-1,0,1,2,3,4,5,6,7,8};
			public void run(){
				if(tempo>=0){
					tempo++;
					if(apertandoccw){
						angulo3--;
						if(angulo3==-3)angulo3=105;
					}
					if(apertandocw){
						angulo3++;
						if(angulo3==108)angulo3=0;
					}
					int emcimade[]={-1,-1};
					if(-py/200/tamanhotela>0&&-px/200/tamanhotela>0&&-py/200/tamanhotela<tracado[fase].length&&-px/200/tamanhotela<tracado[fase][0].length&&tracado[fase][(int)(-py/200/tamanhotela)][(int)(-px/200/tamanhotela)]!=0){
						emcimade[0]=(int)(-py/200/tamanhotela);
						emcimade[1]=(int)(-px/200/tamanhotela);
					}
					boolean japassounessepedaco=false;
					for(int x=0;x<=tamjapassou;x++)
						if(japassou[x][0]==emcimade[0]&&japassou[x][1]==emcimade[1])
							japassounessepedaco=true;
					if(emcimade[0]!=-1&&!japassounessepedaco){
						tamjapassou++;
						japassou[tamjapassou][0]=emcimade[0];
						japassou[tamjapassou][1]=emcimade[1];
					}
					if(emcimade[0]==cfinais[fase][0]&&emcimade[1]==cfinais[fase][1]){
						boolean mostrafim=false;
						if(tamjapassou>=qtascasasprecisa[fase]){
							boolean mudafase=false;
							int qtdestrela=1;
							if(!pisoufora||tempo<tempodafase[fase])qtdestrela=2;
							if(!pisoufora&&tempo<tempodafase[fase])qtdestrela=3;
							if(estrelas[fase]<=qtdestrela){
								mudafase=estrelas[fase]<qtdestrela;
								Editor editor=valorp.edit();
								estrelas[fase]=qtdestrela;
								for(int e=0;e<qtdfases;e++)editor.putInt("fase"+String.valueOf(e),estrelas[e]);
								editor.commit();
								int contaestrela=0;
								for(int e=0;e<qtdfases;e++)contaestrela+=estrelas[e];
								for(int e=0;contaestrela>=0;e++){
									if(e==qtdfases){
										mostrafim=true;
										break;
									}
									fasemax=e;
									contaestrela-=minestrela[e];
								}
							}
							if(mostrafim){
								tempo=-3;
								fase=0;
							}
							else if(mudafase)tempo=-4;
							else tempo=-1;
						}
						else tempo=-2;
					}
					else{
						vx=fatorx[angulo3/3];
						vy=fatory[angulo3/3];
						if(emcimade[0]==-1){
							pisoufora=true;
							if(vx>tamanhotela*vmax[fase]/5)vx=tamanhotela*vmax[fase]/5;
							if(vy>tamanhotela*vmax[fase]/5)vy=tamanhotela*vmax[fase]/5;
							if(vx<tamanhotela*-vmax[fase]/5)vx=tamanhotela*-vmax[fase]/5;
							if(vy<tamanhotela*-vmax[fase]/5)vy=tamanhotela*-vmax[fase]/5;
						}
						if(vx>vmax[fase])vx=vmax[fase];
						if(vy>vmax[fase])vy=vmax[fase];
						if(vx<-vmax[fase])vx=-vmax[fase];
						if(vy<-vmax[fase])vy=-vmax[fase];
						if(tempo>100)px+=tamanhotela*vx*vmax[fase]*0.0642f;
						if(tempo>100)py+=tamanhotela*vy*vmax[fase]*0.0642f;
						if(emcimade[0]==-1&&tempo>tempodafase[fase])tempo=-2;
					}
				}
				handler.post(runnable);
			}
		},0,10);
	}
	class ViewDirigindo extends View{
		Context ct;
		public ViewDirigindo(Context c){super(c);ct=c;}
		@Override public boolean onTouchEvent(MotionEvent me){
			float x=me.getX(),y=me.getY();
			if(tempo>0){
				apertandocw=false;
				apertandoccw=false;
				if(x<(330*tamanhotela)&&y>maxy-(380*tamanhotela))apertandoccw=true;
				if(x>maxx-(330*tamanhotela)&&y>maxy-(380*tamanhotela))apertandocw=true;
				if(me.getAction()==MotionEvent.ACTION_UP){
					apertandocw=false;
					apertandoccw=false;
				}
			}
			if(tempo==-1&&me.getAction()==MotionEvent.ACTION_DOWN){
				if(x<250*tamanhotela)fase=(fase+qtdfases-1)%qtdfases;
				else if(x>maxx-(250*tamanhotela))fase=(fase+1)%qtdfases;
				else if(fase>fasemax)Toast.makeText(MainActivity.this,R.string.faltaestrela,Toast.LENGTH_SHORT).show();
				else{
					tempo=0;
					angulo3=angulosiniciais[fase];
					px=((-ciniciais[fase][1]*200)-100)*tamanhotela;
					py=((-ciniciais[fase][0]*200)-100)*tamanhotela;
					tamjapassou=0;
					japassou[tamjapassou][1]=ciniciais[fase][0];
					japassou[tamjapassou][0]=ciniciais[fase][1];
					vx=0;
					vy=0;
					apertandocw=false;
					apertandoccw=false;
					pisoufora=false;
				}
			}
			if((tempo==-4||tempo==-3)&&me.getAction()==MotionEvent.ACTION_DOWN){
				fase=-1;
				for(int f=0;fase==-1&&f<3;f++)for(int e=0;fase==-1&&e<qtdfases;e++)if(estrelas[e]==f)fase=e;
				if(fase==-1)fase=0;
			}
			if(tempo<-1&&me.getAction()==MotionEvent.ACTION_DOWN)tempo=-1;
			return true;
		}
		@Override protected void onDraw(Canvas c){
			super.onDraw(c);
			Paint p=new Paint();
			Typeface typeface=Typeface.create(Typeface.SERIF,Typeface.BOLD);
			p.setTypeface(typeface);
			if(tempo==-5){
				p.setColor(new Color().rgb(0,65,120));
				c.drawPaint(p);
				c.drawBitmap(logo,(maxx-logo.getWidth())/2,(maxy-logo.getHeight())/2,p);
			}
			else if(tempo==-4){
				c.drawBitmap(estrelas[fase]>0?estrela:semestrela,(float)(maxy*0.04),(float)(maxy*0.04),p);
				c.drawBitmap(estrelas[fase]>1?estrela:semestrela,(float)(maxy*0.04)+(50*tamanhotela),(float)(maxy*0.04),p);
				c.drawBitmap(estrelas[fase]>2?estrela:semestrela,(float)(maxy*0.04)+(100*tamanhotela),(float)(maxy*0.04),p);
				for(int x=0;x<8;x++)c.drawBitmap(pista[3],(x*200*tamanhotela)-(100*tamanhotela),(float)(maxy*0.68),p);
				p.setTextAlign(Paint.Align.CENTER);
				p.setTextSize((float)(maxy*0.160));
				p.setColor(new Color().rgb(0,180,65));
				c.drawText(getString(R.string.passoudefase),maxx/2,maxy/2,p);
			}
			else if(tempo==-3){
				p.setTextAlign(Paint.Align.CENTER);
				p.setColor(new Color().rgb(15,120,215));
				p.setTextSize((float)(maxy*0.18));
				c.drawText(getString(R.string.fimdojogo1),maxx/2,(float)(maxy*0.4),p);
				p.setColor(new Color().rgb(230,230,230));
				p.setTextSize((float)(maxy*0.12));
				c.drawText(getString(R.string.fimdojogo2),maxx/2,(float)(maxy*0.64),p);
			}
			else if(tempo==-2){
				p.setColor(new Color().rgb(0,180,65));
				p.setTextSize((float)(maxy*0.1));
				c.drawText(getString(R.string.tentedenovo1),(float)(maxy*0.1),(float)(maxy*0.2),p);
				p.setColor(new Color().rgb(215,215,215));
				p.setTextSize((float)(maxy*0.047));
				c.drawText(getString(R.string.tentedenovo2),(float)(maxy*0.1),(float)(maxy*0.36),p);
				c.drawText(getString(R.string.tentedenovo3),(float)(maxy*0.1),(float)(maxy*0.44),p);
				for(int x=0;x<8;x++)c.drawBitmap(pista[3],(x*200*tamanhotela)-(100*tamanhotela),(float)(maxy*0.54),p);
			}
			else if(tempo==-1){
				for(int y=0;y<tracado[fase].length;y++)
					for(int x=0;x<tracado[fase][y].length;x++)
						if(tracado[fase][y][x]!=0)
							c.drawBitmap(pistamini[tracado[fase][y][x]],((maxx/2)-(tracado[fase][y].length*tamanhominiaturas/2))+(x*tamanhominiaturas),((maxy/2)-(tracado[fase].length*tamanhominiaturas/2))+(y*tamanhominiaturas),p);
				c.drawBitmap(estrelas[fase]>0?estrela:semestrela,(maxx/2)-(75*tamanhotela),(float)(maxy*0.02),p);
				c.drawBitmap(estrelas[fase]>1?estrela:semestrela,(maxx/2)-(25*tamanhotela),(float)(maxy*0.02),p);
				c.drawBitmap(estrelas[fase]>2?estrela:semestrela,(maxx/2)+(25*tamanhotela),(float)(maxy*0.02),p);
				c.drawBitmap(prev,40*tamanhotela,((maxy-300)/2*tamanhotela),p);
				c.drawBitmap(prox,maxx-(240*tamanhotela),((maxy-300)/2*tamanhotela),p);
				p.setColor(new Color().rgb(215,215,215));
				p.setTextSize((float)((maxx-(200*tamanhotela))*0.06));
				p.setTextAlign(Paint.Align.CENTER);
				c.drawText(String.valueOf(vmax[fase]*5)+" Km/h",maxx/2,(float)(maxy*0.972),p);
			}
			else if(tempo>0){
				p.setColor(new Color().rgb(10,45,90));
				c.drawPaint(p);
				for(int y=0;y<tracado[fase].length;y++)
					for(int x=0;x<tracado[fase][y].length;x++)
						if(tracado[fase][y][x]!=0)
							c.drawBitmap(pista[tracado[fase][y][x]],px+(x*200*tamanhotela)+(maxx/2),py+(y*200*tamanhotela)+(maxy/2),p);
				c.drawBitmap(carro[angulo3/3],(maxx-carro[angulo3/3].getWidth())/2,(maxy-carro[angulo3/3].getHeight())/2,p);
				if(tempo>100&&tempodafase[fase]-tempo>90&&tempodafase[fase]-tempo<510&&(tempodafase[fase]-tempo)%100<10)c.drawBitmap(numeros[(tempodafase[fase]-tempo)/100],(maxx-numeros[(tempodafase[fase]-tempo)/100].getHeight())/2,(maxy-numeros[(tempodafase[fase]-tempo)/100].getHeight())/2,p);
				c.drawBitmap(estrela,(float)(maxy*0.04),(float)(maxy*0.04),p);
				c.drawBitmap(!pisoufora||tempodafase[fase]>tempo?estrela:semestrela,(float)(maxy*0.04)+(50*tamanhotela),(float)(maxy*0.04),p);
				c.drawBitmap(!pisoufora&&tempodafase[fase]>tempo?estrela:semestrela,(float)(maxy*0.04)+(100*tamanhotela),(float)(maxy*0.04),p);
				p.setColor(new Color().rgb(110,170,130));
				if(apertandoccw)c.drawCircle((80*tamanhotela),maxy-(80*tamanhotela),(58*tamanhotela),p);
				c.drawBitmap(botaoccw,(20*tamanhotela),maxy-(140*tamanhotela),p);
				if(apertandocw)c.drawCircle(maxx-(80*tamanhotela),maxy-(80*tamanhotela),(58*tamanhotela),p);
				c.drawBitmap(botaocw,maxx-(140*tamanhotela),maxy-(140*tamanhotela),p);
			}
		}
	}
}
