package com.webcontrol.controlbus;


import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class Contrasenia extends Activity {

CheckBox mostrarContrasenia;
EditText EdTxtContrasenia;
Metodos metodos = new Metodos();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_contrasenia);
		
		EdTxtContrasenia = (EditText) findViewById(R.id.editTextPass);
		
		
		mostrarContrasenia = (CheckBox) findViewById(R.id.mostrarPassConfig);
		mostrarContrasenia
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						metodos.devolverVibrador(Contrasenia.this, 80);
						if (isChecked) {

							EdTxtContrasenia
									.setTransformationMethod(HideReturnsTransformationMethod
											.getInstance());
						} else {

							EdTxtContrasenia
									.setTransformationMethod(PasswordTransformationMethod
											.getInstance());
						}
					}
				});
	}
	public void BtnContrasenia(View v){
		metodos.devolverVibrador(this, 80);
		if (EdTxtContrasenia.getText().toString().equalsIgnoreCase("Desa1.")){
			Intent i = new Intent(this, ConfiguracionPrincipal.class);
			finish();
			startActivity(i);
			
		}
		else if (EdTxtContrasenia.getText().length()==0){
			metodos.devolverToast(this, "Ingrese la Contraseña para Acceder").show();	
		}else{
			metodos.devolverToast(this, "Contraseña Errónea").show();	
		}
		
		
	}
	public void Regresar(View v) {
		metodos.devolverVibrador(this, 80);
		Intent i = new Intent(this, MenuPrincipal.class);
		finish();
		startActivity(i);
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
