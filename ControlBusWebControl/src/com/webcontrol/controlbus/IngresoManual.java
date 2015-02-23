package com.webcontrol.controlbus;

import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class IngresoManual extends Activity {
EditText rutManual;
Metodos metodos = new Metodos();
String idprogramacionbusseleccionado="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_ingreso_manual);
		
		rutManual = (EditText) findViewById(R.id.txtRutManual);
		
		Bundle bundle = getIntent().getExtras();
		idprogramacionbusseleccionado = bundle.getString("IdProgramacionBusseleccionado");
	}

	public void BtnWS(View v) {
		DataBaseManagerWS Manager= new DataBaseManagerWS(this);
		metodos.devolverVibrador(this, 80);
     Intent intent = new Intent(getApplicationContext(),ReservaBus.class);
     intent.putExtra("tipodata", "IDFUNCIONARIO");
     intent.putExtra("data", rutManual.getText().toString().trim());
     intent.putExtra("IdProgramacionBusseleccionado", idprogramacionbusseleccionado);
     
     Cursor cursor = Manager.CursorBRegitroSinReserva();
		if(cursor.moveToFirst()){
			if(cursor.getString(1).equals("SI")){
				intent.putExtra("RegistroSinReserva", "SI");
			}
		}
     startActivity(intent);
	}
	public void Atras(View v){
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(getApplicationContext(),IngresoPasajeros.class));
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		metodos.devolverVibrador(this, 80);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			finish();
			startActivity(new Intent(getApplicationContext(),
					IngresoPasajeros.class));
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
