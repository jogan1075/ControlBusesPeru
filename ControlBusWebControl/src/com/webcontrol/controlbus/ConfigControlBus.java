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
import android.transition.Visibility;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ToggleButton;

public class ConfigControlBus extends Activity {
	Vibrator Vibrador;
	private DataBaseManagerWS Manager;
	Cursor CursorWs;
	String NameSpace;
	String Url;
	String Usuario;
	String Contrasenia;
	String Autentificacion;
	String Autor;

	EditText EdTxtNameSpase;
	EditText EdTxtUrl;
	EditText EdTxtUsuario;
	EditText EdTxtContrasenia;

	ToggleButton TBAutentificacion;
	Metodos metodos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_config_control_bus);
		Vibrador = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);

		Manager = new DataBaseManagerWS(this);
		CursorWs = Manager.CursorConfig();

		EdTxtNameSpase = (EditText) findViewById(R.id.editTextNameSpace);
		EdTxtUrl = (EditText) findViewById(R.id.editTextUrlWS);
		EdTxtUsuario = (EditText) findViewById(R.id.editTextUsuarioWS);
		EdTxtContrasenia = (EditText) findViewById(R.id.editTextContraseniaWS);

		EdTxtNameSpase.setText("http://webservice.webcontrol.cl/");
		EdTxtNameSpase.setEnabled(false);
		 //EdTxtUrl.setText("http://desarrollo.webcontrol.cl/webservice/WSControlBus.asmx");
		EdTxtUrl.setText("https://app.xstratacopperperu.pe/wsandroid/servicioandroid/WSControlBus.asmx");
		EdTxtUsuario.setText("desarrollo");
		//EdTxtUsuario.setVisibility(View.INVISIBLE);
		EdTxtContrasenia.setText("Desa1.");
		//EdTxtContrasenia.setVisibility(View.INVISIBLE);
		TBAutentificacion = (ToggleButton) findViewById(R.id.togglebuttonAutentificacion);

		metodos = new Metodos();
	}

	public void BtnAutentificacion(View v) {
		// Is the toggle on?
		boolean on = ((ToggleButton) v).isChecked();

		Vibrador.vibrate(80);

		if (on) {
			Autentificacion = "SI";
			EdTxtUsuario.setEnabled(true);
			EdTxtContrasenia.setEnabled(true);

		} else {
			Autentificacion = "NO";
			EdTxtUsuario.setEnabled(false);
			EdTxtContrasenia.setEnabled(false);

		}
	}

	public void BtnGuardar(View v) {
		Vibrador.vibrate(80);

		NameSpace = EdTxtNameSpase.getText().toString();
		Url = EdTxtUrl.getText().toString();
		Usuario = EdTxtUsuario.getText().toString();
		Contrasenia = EdTxtContrasenia.getText().toString();
		Autor = Autentificacion;

		if (metodos.isOnline(this)) {
			if (EdTxtNameSpase.getText().length() > 0
					&& EdTxtUrl.getText().length() > 0
					&& EdTxtUsuario.getText().length() > 0
					& EdTxtContrasenia.getText().length() > 0) {

				if (Autor == null) {
					new HiloValidarConexion(this, this, NameSpace, Url,
							"NO", Usuario, Contrasenia).execute();
				}else{
					new HiloValidarConexion(this, this, NameSpace, Url,
							Autor, Usuario, Contrasenia).execute();
				}
			} else {
				metodos.devolverToast(this, "Alguno de los Campos está Vacío")
						.show();
			}

		} else {
			metodos.mostrarDialogoSinAccion(this,
					"Error de Conexión a Internet",
					"Revise Su Conexión e Intente Nuevamente", this);
		}

	}

	public void BtnCancelar(View v) {
		Vibrador.vibrate(80);
		Metodos metodos = new Metodos();
		AlertDialog dialogo = metodos.dialogoConfirmacion(
				"Saliendo de la Aplicación", "¿Está Seguro?", this, "salir",
				this);
		dialogo.show();

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
