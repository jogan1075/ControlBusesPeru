package com.webcontrol.controlbus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListadoPasajeros extends Activity {
	private ProgressDialog pDialog;

	private String id;
	private String texto;
	private String Nombre;
	private DataBaseManagerWS Manager;
	private ListView ListView;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String gettexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String Nombre) {
		this.Nombre = Nombre;
	}

	@Override
	public String toString() {
		return Nombre;
	}

	Metodos metodos = new Metodos();

	private Object position;

	private String IdProgramador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_listado_pasajeros);
		Manager = new DataBaseManagerWS(this);
		Manager.deleteUsuariosSi();

		final Button button = (Button) findViewById(R.id.ButtonVolver);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				vibrar();
				Intent i = new Intent(ListadoPasajeros.this,
						IngresoPasajeros.class);
				startActivity(i);
			}
		});

		Bundle recibir = this.getIntent().getExtras();
		String btn1 = recibir.getString("btn1");

		switch (Integer.parseInt(btn1)) {
		case 1:
			cargarListaPasajeros();
			break;
		case 2:
			cargarListaPasajerosReservas();
			break;
		case 3:
			Autorizados();
		break;
		case 4:
			 Pendientes();
		break;
		}
		
		
	}

	public void Pendientes() {

		String dato = "NO";
		String Reserva = "MOVIL";
		/************* cursor Bscar Patente ********************/
		Cursor BuscarPatente = Manager.BuscarPatente(getFecha());
		if (BuscarPatente.moveToFirst()) {
			String IdProgramador = BuscarPatente.getString(0);
			this.IdProgramador = IdProgramador;
		}
		/******************************************************/
		ListView lista = (ListView) findViewById(R.id.listView1);
		List<ListadoPasajeros> listarUsu = Manager.getAll(IdProgramador, dato,
				Reserva);
		ArrayAdapter<ListadoPasajeros> adapter = new ArrayAdapter<ListadoPasajeros>(
				this, android.R.layout.simple_list_item_1, listarUsu);
		lista.setAdapter(adapter);
		lista.setTextFilterEnabled(true);
		Cursor CursorUsuario = Manager.BuscarUsuarioReservas(IdProgramador,
				dato, Reserva);

		if (CursorUsuario.moveToFirst()) {
			Manager.BuscarUsuarioReservas(IdProgramador, dato, Reserva);

			do {

				String nombre = CursorUsuario.getString(3);
				String RutFuncionarioReservas = CursorUsuario.getString(2);
				String ApellidoFuncionarioReservas = CursorUsuario.getString(4);
				String RutEmpresaReservas = CursorUsuario.getString(5);
				String NombreEmpresaReservas = CursorUsuario.getString(6);
				String AutorizacionAccesoReservas = CursorUsuario.getString(7);
				String UidReservas = CursorUsuario.getString(8);
				String EstadoUidReservas = CursorUsuario.getString(9);
				String AsientoReservas = CursorUsuario.getString(10);
				String UtilizoReservas = CursorUsuario.getString(11);
				String RestantesReservas = CursorUsuario.getString(13);
				String Pendiente = CursorUsuario.getString(14);
				String Autorizado = CursorUsuario.getString(15);
				String TipoReserva = CursorUsuario.getString(16);

				Manager.Insertar(nombre, RutFuncionarioReservas,
						ApellidoFuncionarioReservas, RutEmpresaReservas,
						NombreEmpresaReservas, EstadoUidReservas,
						UtilizoReservas, Pendiente, Autorizado, TipoReserva);

			} while (CursorUsuario.moveToNext());
		}
		/******************************************************/
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int posicion = position + 1;
				vibrar();
				new HiloDetalle(posicion).execute();
			}
		});

	}

	/********* Cargar metodo Pendientes ************/
	public void Autorizados() {

		String dato = "SI";
		String Reserva = "MOVIL";
		ListView lista = (ListView) findViewById(R.id.listView1);
		List<ListadoPasajeros> listarUsu = Manager.getAll(IdProgramador, dato,
				Reserva);
		ArrayAdapter<ListadoPasajeros> adapter = new ArrayAdapter<ListadoPasajeros>(
				this, android.R.layout.simple_list_item_1, listarUsu);
		lista.setAdapter(adapter);
		lista.setTextFilterEnabled(true);
		Cursor CursorUsuario = Manager.BuscarUsuarioReservas(IdProgramador,
				dato, Reserva);

		if (CursorUsuario.moveToFirst()) {
			Manager.BuscarUsuarioReservas(IdProgramador, dato, Reserva);

			do {

				String nombre = CursorUsuario.getString(3);
				String RutFuncionarioReservas = CursorUsuario.getString(2);
				String ApellidoFuncionarioReservas = CursorUsuario.getString(4);
				String RutEmpresaReservas = CursorUsuario.getString(5);
				String NombreEmpresaReservas = CursorUsuario.getString(6);
				String AutorizacionAccesoReservas = CursorUsuario.getString(7);
				String UidReservas = CursorUsuario.getString(8);
				String EstadoUidReservas = CursorUsuario.getString(9);
				String AsientoReservas = CursorUsuario.getString(10);
				String UtilizoReservas = CursorUsuario.getString(11);
				String RestantesReservas = CursorUsuario.getString(13);
				String Pendiente = CursorUsuario.getString(14);
				String Autorizado = CursorUsuario.getString(15);
				String TipoReserva = CursorUsuario.getString(16);

				Manager.Insertar(nombre, RutFuncionarioReservas,
						ApellidoFuncionarioReservas, RutEmpresaReservas,
						NombreEmpresaReservas, EstadoUidReservas,
						UtilizoReservas, Pendiente, Autorizado, TipoReserva);

			} while (CursorUsuario.moveToNext());
		}
		/******************************************************/
		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int posicion = position + 1;
				vibrar();
				new HiloDetalle(posicion).execute();
			}
		});

	}

	private void cargarListaPasajeros() {

		Cursor BuscarPatente = Manager.BuscarPatente(getFecha());
		if (BuscarPatente.moveToFirst()) {

			String IdProgramador = BuscarPatente.getString(0);
			this.IdProgramador = IdProgramador;
		}

		ListView lista = (ListView) findViewById(R.id.listView1);
		List<ListadoPasajeros> listarUsu = Manager.getAll(IdProgramador,"NO","WEB");
		ArrayAdapter<ListadoPasajeros> adapter = new ArrayAdapter<ListadoPasajeros>(
				this, android.R.layout.simple_list_item_1, listarUsu);
		lista.setAdapter(adapter);
		lista.setTextFilterEnabled(true);
		Cursor CursorUsuario = Manager.BuscarUsuarioNO(IdProgramador);

		if (CursorUsuario.moveToFirst()) {
			Manager.BuscarUsuarioSI(IdProgramador);

			do {

				String nombre = CursorUsuario.getString(3);
				String RutFuncionarioReservas = CursorUsuario.getString(2);
				String ApellidoFuncionarioReservas = CursorUsuario.getString(4);
				String RutEmpresaReservas = CursorUsuario.getString(5);
				String NombreEmpresaReservas = CursorUsuario.getString(6);
				String AutorizacionAccesoReservas = CursorUsuario.getString(7);
				String UidReservas = CursorUsuario.getString(8);
				String EstadoUidReservas = CursorUsuario.getString(9);
				String AsientoReservas = CursorUsuario.getString(10);
				String UtilizoReservas = CursorUsuario.getString(11);
				String RestantesReservas = CursorUsuario.getString(13);
				String Pendiente = CursorUsuario.getString(14);
				String Autorizado = CursorUsuario.getString(15);
				String TipoReserva = CursorUsuario.getString(16);

				Manager.Insertar(nombre, RutFuncionarioReservas,
						ApellidoFuncionarioReservas, RutEmpresaReservas,
						NombreEmpresaReservas, EstadoUidReservas,
						UtilizoReservas, Pendiente, Autorizado, TipoReserva);

			} while (CursorUsuario.moveToNext());
		}

		lista.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				int posicion = position + 1;
				vibrar();
				new HiloDetalle(posicion).execute();
			}
		});
	}

	private void cargarListaPasajerosReservas() {

		Cursor BuscarPatente = Manager.BuscarPatente(getFecha());
		if (BuscarPatente.moveToFirst()) {

			String IdProgramador = BuscarPatente.getString(0);
			this.IdProgramador = IdProgramador;
		}

		ListView lista = (ListView) findViewById(R.id.listView1);
		List<ListadoPasajeros> listarUsu = Manager.getAll(IdProgramador,"SI","WEB");
		ArrayAdapter<ListadoPasajeros> adapter = new ArrayAdapter<ListadoPasajeros>(
				this, android.R.layout.simple_list_item_1, listarUsu);
		lista.setAdapter(adapter);
		lista.setTextFilterEnabled(true);
		Cursor CursorUsuario = Manager.BuscarUsuarioSI(IdProgramador);
		if (CursorUsuario.moveToFirst()) {
			Manager.BuscarUsuarioSI(IdProgramador);
			do {
				String nombre = CursorUsuario.getString(3);
				String RutFuncionarioReservas = CursorUsuario.getString(2);
				String ApellidoFuncionarioReservas = CursorUsuario.getString(4);
				String RutEmpresaReservas = CursorUsuario.getString(5);
				String NombreEmpresaReservas = CursorUsuario.getString(6);
				String AutorizacionAccesoReservas = CursorUsuario.getString(7);
				String UidReservas = CursorUsuario.getString(8);
				String EstadoUidReservas = CursorUsuario.getString(9);
				String AsientoReservas = CursorUsuario.getString(10);
				String UtilizoReservas = CursorUsuario.getString(11);
				String RestantesReservas = CursorUsuario.getString(13);
				String Pendiente = CursorUsuario.getString(14);
				String Autorizado = CursorUsuario.getString(15);
				String TipoReserva = CursorUsuario.getString(16);

				Manager.Insertar(nombre, RutFuncionarioReservas,
						ApellidoFuncionarioReservas, RutEmpresaReservas,
						NombreEmpresaReservas, EstadoUidReservas,
						UtilizoReservas, Pendiente, Autorizado, TipoReserva);

			} while (CursorUsuario.moveToNext());
		}

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int posicion = position + 1;
				vibrar();
				new HiloDetalle(posicion).execute();
			}
		});
	}

	public void Guardar(View v) {
		metodos.devolverVibrador(this, 80);
	}

	public void VolverBus(View v) {
		metodos.devolverVibrador(this, 80);
		finish();
		startActivity(new Intent(getApplicationContext(), Opciones.class));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		metodos.devolverVibrador(this, 80);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			finish();
			startActivity(new Intent(getApplicationContext(), Opciones.class));
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	/******************************* listado **************************************/
	private class HiloDetalle extends AsyncTask<Void, Void, Void> {

		private String nombre;
		private int posicion;
		private String apellido;
		private String RutFuncionarioReservas;
		private String ApellidoFuncionarioReservas;
		private String RutEmpresaReservas;
		private String NombreEmpresaReservas;
		private String AutorizacionAccesoReservas;
		private String UidReservas;
		private String EstadoUidReservas;
		private String AsientoReservas;
		private String UtilizoReservas;
		private String RestantesReservas;
		private String Pendiente;
		private String Autorizado;
		private String TipoReserva;
		private String idProgramador;

		public HiloDetalle(int posicion) {
			this.posicion = posicion;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ListadoPasajeros.this);
			pDialog.setMessage("Espere un momento...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {

			Cursor CursorUsuario = Manager.BuscarUsuario(posicion);

			if (CursorUsuario.moveToFirst()) {
				do {

					this.nombre = CursorUsuario.getString(1);
					this.RutFuncionarioReservas = CursorUsuario.getString(2);
					this.ApellidoFuncionarioReservas = CursorUsuario
							.getString(3);
					this.NombreEmpresaReservas = CursorUsuario.getString(5);
					this.TipoReserva = CursorUsuario.getString(10);
					this.Autorizado = CursorUsuario.getString(9);
				} while (CursorUsuario.moveToNext());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (pDialog.isShowing())
				pDialog.dismiss();

			Intent a = new Intent(ListadoPasajeros.this, MostrarPasajeros.class);
			a.putExtra("NombreFuncionarioReservas", nombre);
			a.putExtra("rut", RutFuncionarioReservas);
			a.putExtra("ApellidoFuncionarioReservas",
					ApellidoFuncionarioReservas);

			a.putExtra("NombreEmpresaReservas", NombreEmpresaReservas);
			a.putExtra("tiporeserva", TipoReserva);
			a.putExtra("Autorizado", Autorizado);
			startActivity(a);
		}
	}

	public void vibrar() {
		Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		v.vibrate(80);
	}

	public String getFecha() {
		String FechaHora = getDateTime();
		String Fecha = FechaHora.substring(0, 10);
		String Hora = FechaHora.substring(11, FechaHora.length());
		Fecha = Fecha.replace("/", "");
		return Fecha;
	}

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		return dateFormat.format(date);
	}
}
