package com.webcontrol.controlbus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import Hilos.HiloReservarPasaje;
import Hilos.HiloSincronizacionPersonas;
import Hilos.HiloSincronizacionProgamacionBus;
import Hilos.HiloSincronizacionReservas;
import Hilos.HiloSincronizacionVehiculos;
import Hilos.HiloSincronizarReservasLocales;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuBus extends Activity {
	EditText txt;
	Metodos metodos = new Metodos();
	private DataBaseManagerWS Manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_menu_bus);
		txt = (EditText) findViewById(R.id.txtPlacaBusMenuBus);
		txt.setText("BCHG68");
		
		TextView date = (TextView)findViewById(R.id.txtfecha);
		
		date.setText("Fecha:  " +metodos.devolverFecha());

		Manager = new DataBaseManagerWS(this);
		
		Cursor HabilitaSincronizacion = Manager.CursorHABILITAR_SINCRONIZACIONAUTOMATICA();
		if(HabilitaSincronizacion.moveToFirst()){
			
			if(HabilitaSincronizacion.getString(1).equalsIgnoreCase("si")){
				if (metodos.isOnline(this)) {
					Cursor CursorSincReservaLocales = Manager
							.CursorSincReservaLocales();
					if (CursorSincReservaLocales.moveToFirst()) {

						do {

							new HiloSincronizarReservasLocales(
									CursorSincReservaLocales.getString(0),
									CursorSincReservaLocales.getString(1),
									CursorSincReservaLocales.getString(2), this)
									.execute();

						} while (CursorSincReservaLocales.moveToNext());
					}
					
					
					Cursor CursorSincronizacionReservas = Manager
							.CursorSincronizacionReservas();

					if (CursorSincronizacionReservas.moveToFirst()) {
						do {
							
							new HiloSincronizacionReservas(CursorSincronizacionReservas.getString(0),this).execute();

						} while (CursorSincronizacionReservas.moveToNext());
					}

					Cursor CursorSincronizacionProgramacionBus = Manager
							.CursorSincronizacion();

					if (CursorSincronizacionProgramacionBus.moveToFirst()) {
						do {
								
							new HiloSincronizacionProgamacionBus(CursorSincronizacionProgramacionBus.getString(0),this).execute();

						} while (CursorSincronizacionProgramacionBus.moveToNext());
					}
					
					Cursor CursorSincronizacionPersona = Manager.CursorSincronizacionPersona();

					if (CursorSincronizacionPersona.moveToFirst()) {
						do {
							
							new HiloSincronizacionPersonas(CursorSincronizacionPersona.getString(1),this).execute();
						} while (CursorSincronizacionPersona.moveToNext());
					}
					
					Cursor CursorSincronizacionVehiculo = Manager
							.CursorSincronizacionVehiculo();

					if (CursorSincronizacionVehiculo.moveToFirst()) {
						do {
							
							new HiloSincronizacionVehiculos(CursorSincronizacionVehiculo.getString(1),this).execute();

						} while (CursorSincronizacionVehiculo.moveToNext());

					}
					
					Cursor c = Manager.CursorReservas();
					if (c.moveToFirst()) {


						do {
							String resp = c.getString(15);
							if (resp.equalsIgnoreCase("movil")) {
								String fecha = getFecha();
								String hora = getHora();
								new HiloReservarPasaje(getApplicationContext(),c.getString(1),c.getString(0),fecha,hora,c.getString(8)).execute();
								}
						} while (c.moveToNext());
					}
				}
			}
			
		}
	
		
	}

	public void VolverBus(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(this, MenuPrincipal.class));
	}

	public void IngresarPlaca(View v) {
		metodos.devolverVibrador(this, 80);
		if (txt.getText().toString().length() == 0) {
			Toast.makeText(getApplicationContext(),
					"No Ingrese Valores Vacíos", Toast.LENGTH_SHORT).show();
		} else {
			
			Cursor CursorBus = Manager.BuscarBusPatentefECHA(txt.getText().toString().trim().toUpperCase(),getFecha().replace("/", ""));
			if(CursorBus.moveToFirst()){
				String fecha = CursorBus.getString(5);
				
				if(fecha.equalsIgnoreCase(getFecha().replace("/", ""))){
					finish();
					Intent i = new Intent(this, DetalleBus.class);
					i.putExtra("placa", txt.getText().toString().toUpperCase());
					startActivity(i);
				}else
				{
					Toast.makeText(getApplicationContext(),
							"No Existe Reservas Para la fecha actual", Toast.LENGTH_LONG).show();
				}
			}else
			{
				Toast.makeText(getApplicationContext(),
						"No Existe Reservas Para la fecha actual y la Placa Patente que fue ingresada", Toast.LENGTH_LONG).show();
			}
			
			
		}

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
	public String getFecha() {
		String FechaHora = getDateTime();
		String Fecha = FechaHora.substring(0, 10);
		String Hora = FechaHora.substring(11, FechaHora.length());
		return Fecha;
	}
	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	

	public String getHora() {
		String FechaHora = getDateTime();
		String Fecha = FechaHora.substring(0, 10);
		String Hora = FechaHora.substring(11, FechaHora.length());
		Hora = Hora.substring(0, 5);
		return Hora;
	}
	

}
