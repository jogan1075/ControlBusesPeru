package com.webcontrol.controlbus;


import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	 private ProgressBar progressBar;
	 private int progressStatus = 0;
	 private Handler handler = new Handler();
	 Cursor TraerDatosWS;
	 DataBaseManagerWS ManagerWS;
	 LinearLayout layoutInicio;
	 Metodos metodos = new Metodos();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		 progressBar.setIndeterminate(true);
		 layoutInicio = (LinearLayout) findViewById(R.id.LogoCargando);
		 layoutInicio.setVisibility(View.INVISIBLE);
		 ManagerWS = new DataBaseManagerWS(this);
		 TraerDatosWS = ManagerWS.CursorConfig();
		 if (TraerDatosWS.moveToFirst()){
			 finish();
			 startActivity(new Intent(getApplicationContext(),MenuPrincipal.class));
		 }
		 else{
			 cargarHilo();
		 }
		  
    }
    private void cargarHilo() {
		// TODO Auto-generated method stub
		layoutInicio.setVisibility(View.VISIBLE);
		new Thread(new Runnable() {
		     public void run() {
		        while (progressStatus < 100) {
		           progressStatus += 10;
		   
		    handler.post(new Runnable() {
		    public void run() {
		       progressBar.setProgress(progressStatus);
		       if (progressStatus ==100){
		    	   finish();
					  startActivity(new Intent(getApplicationContext(),ConfigControlBus.class));
				  }
		    }
		        });
		        try {
		           // Sleep for 200 milliseconds. 
		                         //Just to display the progress slowly
		           Thread.sleep(100);
		        } catch (InterruptedException e) {
		           e.printStackTrace();
		        }
		     }
		  }
		  }).start();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		metodos.devolverVibrador(this, 80);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			finish();
		
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
