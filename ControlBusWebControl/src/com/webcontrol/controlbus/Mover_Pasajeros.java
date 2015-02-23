package com.webcontrol.controlbus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Mover_Pasajeros extends Activity {
	private EditText placa;
	private DataBaseManagerWS Manager;
	private String IdProgramador;
	private String Patente;

	Metodos metodos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mover__pasajeros);

		metodos = new Metodos();
		Manager = new DataBaseManagerWS(this);

		TextView date = (TextView) findViewById(R.id.txtfecha);

		date.setText("Fecha:  " + metodos.devolverFecha());
		
		placa = (EditText) findViewById(R.id.txtPlacaBusMenuBus);
		

	}
	
	public void Volver(View v) {
		metodos.devolverVibrador(this, 80);
		startActivity(new Intent(this, IngresoPasajeros.class));
		finish();		
	}

	public void btnSiguiente(View view) {
		String dato = placa.getText().toString();
		
		Cursor BuscarPatente = Manager.BuscarVehiculoId(dato);
		if (BuscarPatente.moveToFirst()) {
			
		}
		

	}

	
}
