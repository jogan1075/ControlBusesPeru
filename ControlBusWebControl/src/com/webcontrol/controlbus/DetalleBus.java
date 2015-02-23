package com.webcontrol.controlbus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import Hilos.HiloDatosBus;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleBus extends Activity {
	int cont = 1;
	ListView lista;
	ArrayList<String> datos;
	Metodos metodos = new Metodos();
	private DataBaseManagerWS Manager;
	String placa="";
	String idprogramacion="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.detallebus);

		Bundle datos = getIntent().getExtras();

		placa = datos.getString("placa");

		Manager = new DataBaseManagerWS(this);
		
		TextView placaPatente = (TextView)findViewById(R.id.txtplacaPatente);
		TextView conductor1 = (TextView)findViewById(R.id.txtconductor1);
		TextView conductor2 = (TextView)findViewById(R.id.txtconductor2);
		TextView origen = (TextView)findViewById(R.id.txtorigen);
		TextView destino = (TextView)findViewById(R.id.txtdestino);
		TextView fecha = (TextView)findViewById(R.id.txtfecha);
		
		
		fecha.setText(metodos.devolverFecha());
		String fecha1 = getFecha().replace("/", "");
		Cursor cursorDatosBus = Manager.BuscarBusPatentefECHA(placa,fecha1);
		if (cursorDatosBus.moveToFirst()) {
			placaPatente.setText(cursorDatosBus.getString(1));
			conductor1.setText(cursorDatosBus.getString(9));
			conductor2.setText(cursorDatosBus.getString(10));
			origen.setText(cursorDatosBus.getString(2));
			destino.setText(cursorDatosBus.getString(3));
			
			idprogramacion = cursorDatosBus.getString(0);
			
		}else
		{
			Toast.makeText(this, "Error placa patente no existente...", Toast.LENGTH_SHORT).show();
			finish();
			startActivity(new Intent(DetalleBus.this, MenuBus.class));
			
		}

	}
	
	public String getFecha() {
		String FechaHora = getDateTime();
		String Fecha = FechaHora.substring(0, 10);
		String Hora = FechaHora.substring(11, FechaHora.length());
		return Fecha;
	}

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void Pasajeros(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		Intent i = new Intent(getApplicationContext(),IngresoPasajeros.class);
		i.putExtra("placa", placa);
		i.putExtra("idprogramacion", idprogramacion);
		startActivity(i);
		
	
	}

	public void Regresar(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(getApplicationContext(), MenuBus.class));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		metodos.devolverVibrador(this, 80);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			finish();
			startActivity(new Intent(getApplicationContext(), MenuBus.class));
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
