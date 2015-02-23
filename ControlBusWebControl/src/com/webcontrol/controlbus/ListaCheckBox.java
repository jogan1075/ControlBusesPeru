package com.webcontrol.controlbus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Adaptador.AdaptadorPersonas;
import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import ClasesYMetodos.Personas;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class ListaCheckBox extends Activity {

	AdaptadorPersonas adaptador = null;
	DataBaseManagerWS Manager;
	String CampoAmostrar;
	String IdProgramacion;
	ArrayList<Personas> listaPersona;
	ListView listView;
	String NombreEncontrado, ApellidoEncontrado, RutEncontrado,
			IdProgramacionEncontrado;

	Metodos util;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.lista_check_box);
		listView = (ListView) findViewById(R.id.listView1);

		Manager = new DataBaseManagerWS(this);
		util = new Metodos();
		listaPersona = new ArrayList<Personas>();

		MostrarLista();
	}

	public void Volver(View v) {
		util.devolverVibrador(this, 80);
		startActivity(new Intent(this, IngresoPasajeros.class));
		finish();
	}

	public void btnMover(View v) {
		util.devolverVibrador(this, 80);
		ArrayList<Personas> arrayPersona = adaptador.lista;
		for (int i = 0; i < arrayPersona.size(); i++) {
			Personas persona = arrayPersona.get(i);
			if (persona.isSelected()) {
				String rut = persona.getRut();
				

			}
		}
	}

	public void MostrarLista() {

		Cursor BuscarPatente = Manager.BuscarPatente(util.getFecha());
		if (BuscarPatente.moveToFirst()) {
			IdProgramacion = BuscarPatente.getString(0);
		}

		Cursor Buscar = Manager.Buscar(IdProgramacion);
		if (Buscar.moveToFirst()) {
			do {
				IdProgramacionEncontrado = Buscar.getString(1);
				RutEncontrado = Buscar.getString(2);
				NombreEncontrado = Buscar.getString(3);
				ApellidoEncontrado = Buscar.getString(4);

				listaPersona.add(new Personas(NombreEncontrado,
						ApellidoEncontrado, RutEncontrado,
						IdProgramacionEncontrado, false));
			} while (Buscar.moveToNext());
		}

		adaptador = new AdaptadorPersonas(this, R.layout.campos_pasajeros,
				listaPersona);
		listView.setAdapter(adaptador);
	}

}