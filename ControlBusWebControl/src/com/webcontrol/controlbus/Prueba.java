package com.webcontrol.controlbus;

import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Prueba extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prueba);
		final EditText txt = (EditText) findViewById(R.id.editText1);
		final EditText txt2 = (EditText) findViewById(R.id.editText2);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Metodos metodos =new Metodos();
				metodos.recibirHora(Prueba.this, "00",
						"16");
			}
		});
	}
}
