package com.webcontrol.controlbus;

import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ConfiguracionPrincipal extends Activity {
Metodos metodos = new Metodos();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_configuracion_principal);
	}

	public void btnSincronizar(View v) {
		metodos.devolverVibrador(this, 80);
		Intent i = new Intent(this, Sincronizacion_buses.class);
		finish();
		startActivity(i);

	}
	
	public void btnConfiguracionApp(View v) {
		metodos.devolverVibrador(this, 80);
		Intent i = new Intent(this, Configuracion.class);
		finish();
		startActivity(i);

	}
	public void BtnConexionWs(View v) {
		metodos.devolverVibrador(this, 80);
		Intent i = new Intent(this, ConfigWS.class);
		finish();
		startActivity(i);
	}
	public void Regresar(View v){
		metodos.devolverVibrador(this, 80);
		Intent i = new Intent(this, MenuPrincipal.class);
		finish();
		startActivity(i);
	}
	public void Log(View v){
		metodos.devolverVibrador(this, 80);
		Intent i = new Intent(this, LogActivity.class);
		finish();
		startActivity(i);
	}
	public void btnSalirApp(View v){
		metodos.devolverVibrador(this, 80);
		metodos.dialogoConfirmacion("¿Desea Salir de la Aplicación?",null, this, "salir", this).show();
	}
	
	public void btnVariableApp(View v){
		metodos.devolverVibrador(this, 80);
		startActivity(new Intent(this,Configuracion_Variables.class));
		finish();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		metodos.devolverVibrador(this, 80);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			finish();
			startActivity(new Intent(getApplicationContext(),
					MenuPrincipal.class));
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
