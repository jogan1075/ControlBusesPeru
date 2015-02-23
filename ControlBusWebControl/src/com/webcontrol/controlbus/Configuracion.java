package com.webcontrol.controlbus;

import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class Configuracion extends Activity implements OnCheckedChangeListener{
Metodos metodos = new Metodos();
String TextoCamara, TextoNFC, TextoLog;
Switch switchCamara, switchNFC, switchLog, switchSincronizacion;
DataBaseManagerWS Manager;
String Idioma;
TextView CamaraConfig, NFCConfig, LogConfig, TextoArribaConfig,
		SincronizacionConfig;
Button Aceptar, Cancelar;
Vibrator vibrator;
Cursor CursorAPP;
String ConfigCamara, ConfigNfc, ConfigLog;
String HabCamara, HabNFC, HabLog, HabSinc;
Cursor VerEstado, ComprobarServicio;
String estadoServicio = "";
String SiNoServicio = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_configuracion);
		Manager = new DataBaseManagerWS(this);

		CamaraConfig = (TextView) findViewById(R.id.camaraConfig);
		NFCConfig = (TextView) findViewById(R.id.NFCConfig);
		LogConfig = (TextView) findViewById(R.id.LogConfig);
		TextoArribaConfig = (TextView) findViewById(R.id.textoArribaConfig);
		SincronizacionConfig = (TextView) findViewById(R.id.txtSincronizacionConfigApp);

		switchCamara = (Switch) findViewById(R.id.switch1);
		switchCamara.setOnCheckedChangeListener(this);
		switchNFC = (Switch) findViewById(R.id.switch2);
		switchNFC.setOnCheckedChangeListener(this);
		switchLog = (Switch) findViewById(R.id.switch3);
		switchLog.setOnCheckedChangeListener(this);
//		switchSincronizacion = (Switch) findViewById(R.id.switch4);
//		switchSincronizacion.setOnCheckedChangeListener(this);

	//	Aceptar = (Button) findViewById(R.id.buttonGuardarConfigApp);
		Cancelar = (Button) findViewById(R.id.buttonCancelarConfigApp);
		

		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		CursorAPP = Manager.CursorConfigApp();
		if (CursorAPP.moveToFirst()) {
			do {
				ConfigCamara = CursorAPP.getString(0);
				ConfigNfc = CursorAPP.getString(1);
				ConfigLog = CursorAPP.getString(2);
				
			} while (CursorAPP.moveToNext());
		}
		if (ConfigCamara.equalsIgnoreCase("SI")) {
			switchCamara.setChecked(true);
			HabCamara = "SI";
		} else {
			switchCamara.setChecked(false);
			HabCamara = "NO";
		}
		if (ConfigNfc.equalsIgnoreCase("SI")) {
			switchNFC.setChecked(true);
			HabNFC = "SI";
		} else {
			switchNFC.setChecked(false);
			HabNFC = "NO";
		}
		if (ConfigLog.equalsIgnoreCase("SI")) {
			switchLog.setChecked(true);
			HabLog = "SI";
		} else {
			switchLog.setChecked(false);
			HabLog = "NO";
		}
	}
	
	public void BtnGuardar(View v){
		metodos.devolverVibrador(this, 80);
		Cursor VerConfig = Manager.CursorConfigApp();
		if (VerConfig.moveToFirst()) {
			Manager.ActualizarConfigApp(HabCamara, HabNFC, HabLog
					);
		} else {
			Manager.InsertarConfigApp(HabCamara, HabNFC, HabLog
					);
		}
    finish();
    startActivity(new Intent(getApplicationContext(), ConfiguracionPrincipal.class));
	}
	
	public void BtnCancelarConfigApp(View v){
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(getApplicationContext(), ConfiguracionPrincipal.class));
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.switch1:
			if (buttonView.isChecked()) {
				vibrator.vibrate(80);
				HabCamara = "SI";

			} else {
				vibrator.vibrate(80);
				HabCamara = "NO";
			}

			break;

		case R.id.switch2:
			if (buttonView.isChecked()) {
				vibrator.vibrate(80);
				HabNFC = "SI";
			} else {
				vibrator.vibrate(80);
				HabNFC = "NO";
			}

			break;
		case R.id.switch3:
			if (buttonView.isChecked()) {
				vibrator.vibrate(80);
				HabLog = "SI";
			} else {
				vibrator.vibrate(80);
				HabLog = "NO";
			}

			break;
//		case R.id.switch4:
//			if (buttonView.isChecked()) {
//				vibrator.vibrate(80);
//				estadoServicio = "SI";
//			} else {
//				vibrator.vibrate(80);
//				estadoServicio = "NO";
//			}
//
//			break;
		}
	}

	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		metodos.devolverVibrador(this, 80);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			finish();
			startActivity(new Intent(getApplicationContext(), ConfiguracionPrincipal.class));
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}
}
