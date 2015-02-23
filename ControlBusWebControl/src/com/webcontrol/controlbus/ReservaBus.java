package com.webcontrol.controlbus;

import ClasesYMetodos.DataBaseManagerWS;
import Hilos.HiloConsultaReserva;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ReservaBus extends Activity {

	TextView txtNombre, txtDni, txtEmpresa, txtOrigen, txtDestino, txtHora, txtFecha,
			txtPlacaBus;
	Button btn;
	Bundle bundle;
	String dataFunc, tipoDataFunc, fechaActual, idprogramacionbusseleccionado, registrosinreserva;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_reserva_bus);
		txtNombre = (TextView) findViewById(R.id.txtNombreBus);
		txtDni = (TextView) findViewById(R.id.txtRutBus);
		txtEmpresa = (TextView) findViewById(R.id.txtEmpresaBus);
		txtOrigen = (TextView) findViewById(R.id.txtOrigenBus);
		txtDestino = (TextView) findViewById(R.id.txtDestinoBus);
		txtHora = (TextView) findViewById(R.id.txtHoraBus);
		txtFecha = (TextView) findViewById(R.id.txtFechaBus);
		txtPlacaBus = (TextView) findViewById(R.id.txtPatenteBus);
		DataBaseManagerWS Manager= new DataBaseManagerWS(this);
		btn = (Button) findViewById(R.id.btnConfirmarBus);

		bundle = getIntent().getExtras();
		if (bundle != null) {
			dataFunc = bundle.getString("data");
			tipoDataFunc = bundle.getString("tipodata");
			fechaActual = bundle.getString("fechaactual");
			idprogramacionbusseleccionado = bundle.getString("IdProgramacionBusseleccionado");
			Cursor cursor = Manager.CursorBRegitroSinReserva();
			if(cursor.moveToFirst()){
				if(cursor.getString(1).equals("SI")){
					registrosinreserva = bundle.getString("RegistroSinReserva");
				}
			}else
			{
				registrosinreserva = null;
			}
			
			new HiloConsultaReserva(this, this, txtNombre, txtDni, txtEmpresa,
					txtOrigen, txtDestino, txtHora, txtFecha, txtPlacaBus,
					tipoDataFunc, dataFunc, fechaActual, idprogramacionbusseleccionado, btn, registrosinreserva).execute();
		}
	}
}
