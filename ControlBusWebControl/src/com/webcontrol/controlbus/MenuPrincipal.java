package com.webcontrol.controlbus;

import ClasesYMetodos.CustomDigitalClock;
import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import ClasesYMetodos.UserEmailFetcher;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MenuPrincipal extends Activity {
	static int repetir = 0;
	TextView fecha;
	Metodos metodos = new Metodos();
	DataBaseManagerWS Manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_menu_principal);
		fecha = (TextView) findViewById(R.id.txtfechalq);
		fecha.setText("Fecha: "+metodos.devolverFecha());
		
		TextView txtversion = (TextView)findViewById(R.id.txtversion);
		
		txtversion.setText("Version: " + metodos.devolverVersionApp(getApplicationContext()));
		
		Manager =new DataBaseManagerWS(this);
		Cursor VerConfig = Manager.CursorConfigApp();
		if (!VerConfig.moveToFirst()){
			Manager.InsertarConfigApp("SI", "SI", "SI");
		}
		
//		startService(new Intent(this, VerficiarCambiosService.class));
//		startService(new Intent(this, UpdateData.class));
//		startService(new Intent(this, ServicioPersonaVehiculos.class));
		
	}

	public void irBus(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(getApplicationContext(), MenuBus.class));
		
	}

	public void irConfig(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(getApplicationContext(), Contrasenia.class));
	}

	public void irAcercaDe(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(getApplicationContext(), AcercaDe.class));
	}

	public void irContacto(View v) {
		metodos.devolverVibrador(this, 80);
		UserEmailFetcher email = new UserEmailFetcher();
		String Correo = email.getEmail(getApplicationContext());
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				"mailto", "contacto@webcontrol.cl", null));
		if (Correo != null) {

			emailIntent
					.putExtra(Intent.EXTRA_SUBJECT, "Consulta de: " + Correo);
		} else {

			emailIntent.putExtra(Intent.EXTRA_SUBJECT,
					"Ingrese Título de la Consulta");
		}

		startActivity(Intent.createChooser(emailIntent, "Enviar Consulta"));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		metodos.devolverVibrador(this, 80);

		if (keyCode == KeyEvent.KEYCODE_BACK && repetir == 0) {

			Toast.makeText(getApplicationContext(),
					"Presione Nuevamente Para Salir", 100).show();
			repetir = 1;
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_BACK && repetir == 1) {

			finish();
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);

			startActivity(intent);
			repetir = 0;
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
