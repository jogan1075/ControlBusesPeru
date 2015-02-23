package com.webcontrol.controlbus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import ClasesYMetodos.CustomDigitalClock;
import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import ClasesYMetodos.NdefReaderTask;
import Hilos.HiloReservarPasaje;
import Hilos.HiloSincronizacionPersonas;
import Hilos.HiloSincronizacionProgamacionBus;
import Hilos.HiloSincronizacionReservas;
import Hilos.HiloSincronizacionVehiculos;
import Hilos.HiloSincronizarReservasLocales;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class IngresoPasajeros extends Activity {
	public static final String MIME_TEXT_PLAIN = "text/plain";
	public static final String TAG = "NfcDemo";

	private NfcAdapter mNfcAdapter;

	TextView uid;
	String UIDNFC = "";
	MediaPlayer mpAviso;
	MediaPlayer mpError;
	Bundle bundle;
	ImageButton btnCamara, btnManual;
	Metodos metodos;
	String placa = "", idprogramacion = "";
	TextView txtregistrados, txtreservados, txtplacapatente, txtfecha,
			txtvalidacionreserva;
	Button btnderecha;
	private DataBaseManagerWS Manager;
	CustomDigitalClock reloj;

	String resp = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_ingreso_pasajeros);
		// uid = (TextView) findViewById(R.id.textView1);
		// bundle = getIntent().getExtras();

		mpAviso = MediaPlayer.create(getApplicationContext(), R.raw.ok);
		mpError = MediaPlayer.create(getApplicationContext(), R.raw.incorrecto);

		txtregistrados = (TextView) findViewById(R.id.textView3);
		txtreservados = (TextView) findViewById(R.id.textView5);

		// btnderecha= (Button)findViewById(R.id.btnderecha);
		txtplacapatente = (TextView) findViewById(R.id.textView4);
		txtfecha = (TextView) findViewById(R.id.textView6);
		txtvalidacionreserva = (TextView) findViewById(R.id.txtvalidacionReserva);

		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		metodos = new Metodos();
		Manager = new DataBaseManagerWS(this);
		Bundle datos = getIntent().getExtras();

		if (datos != null) {

			placa = datos.getString("placa");
			idprogramacion = datos.getString("idprogramacion");
			txtplacapatente.setText(placa);
		}
		if ((placa != null && placa != "")
				&& (idprogramacion != null && idprogramacion != "")) {

			Cursor c = Manager.CursorBundle();
			if (!c.moveToFirst()) {
				Manager.InsertarBundle(idprogramacion, placa);
			} else {
				Manager.ActualizarBundle(idprogramacion, placa);
			}

		} else {
			Cursor cursor = Manager.CursorBundle();
			if (cursor.moveToFirst()) {
				placa = cursor.getString(2);
				idprogramacion = cursor.getString(1);
			}
		}

		reloj = (CustomDigitalClock) findViewById(R.id.digitalClock1);
		reloj.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}

		});

		/***************************** Boton Derecha **********************************************/
		TextView btn = (TextView) this.findViewById(R.id.textView5);
		final String uno = "SI";

		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (uno.equals(resp)) {
					Bundle bundle = new Bundle();

					String btn = "4";
					bundle.putString("btn1", btn);
					Intent intent = new Intent(IngresoPasajeros.this,
							ListadoPasajeros.class);
					intent.putExtras(bundle);
					startActivity(intent);
				} else {

					metodos.devolverVibrador(getApplicationContext(), 80);
					Bundle bundle = new Bundle();

					String btn = "1";
					bundle.putString("btn1", btn);
					Intent intent = new Intent(IngresoPasajeros.this,
							ListadoPasajeros.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
		/***************************** Boton Izquierda **********************************************/

		final TextView btn1 = (TextView) this.findViewById(R.id.textView3);
		final String Dos = "SI";
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Dos.equals(resp)) {

					Bundle bundle = new Bundle();

					String btn = "3";
					bundle.putString("btn1", btn);
					Intent intent = new Intent(IngresoPasajeros.this,
							ListadoPasajeros.class);
					intent.putExtras(bundle);
					startActivity(intent);

				} else {

					metodos.devolverVibrador(getApplicationContext(), 80);
					Bundle bundle = new Bundle();

					String btn = "2";
					bundle.putString("btn1", btn);
					Intent intent = new Intent(IngresoPasajeros.this,
							ListadoPasajeros.class);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});

		txtfecha.setText(metodos.devolverFecha());

		if (mNfcAdapter == null) {
			// Stop here, we definitely need NFC
			// metodos.NFCError(this, "Dispositivo sin NFC",
			// "No cuenta Con NFC", "cerrar");
			metodos.NFCError(this, "Dispositivo sin NFC", "", "cerrar",
					mNfcAdapter, this);
			return;

		}

		Button opciones = (Button) findViewById(R.id.btnopciones);
		opciones.setEnabled(false);

		new HiloReloj(this, this, opciones, idprogramacion).execute();

		if (!mNfcAdapter.isEnabled()) {
			// NFCError(this, "NFC Desabilitado",
			// "Haga Click para Habilitarlo","habilitar");
			metodos.NFCError(this, "NFC Desabilitado",
					"Haga Click para Habilitarlo", "habilitar", mNfcAdapter,
					this);
		}
		if (idprogramacion != null) {
			handleIntent(getIntent());

			Cursor cursorRegistroSinReservas = Manager
					.CursorBRegitroSinReserva();
			if (cursorRegistroSinReservas.moveToFirst()) {
				resp = cursorRegistroSinReservas.getString(1);
				if (resp.equals("SI")) {
					txtvalidacionreserva.setText("Validación Reserva MOVIL");
					CargarListaReservasMovil();
				} else {
					txtvalidacionreserva.setText("Validación Reserva WEB");
					cargarListaReservas();
				}
			} else {
				txtvalidacionreserva.setText("Validación Reserva WEB");
				cargarListaReservas();
			}

			
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
		
			
			
			
			
		} else {
			startActivity(new Intent(IngresoPasajeros.this, MenuPrincipal.class));
		}
	}

	// public void Lista1(){
	// metodos.devolverVibrador(this, 80);
	// startActivity(new Intent(this, ListadoPasajeros.class));
	// }

	public String HoraEspera(String horabd, String minutosEspera) {

		String[] hr = horabd.split(":");
		String minutos = hr[1];
		int hora = 0;
		if (minutos.equals("00")) {
			minutos = "60";
		}

		int min = Integer.parseInt(minutos);

		min = min - Integer.parseInt(minutosEspera);

		if (min < 60) {

			hora = Integer.parseInt(hr[0]) - 1;
		}
		String horaFinal = String.valueOf(hora) + ":" + String.valueOf(min);

		return horaFinal;

	}

	private void CargarListaReservasMovil() {
		Cursor cursorbus = Manager.BuscarBusPatentefECHA(placa, getFecha()
				.replace("/", ""));

		if (cursorbus.moveToFirst()) {

			int pendiente = Manager.BuscarReservasMovilPendites(idprogramacion);
			int autorizados = Manager.BuscarAutorizadosMovil(idprogramacion);
			txtregistrados.setText("Autorizados: " + autorizados);
			// btnderecha.setText("Pendientes: " + pendiente);
			txtreservados.setText("Pendientes: " + pendiente);
		}
	}

	private void cargarListaReservas() {
		// TODO Auto-generated method stub
		Cursor cursorbus = Manager.BuscarBusPatentefECHA(placa, getFecha()
				.replace("/", ""));

		if (cursorbus.moveToFirst()) {

			String idprogramacion = cursorbus.getString(0);

			int Reservaciones = Manager.BuscarReservaciones(idprogramacion);
			int Registrados = Manager.BuscarRegistrados(idprogramacion);

			txtregistrados.setText("Reservas: " + Registrados);
			// btnderecha.setText("Registrados: " + Reservaciones);
			txtreservados.setText("Registrados: " + Reservaciones);

		}

	}

	public String getHora() {
		String FechaHora = getDateTime();
		String Fecha = FechaHora.substring(0, 10);
		String Hora = FechaHora.substring(11, FechaHora.length());
		Hora = Hora.substring(0, 5);
		return Hora;
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

	public void Volver(View v) {
		metodos.devolverVibrador(this, 80);

		Cursor cursor = Manager.CursorBRegitroSinReserva();
		if (cursor.moveToFirst()) {

			if (cursor.getString(1).equals("SI")
					&& cursor.getString(2).equals(idprogramacion)) {
				Manager.ActualizarRegitroSinReserva("NO", idprogramacion);
			}
		}
		finish();
		startActivity(new Intent(getApplicationContext(), MenuBus.class));
	}

	public void Opc(View v) {
		metodos.devolverVibrador(this, 80);
		Intent i = new Intent(getApplicationContext(), Opciones.class);
		i.putExtra("placa", placa);
		i.putExtra("idprogramacion", idprogramacion);
		startActivity(i);
		finish();
	}

	public void Camara(View v) {
		metodos.devolverVibrador(this, 80);
		startActivity(new Intent(getApplicationContext(), Escanear.class));
	}

	public void IngresarManual(View v) {
		// Vibrador.vibrate(80);
		metodos.devolverVibrador(this, 80);
		Intent i = new Intent(getApplicationContext(), IngresoManual.class);
		i.putExtra("IdProgramacionBusseleccionado", idprogramacion);
		startActivity(i);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		metodos.devolverVibrador(this, 80);
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			finish();
			startActivity(new Intent(getApplicationContext(), MenuBus.class));
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onResume() {
		super.onResume();

		setupForegroundDispatch(this, mNfcAdapter);
	}

	@Override
	protected void onPause() {

		stopForegroundDispatch(this, mNfcAdapter);

		super.onPause();
	}

	@Override
	protected void onNewIntent(Intent intent) {

		handleIntent(intent);

	}

	private void handleIntent(Intent intent) {

		String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

			String type = intent.getType();
			if (MIME_TEXT_PLAIN.equals(type)) {

				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				new NdefReaderTask().execute(tag);

			} else {
				Log.d(TAG, "Wrong mime type: " + type);
			}
		} else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

			// In case we would still use the Tech Discovered Intent

			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			String[] techList = tag.getTechList();
			String searchedTech = Ndef.class.getName();

			for (String tech : techList) {
				if (searchedTech.equals(tech)) {
					new NdefReaderTask().execute(tag);
					break;
				}
			}
		} else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
			byte[] byte_id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);

			Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

			UIDNFC = getHexString(tag.getId()).toString();

			Intent intentNfc = new Intent(getApplicationContext(),
					ReservaBus.class);
			intentNfc.putExtra("data", UIDNFC);
			intentNfc.putExtra("tipodata", "UID");
			intentNfc.putExtra("fechaactual", getFecha());
			intentNfc.putExtra("IdProgramacionBusseleccionado", idprogramacion);

			Cursor cursor = Manager.CursorBRegitroSinReserva();
			if (cursor.moveToFirst()) {
				if (cursor.getString(1).equals("SI")) {
					intentNfc.putExtra("RegistroSinReserva", "SI");
				}
			}
			startActivity(intentNfc);

		}

	}

	public static String getHexString(byte[] b) {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}

		int largo = (result.length()) / 2;
		String resultado = "";
		for (int j = largo; j > 0; j--) {
			if (j != largo) {
				resultado = resultado
						+ result.substring(((j * 2) - 2), ((j * 2)));
			}

		}
		return resultado.toUpperCase();
	}

	public static void setupForegroundDispatch(final Activity activity,
			NfcAdapter adapter) {
		final Intent intent = new Intent(activity.getApplicationContext(),
				activity.getClass());
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		final PendingIntent pendingIntent = PendingIntent.getActivity(
				activity.getApplicationContext(), 0, intent, 0);

		IntentFilter[] filters = new IntentFilter[1];
		String[][] techList = new String[][] {};

		// Notice that this is the same filter as in our manifest.
		filters[0] = new IntentFilter();
		filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		filters[0].addCategory(Intent.CATEGORY_DEFAULT);
		try {
			filters[0].addDataType(MIME_TEXT_PLAIN);
		} catch (MalformedMimeTypeException e) {
			throw new RuntimeException("Check your mime type.");
		}

		adapter.enableForegroundDispatch(activity, pendingIntent, filters,
				techList);
	}

	public static void stopForegroundDispatch(final Activity activity,
			NfcAdapter adapter) {
		adapter.disableForegroundDispatch(activity);
	}

	
	
	public class HiloReloj extends AsyncTask<String, Void, String> {

		String horaBD = "";
		String horaActual = "";
		DataBaseManagerWS Manager;
		Context context;
		Activity activity;
		Button Opciones;
		String idprogramacion = "";
		Boolean resp = false;
		String HoraFinalBD = "";
		String HoraEspera = "";

		public HiloReloj(Context contexto, Activity actividad, Button btn,
				String idprogramacion) {
			this.context = contexto;
			this.activity = actividad;
			this.Opciones = btn;
			Manager = new DataBaseManagerWS(context);
			this.idprogramacion = idprogramacion;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			Cursor horayfecha = Manager.DevolerFechayHora(idprogramacion);
			if (horayfecha.moveToFirst()) {

				horaBD = horayfecha.getString(2);

			}

			Cursor c = Manager.CursorVALORMINUTOS();
			if (c.moveToFirst()) {
				HoraEspera = c.getString(1);
			}else{
				Manager.InsertarVALORMINUTOS("5");
				HoraEspera = "5";
			}

			horaActual = getHora();

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			HoraFinalBD = HoraEspera(horaBD, HoraEspera);

			int hf = 0;
			int ha = 0;

			hf = Integer.parseInt(HoraFinalBD.replace(":", ""));
			ha = Integer.parseInt(horaActual.replace(":", ""));

			if (hf == ha) {
				resp = true;

			} else if (ha > hf) {
				resp = true;
			} else {
				resp = false;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (resp) {
				Opciones.setEnabled(true);
				Opciones.setTextColor(Color.WHITE);
			} else {
				Opciones.setEnabled(false);
				Opciones.setTextColor(Color.GRAY);
				new HiloReloj(context, activity, Opciones, idprogramacion)
						.execute();
			}
		}

	}

}
