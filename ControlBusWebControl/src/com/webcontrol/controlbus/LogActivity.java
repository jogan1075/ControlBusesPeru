package com.webcontrol.controlbus;

import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

public class LogActivity extends Activity {
Metodos metodos = new Metodos();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_log);
	}

	public void Regresar(View v) {
		metodos.devolverVibrador(getApplicationContext(), 80);
		finish();
		startActivity(new Intent(getApplicationContext(),
				ConfiguracionPrincipal.class));
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
}
