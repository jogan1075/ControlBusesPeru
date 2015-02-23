package com.webcontrol.controlbus;

import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Configuracion_Variables extends Activity {
	Metodos util;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_configuracion__variables);
		util = new Metodos();
	}

	public void BtnTiempoEsperaReservas(View v) {
		
		util.devolverVibrador(this, 80);
		startActivity(new Intent(this, TiempoEsperaReservas.class));
		
		finish();
	}

	public void Regresar(View v) {
		util.devolverVibrador(this, 80);
		startActivity(new Intent(this,MenuPrincipal.class));
		finish();
		
	}

}
