package com.webcontrol.controlbus;

import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

public class TiempoEsperaReservas extends Activity {

	Metodos util;
	DataBaseManagerWS manager;
	EditText texto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_tiempo_espera_reservas);

		texto = (EditText) findViewById(R.id.edtxtMinutos);

		util = new Metodos();
		manager = new DataBaseManagerWS(this);

		Cursor cursor = manager.CursorVALORMINUTOS();
		if (cursor.moveToFirst()) {
			texto.setText(cursor.getString(1));
		} else {
			manager.InsertarVALORMINUTOS("5");
			texto.setText("5");
		}
	}

	public void BtnVolver(View v) {
		util.devolverVibrador(this, 80);
		startActivity(new Intent(this, Configuracion_Variables.class));
		finish();
	}

	public void BtnGuardar(View v) {
		util.devolverVibrador(this, 80);

		Cursor cursor = manager.CursorVALORMINUTOS();
		if (cursor.moveToFirst()) {
			manager.ActualizarVALORMINUTOS(texto.getText().toString());
		} else {
			manager.InsertarVALORMINUTOS(texto.getText().toString());
		}

		startActivity(new Intent(this, ConfiguracionPrincipal.class));
		finish();

	}
}
