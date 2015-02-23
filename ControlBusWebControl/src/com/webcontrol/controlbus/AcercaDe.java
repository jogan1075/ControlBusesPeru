package com.webcontrol.controlbus;

import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class AcercaDe extends Activity {
	TextView txtVersion, txtAño,txtfin;
	Metodos metodos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_acerca_de);
		txtVersion = (TextView) findViewById(R.id.textViewVersion);
		txtAño = (TextView) findViewById(R.id.textViewAnio);
		txtfin = (TextView) findViewById(R.id.textView4);
		metodos = new Metodos();
		txtVersion.setText("Versión: " + metodos.devolverVersionApp(this));
		txtAño.setText(metodos.devolverAño() + " WebControl System Ltda.");

		
	}

	public void VolverAcercade(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(getApplicationContext(), MenuPrincipal.class));
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
