package com.webcontrol.controlbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MostrarPasajeros extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_mostrar_pasajeros);

		Bundle datos = getIntent().getExtras();
		
		TextView NombreFuncionarioReservas = (TextView) findViewById(R.id.NombreFuncionario);
		NombreFuncionarioReservas.setText(datos.getString("NombreFuncionarioReservas"));
		
		TextView ApellidoFuncionarioReservas = (TextView) findViewById(R.id.ApellidoFuncionarioReservas);
		ApellidoFuncionarioReservas.setText(datos.getString("ApellidoFuncionarioReservas"));
		
		TextView TipoReserva = (TextView) findViewById(R.id.TipoReserva);
		TipoReserva.setText( datos.getString("tiporeserva"));
		
		TextView NombreEmpresaReservas = (TextView) findViewById(R.id.NombreEmpresaReservas);
		NombreEmpresaReservas.setText(datos.getString("NombreEmpresaReservas"));
		
		TextView rut= (TextView)findViewById(R.id.rut);
		rut.setText(datos.getString("rut"));
		
		TextView autorizado = (TextView)findViewById(R.id.autorizado);
		autorizado.setText(datos.getString("Autorizado"));
		
		
		final Button button = (Button) findViewById(R.id.btnVolver);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				vibrar();
				Intent i = new Intent(MostrarPasajeros.this,
						IngresoPasajeros.class);
				startActivity(i);
			}
		});

	}

	public void vibrar() {
		Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		v.vibrate(80);
	}

}
