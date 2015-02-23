package com.webcontrol.controlbus;

import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import Hilos.HiloValidarConexion;
import Hilos.HiloValidarConexionWSDos;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ToggleButton;

public class ConfigWS extends Activity {
	Metodos metodos = new Metodos();
	Vibrator Vibrador;
	private DataBaseManagerWS Manager;
	Cursor CursorWs;
	String NameSpace;
	String Url;
	String Usuario;
	String Contrasenia;
	String Autentificacion;

	EditText EdTxtNameSpace;
	EditText EdTxtUrl;
	EditText EdTxtUsuario;
	EditText EdTxtContrasenia;

	ToggleButton TBAutentificacion;
	String autentificacion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_config_control_bus);

		Manager = new DataBaseManagerWS(this);
		CursorWs = Manager.CursorConfig();

		EdTxtNameSpace = (EditText) findViewById(R.id.editTextNameSpace);
		EdTxtUrl = (EditText) findViewById(R.id.editTextUrlWS);
		EdTxtUsuario = (EditText) findViewById(R.id.editTextUsuarioWS);
		EdTxtContrasenia = (EditText) findViewById(R.id.editTextContraseniaWS);
		

		TBAutentificacion = (ToggleButton) findViewById(R.id.togglebuttonAutentificacion);
		if (CursorWs.moveToFirst()) {
			EdTxtNameSpace.setText(CursorWs.getString(0));
			EdTxtUrl.setText(CursorWs.getString(1));
			EdTxtUsuario.setText(CursorWs.getString(2));
			EdTxtContrasenia.setText(CursorWs.getString(3));
			autentificacion = CursorWs.getString(4);
		}
		if (autentificacion.equalsIgnoreCase("SI")) {
			TBAutentificacion.setChecked(true);
		} else {
			TBAutentificacion.setChecked(false);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		metodos.devolverVibrador(this, 80);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			finish();
			startActivity(new Intent(getApplicationContext(),
					ConfiguracionPrincipal.class));
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void BtnAutentificacion(View v) {
		// Is the toggle on?
		boolean on = ((ToggleButton) v).isChecked();

		metodos.devolverVibrador(this, 80);

		if (on) {
			Autentificacion = "SI";
			EdTxtUsuario.setEnabled(true);
			//EdTxtUsuario.setText("");
			EdTxtContrasenia.setEnabled(true);
			//EdTxtContrasenia.setText("");

		} else {
			Autentificacion = "NO";
			EdTxtUsuario.setEnabled(false);
			EdTxtContrasenia.setEnabled(false);

		}
	}

	public void BtnGuardar(View v) {
		metodos.devolverVibrador(this, 80);
		NameSpace = EdTxtNameSpace.getText().toString();
		Url = EdTxtUrl.getText().toString();
		Usuario = EdTxtUsuario.getText().toString();
		Contrasenia = EdTxtContrasenia.getText().toString();

		if (metodos.isOnline(this)) {
			if (EdTxtNameSpace.getText().length()>0 && EdTxtUrl.getText().length()>0 &&
					EdTxtUsuario.getText().length()>0 & EdTxtContrasenia.getText().length()>0){
				new HiloValidarConexionWSDos(this, this, NameSpace, Url,
						Autentificacion, Usuario, Contrasenia).execute();
			}else{
				metodos.devolverToast(this, "Alguno de los Campos está Vacío");
			}
		} else {
			metodos.mostrarDialogoSinAccion(this,
					"Error de Conexión a Internet",
					"Revise Su Conexión e Intente Nuevamente", this);
		}

	}

	public void BtnCancelar(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(getApplicationContext(),
				ConfiguracionPrincipal.class));
	}

}
