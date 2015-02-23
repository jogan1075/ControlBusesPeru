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
import android.widget.TextView;

public class Opciones extends Activity {
	Metodos metodos = new Metodos();
	String placa="", idprogramacion="";
	private DataBaseManagerWS Manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_opciones);
		
		TextView txtplacapatente = (TextView)findViewById(R.id.txtplacapatente);
		
		Bundle datos = getIntent().getExtras();

		if (datos != null) {

			placa = datos.getString("placa");
			idprogramacion = datos.getString("idprogramacion");
			txtplacapatente.setText(placa);
			
		}
		
		metodos = new Metodos();
		Manager = new DataBaseManagerWS(this);
	}

	public void Volver(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(getApplicationContext(),
				IngresoPasajeros.class));
	}



	public void Traslado(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		Intent i = new Intent (Opciones.this,ListaCheckBox.class);
		startActivity(i);
	}

	public void RegistroSinReserva(View v) {
		metodos.devolverVibrador(this, 80);
		
		Cursor cursor= Manager.CursorBRegitroSinReserva();
		if(cursor.moveToFirst()){
			
			if(cursor.getString(1).equals("NO") && cursor.getString(2).equals(idprogramacion)){
				Manager.ActualizarRegitroSinReserva("SI",idprogramacion);
			}
		}else
		{
			Manager.InsertarRegitroSinReserva("SI", idprogramacion);
		}
		
		finish();		
		
		startActivity(new Intent(getApplicationContext(),
				IngresoPasajeros.class));
		
		
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
