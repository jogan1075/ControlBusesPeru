package com.webcontrol.controlbus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import Hilos.HiloDatosBus;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Sincronizacion_buses extends Activity {

	Metodos metodos = new Metodos();
	Vibrator Vibrador;
	Button regresar, sinc;

	String METHOD_NAME, SOAP_ACTION, NAMESPACE, URL, UsuarioWs, ContraseniaWs,
			Autentificacion, EstadoSync, TipoSync;
	Context c;
	private DataBaseManagerWS Manager;
	ProgressBar progress;

	TextView TxtSincronizacion;

	int Progreso = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_sincronizacion_buses);

		Manager = new DataBaseManagerWS(this);
		Vibrador = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);

		TxtSincronizacion = (TextView) findViewById(R.id.textViewSincronizacion);

		Cursor CursorSincronizacion = Manager.CursorSincronizacion();

		regresar = (Button) findViewById(R.id.buttonRegresar);
		sinc = (Button) findViewById(R.id.btnsincronizar);

		if (!(CursorSincronizacion.moveToFirst())) {

			Manager.InsertarDatosSincronizacion("0", "0", 0, "OFF", "INICIAL");

			EstadoSync = "OFF";
			TipoSync = "INICIAL";

		} else {
			do {

				EstadoSync = CursorSincronizacion.getString(2);
				TipoSync = CursorSincronizacion.getString(3);

			} while (CursorSincronizacion.moveToNext());
		}

		Cursor CursorSincronizacionReservas = Manager
				.CursorSincronizacionReservas();

		if (!(CursorSincronizacionReservas.moveToFirst())) {

			Manager.InsertarDatosSincronizacionReserva("0", "0", 0, "OFF",
					"INICIAL");

			EstadoSync = "OFF";
			TipoSync = "INICIAL";

		} else {
			do {

				EstadoSync = CursorSincronizacionReservas.getString(2);
				TipoSync = CursorSincronizacionReservas.getString(3);

			} while (CursorSincronizacionReservas.moveToNext());
		}

		Cursor CursorWs = Manager.CursorConfig();
		if (CursorWs.moveToFirst()) {
			do {
				NAMESPACE = CursorWs.getString(0);
				URL = CursorWs.getString(1);
				UsuarioWs = CursorWs.getString(2); // desarrollo
				ContraseniaWs = CursorWs.getString(3); // Desa1.
				Autentificacion = CursorWs.getString(4);
			} while (CursorWs.moveToNext());
		}

		Cursor CursorSincronizacionPersona = Manager
				.CursorSincronizacionPersona();

		if (!(CursorSincronizacionPersona.moveToFirst())) {
			Manager.InsertarDatosSincronizacionPERSONAS("0", "1", 0, "OFF",
					"INICIAL");

			EstadoSync = "OFF";
			TipoSync = "INICIAL";
		} else {
			do {
				EstadoSync = CursorSincronizacionPersona.getString(3);
				TipoSync = CursorSincronizacionPersona.getString(4);

			} while (CursorSincronizacionPersona.moveToNext());
		}
		Cursor CursorSincronizacionVehiculo = Manager
				.CursorSincronizacionVehiculo();

		if (!(CursorSincronizacionVehiculo.moveToFirst())) {
			Manager.InsertarDatosSincronizacionVEHICULOS("0", "1", 0, "OFF",
					"INICIAL");

			EstadoSync = "OFF";
			TipoSync = "INICIAL";
		} else {
			do {
				EstadoSync = CursorSincronizacionVehiculo.getString(3);
				TipoSync = CursorSincronizacionVehiculo.getString(4);

			} while (CursorSincronizacionVehiculo.moveToNext());
		}
		
		Cursor habilitarsinc = Manager.CursorHABILITAR_SINCRONIZACIONAUTOMATICA();
		if(habilitarsinc.moveToFirst()){
			Manager.ActualizarHABILITAR_SINCRONIZACIONAUTOMATICA("NO");
		}else{
			Manager.InsertarHABILITAR_SINCRONIZACIONAUTOMATICA("NO");
		}

		sinc.setTextColor(Color.WHITE);
		sinc.setEnabled(true);
		regresar.setTextColor(Color.WHITE);
		regresar.setEnabled(true);

	}

	public void Regresar(View v) {
		metodos.devolverVibrador(this, 80);
		Intent i = new Intent(this, MenuPrincipal.class);
		finish();
		startActivity(i);
	}

	public void btnSincronizar(View v) {

		Vibrador.vibrate(80);
		Button boton = (Button) v;
		new HiloSincronizacionInicial().execute();
		// new HiloSincronizacionPersonasInicial2().execute();

		sinc.setTextColor(Color.GRAY);
		sinc.setEnabled(false);
		regresar.setTextColor(Color.GRAY);
		regresar.setEnabled(false);

	}

	private class HiloSincronizacionInicial extends AsyncTask<Void, Void, Void> {

		String IdProgramacion = "";
		String Patente = "";
		String Origen = "";
		String Destino = "";
		String Duracion = "";
		String Fecha = "";
		String Hora = "";
		String Activo = "";
		String Costo = "", Conductor1 = "", Conductor2 = "";
		String IdSync = "";
		String Restantes;
		String Capacidad = "";
		int datosrestantes;
		int ContadorErrores;

		String Respuesta;
		JSONObject RespuestaJson;
		Exception Exception = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// super.onPreExecute();

			Cursor CursorSincronizacion = Manager.CursorSincronizacion();

			if (CursorSincronizacion.moveToFirst()) {
				do {
					IdSync = CursorSincronizacion.getString(0);
					// Toast.makeText(Sincronizacion.this, IdSync,
					// Toast.LENGTH_SHORT).show();
					ContadorErrores = CursorSincronizacion.getInt(1);

				} while (CursorSincronizacion.moveToNext());
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			METHOD_NAME = "WSSincronizarProgramacion";
			SOAP_ACTION = NAMESPACE + "WSSincronizarProgramacion";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("idsincronizacion", IdSync);
			request.addProperty("imei", getIMEI());

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11); // la version de WS 1.1
			envelope.dotNet = true; // estamos utilizando .net
			envelope.setOutputSoapObject(request);
			HttpTransportSE transporte = new HttpTransportSE(URL);
			try {
				if (Autentificacion.equals("SI")) {
					// *****
					List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
					String Conexion = UsuarioWs + ":" + ContraseniaWs;
					headerList.add(new HeaderProperty("Authorization", "Basic "
							+ org.kobjects.base64.Base64.encode(Conexion
									.getBytes())));
					transporte.call(SOAP_ACTION, envelope, headerList);
					// /*****
				} else {
					transporte.call(SOAP_ACTION, envelope);
				}

				SoapPrimitive resultado = (SoapPrimitive) envelope
						.getResponse();

				Respuesta = resultado.toString();
				RespuestaJson = new JSONObject(Respuesta);
				IdProgramacion = RespuestaJson.getString("IdProgramacion");
				Patente = RespuestaJson.getString("Patente");
				Origen = RespuestaJson.getString("Origen");
				Destino = RespuestaJson.getString("Destino");
				Duracion = RespuestaJson.getString("Duracion");
				Fecha = RespuestaJson.getString("Fecha");
				Hora = RespuestaJson.getString("Hora");
				Activo = RespuestaJson.getString("Activo");
				Costo = RespuestaJson.getString("Costo");
				Conductor1 = RespuestaJson.getString("Conductor1");
				Conductor2 = RespuestaJson.getString("Conductor2");
				IdSync = RespuestaJson.getString("IdSync");
				Capacidad = RespuestaJson.getString("Capacidad");
				Restantes = RespuestaJson.getString("Restantes");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Exception = e;
				// e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				Exception = e;
				// e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Exception = e;
				//
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

		if(!Respuesta.equalsIgnoreCase("Sin Acceso")){
				
			
			if (!Restantes.equals("null")) {

				datosrestantes = (Integer.parseInt(Restantes));

				if (Exception == null) {
					TxtSincronizacion
							.setText("Sincronizando Datos de Bus.... Restan "
									+ datosrestantes + " Registros");

					if (Integer.parseInt(IdSync) != 0) {
						Manager.ActualizarSincronizacion(Patente, IdSync, 0,
								"ON", "INICIAL");

						Cursor CursorBusPorPatente = Manager
								.BuscarBusIdprogramacion(IdProgramacion);
						if (CursorBusPorPatente.moveToFirst()) {

							// int id =
							// Manager.devolveridDatoBus(IdProgramacion);

							Manager.ActualizarDatosBus(IdProgramacion, Patente,
									Origen, Destino, Duracion, Fecha, Hora,
									Activo, Costo, Conductor1, Conductor2,
									IdSync);

						} else {
							Manager.InsertarDatosBus(IdProgramacion, Patente,
									Origen, Destino, Duracion, Fecha, Hora,
									Activo, Costo, Conductor1, Conductor2,
									IdSync);
						}

					} else {
						Manager.ActualizarSincronizacion(Patente, IdSync, 0,
								"OFF", "INICIAL");
						// if (Progreso == -1) {
						// progress.setMax(datosrestantes);
						// progress.setProgress(0);
						// progress.setVisibility(View.VISIBLE);
						// Progreso = datosrestantes;
						// } else {
						// progress.incrementProgressBy(1);
						// }

					}
					new HiloSincronizacionInicial().execute();
				} else {
					if (ContadorErrores >= 5) {

						Manager.ActualizarContadorSincronizacion(0, "OFF",
								"INICIAL");

					} else {

						Manager.ActualizarContadorSincronizacion(
								(ContadorErrores + 1), "ON", "INICIAL");

						new HiloSincronizacionInicial().execute();
					}
				}
			} else {

				// if (Progreso == -1) {
				// progress.setMax(datosrestantes);
				// progress.setProgress(0);
				// progress.setVisibility(View.VISIBLE);
				// Progreso = datosrestantes;
				// } else {
				// progress.incrementProgressBy(datosrestantes);
				// }

				Toast.makeText(getApplicationContext(),
						"Sincronización Datos del Bus Finalizada Con Exito!",
						Toast.LENGTH_SHORT).show();

				new HiloSincronizacionReservas().execute();

			}
		}else{
			
			startActivity(new Intent(Sincronizacion_buses.this, MenuPrincipal.class));
			finish();
			Toast.makeText(getApplicationContext(),
					"Dispositivo sin Autorizacion!",
					Toast.LENGTH_SHORT).show();
		}

		}

	}

	private class HiloSincronizacionReservas extends
			AsyncTask<Void, Void, Void> {

		String IdProgramacion = "";
		String rutfuncionario = "";
		String nombreFuncionario = "";
		String apellidoFuncionario = "";
		String Rutempresa = "";
		String nombreEmpresa = "";
		String autorizacionacceso = "";
		String uid = "";
		String estadouid = "";
		String Asiento = "";
		String utilizo = "";
		String Idsync = "";
		String Restantes = "";
		String Autorizado = "";
		String TipoReserva = "";

		int datosrestantes;
		int ContadorErrores;

		String Respuesta;
		JSONObject RespuestaJson;
		Exception Exception = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// super.onPreExecute();

			Cursor CursorSincronizacion = Manager
					.CursorSincronizacionReservas();

			if (CursorSincronizacion.moveToFirst()) {
				do {
					Idsync = CursorSincronizacion.getString(0);
					// Toast.makeText(Sincronizacion.this, IdSync,
					// Toast.LENGTH_SHORT).show();
					ContadorErrores = CursorSincronizacion.getInt(1);

				} while (CursorSincronizacion.moveToNext());
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			METHOD_NAME = "WSSincronizarReservas";
			SOAP_ACTION = NAMESPACE + "WSSincronizarReservas";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("idsincronizacion", Idsync);
			request.addProperty("imei", getIMEI());

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER12); // la version de WS 1.1
			envelope.dotNet = true; // estamos utilizando .net
			envelope.setOutputSoapObject(request);
			HttpTransportSE transporte = new HttpTransportSE(URL, 60000);

			try {
				if (Autentificacion.equals("SI")) {
					// *****
					List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
					String Conexion = UsuarioWs + ":" + ContraseniaWs;
					headerList.add(new HeaderProperty("Authorization", "Basic "
							+ org.kobjects.base64.Base64.encode(Conexion
									.getBytes())));
					transporte.call(SOAP_ACTION, envelope, headerList);
					// /*****
				} else {
					transporte.call(SOAP_ACTION, envelope);
					// transporte.getServiceConnection().disconnect();
				}

				SoapPrimitive resultado = (SoapPrimitive) envelope
						.getResponse();

				Respuesta = resultado.toString();
				RespuestaJson = new JSONObject(Respuesta);

				IdProgramacion = RespuestaJson.getString("IdProgramacion");
				rutfuncionario = RespuestaJson.getString("IdFuncionario");
				nombreFuncionario = RespuestaJson
						.getString("NombreFuncionario");
				apellidoFuncionario = RespuestaJson
						.getString("ApellidoFuncionario");
				Rutempresa = RespuestaJson.getString("Empresa");
				nombreEmpresa = RespuestaJson.getString("EmpresaFuncionario");
				autorizacionacceso = RespuestaJson
						.getString("AutorizacionAcceso");
				uid = RespuestaJson.getString("UID");
				estadouid = RespuestaJson.getString("EstadoUID");
				Asiento = RespuestaJson.getString("Asiento");
				utilizo = RespuestaJson.getString("Utilizo");
				Idsync = RespuestaJson.getString("IdSync");
				Autorizado = RespuestaJson.getString("Autorizado");
				TipoReserva = RespuestaJson.getString("TipoReserva");
				Restantes = RespuestaJson.getString("Restantes");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Exception = e;
				// e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				Exception = e;
				// e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Exception = e;
				//
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			if (!Restantes.equals("null")) {

				if (Exception == null) {
					TxtSincronizacion
							.setText("Sincronizando Reservas.... Restan "
									+ Restantes + " Registros");

					if (Integer.parseInt(Idsync) != 0) {
						Manager.ActualizarSincronizacionReservas(
								rutfuncionario, Idsync, 0, "ON", "INICIAL");

						Boolean resultado = Manager
								.BuscarReservaIdProgramacion(IdProgramacion,
										rutfuncionario);
						if (resultado.equals(true)) {

							int id = Manager
									.devolverIdReservasRut(rutfuncionario);

							Manager.ActualizarReservas(String.valueOf(id),
									IdProgramacion, rutfuncionario,
									nombreFuncionario, apellidoFuncionario,
									Rutempresa, nombreEmpresa,
									autorizacionacceso, uid, estadouid,
									Asiento, utilizo, Idsync, Restantes, "NO",
									Autorizado, TipoReserva);

						} else {

							Manager.InsertarReservas(IdProgramacion,
									rutfuncionario, nombreFuncionario,
									apellidoFuncionario, Rutempresa,
									nombreEmpresa, autorizacionacceso, uid,
									estadouid, Asiento, utilizo, Idsync,
									Restantes, "NO", Autorizado, TipoReserva);

						}

					}
					// if (Progreso == -1) {
					// progress.setMax(datosrestantes);
					// progress.setProgress(0);
					// progress.setVisibility(View.VISIBLE);
					// Progreso = datosrestantes;
					// } else {
					// progress.incrementProgressBy(1);
					// }
					new HiloSincronizacionReservas().execute();
				} else {
					if (ContadorErrores >= 5) {

						Manager.ActualizarContadorSincronizacionReserva(0,
								"OFF", "INICIAL");

					} else {

						Manager.ActualizarContadorSincronizacionReserva(
								(ContadorErrores + 1), "ON", "INICIAL");

						new HiloSincronizacionReservas().execute();
					}
				}
			} else {

				Toast.makeText(getApplicationContext(),
						"Sincronización Reservas BUS Finalizada Con Exito!",
						Toast.LENGTH_SHORT).show();

				new HiloSincronizacionPersonasInicial().execute();

			}

		}

	}

	private class HiloSincronizacionPersonasInicial extends
			AsyncTask<Void, Void, Void> {

		String UID = "";
		String EstadoUID = "";
		String IdFuncionario = "";
		String Nombres = "";
		String Apellidos = "";
		String NombreEmpresa = "";
		String IdEmpresa = "";
		String Ost = "", CCosto = "", TipoPase = "";
		String Autorizacion = "";
		String FechaConsulta = "";
		String AutorizacionConductor = "";
		String Estado = "";
		String Mensaje = "";
		String Imagen = "";
		int ContadorMax = 0;
		int ContadorErrores;
		String Restantes;
		String Respuesta;
		JSONObject RespuestaJson;
		Exception Exception = null;
		String IdSync = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// super.onPreExecute();

			Cursor CursorSincronizacion = Manager.CursorSincronizacionPersona();

			if (CursorSincronizacion.moveToFirst()) {
				do {
					IdSync = CursorSincronizacion.getString(1);
					// Toast.makeText(Sincronizacion.this, IdSync,
					// Toast.LENGTH_SHORT).show();
					ContadorErrores = CursorSincronizacion.getInt(2);

				} while (CursorSincronizacion.moveToNext());
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			METHOD_NAME = "WSSincronizaInicial";
			SOAP_ACTION = NAMESPACE + "WSSincronizaInicial";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("idsincronizacion", IdSync);
			request.addProperty("imei", getIMEI());

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11); // la version de WS 1.1
			envelope.dotNet = true; // estamos utilizando .net
			envelope.setOutputSoapObject(request);
			HttpTransportSE transporte = new HttpTransportSE(URL);
			try {
				if (Autentificacion.equals("SI")) {
					// *****
					List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
					String Conexion = UsuarioWs + ":" + ContraseniaWs;
					headerList.add(new HeaderProperty("Authorization", "Basic "
							+ org.kobjects.base64.Base64.encode(Conexion
									.getBytes())));
					transporte.call(SOAP_ACTION, envelope, headerList);
					// /*****
				} else {
					transporte.call(SOAP_ACTION, envelope);
				}

				SoapPrimitive resultado = (SoapPrimitive) envelope
						.getResponse();

				Respuesta = resultado.toString();
				RespuestaJson = new JSONObject(Respuesta);

				UID = RespuestaJson.getString("UID");
				EstadoUID = RespuestaJson.getString("EstadoUID");
				IdFuncionario = RespuestaJson.getString("IdFuncionario");
				Nombres = RespuestaJson.getString("Nombres");
				Apellidos = RespuestaJson.getString("Apellidos");
				NombreEmpresa = RespuestaJson.getString("NombreEmpresa");
				IdEmpresa = RespuestaJson.getString("IdEmpresa");
				Autorizacion = RespuestaJson.getString("Autorizacion");
				FechaConsulta = RespuestaJson.getString("FechaConsulta");
				AutorizacionConductor = RespuestaJson
						.getString("AutorizacionConductor");
				Estado = RespuestaJson.getString("Estado");
				Mensaje = RespuestaJson.getString("Mensaje");
				Imagen = RespuestaJson.getString("Imagen");
				Ost = RespuestaJson.getString("Ost");
				TipoPase = RespuestaJson.getString("TipoPase");
				CCosto = RespuestaJson.getString("CCosto");
				// ContadorMax = RespuestaJson.getInt("max");
				IdSync = RespuestaJson.getString("idsincronizacion");
				Restantes = RespuestaJson.getString("restantes");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Exception = e;
				// e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				Exception = e;
				// e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Exception = e;
				//
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			if (!Restantes.equals("null")) {

				int datosrestantes = (Integer.parseInt(Restantes));

				if (Exception == null) {
					TxtSincronizacion
							.setText("Sincronizando Personas.... Restan "
									+ datosrestantes + " Registros");

					if (Integer.parseInt(IdSync) != 0) {

						Manager.ActualizarSincronizacionPersona(IdFuncionario,
								IdSync, 0, "ON", "INICIAL");

						Cursor CursorFuncionario = Manager
								.BuscarFuncionarioId(IdFuncionario);
						if (CursorFuncionario.moveToFirst()) {
							int id = Manager
									.devolveridFuncionario(IdFuncionario);
							Manager.ActualizarDataFuncionario(UID, EstadoUID,
									IdFuncionario, Nombres, Apellidos,
									NombreEmpresa, IdEmpresa, Ost, CCosto,
									TipoPase, Imagen, Autorizacion,
									AutorizacionConductor, FechaConsulta, id);
						} else {
							Manager.InsertarDatosFuncionario(UID, EstadoUID,
									IdFuncionario, Nombres, Apellidos,
									NombreEmpresa, IdEmpresa, Ost, CCosto,
									TipoPase, Imagen, Autorizacion,
									AutorizacionConductor, FechaConsulta);
						}

					} else {
						Manager.ActualizarSincronizacionPersona(IdFuncionario,
								IdSync, 0, "OFF", "INICIAL");
					}

					new HiloSincronizacionPersonasInicial().execute();
				} else {
					if (ContadorErrores >= 5) {

						// Manager.InsertarDatosLog("Error: Sincronización Inicial Personas ",
						// getFecha(), getHora());
						Manager.ActualizarContadorSincronizacion(0, "OFF",
								"INICIAL");
						// BtnSyncInicial.setTextColor(Color.WHITE);
						// BtnSyncInicial.setEnabled(true);

						// BtnRegresar.setTextColor(Color.WHITE);
						// BtnRegresar.setEnabled(true);

						// progress.setVisibility(View.INVISIBLE);
						TxtSincronizacion
								.setText("Sincronizando Personas.... Restan "
										+ datosrestantes + " Registros");
						// Toast.makeText(getApplicationContext(),Toast2 +
						// datosrestantes + Toast3,Toast.LENGTH_LONG).show();

					} else {

						Manager.ActualizarContadorSincronizacion(
								(ContadorErrores + 1), "ON", "INICIAL");

						new HiloSincronizacionPersonasInicial().execute();
					}
				}
			} else {

				Toast.makeText(
						getApplicationContext(),
						"Fin Sincronización Personas \nSincronización Realizada Con Exito!",
						Toast.LENGTH_SHORT).show();

				new HiloSincronizarVehiculos().execute();

			}

		}

	}

	private class HiloSincronizarVehiculos extends AsyncTask<Void, Void, Void> {
		String UID = "";
		String EstadoUID = "";
		String IdVehiculo = "";
		String Marca = "";
		String Modelo = "";
		String AnioVehiculo = "";
		String TipoVehiculo = "";
		String AutorizacionVehiculo = "";
		String FechaConsulta = "";
		String ROLEmpresa = "";
		String NombreEmpresa = "";
		String Fecha = "";
		String Estado = "";
		String Mensaje = "";
		int ContadorMax = 0;
		String Restantes;
		String IdSync = "";
		String Capacidad="";

		int ContadorErrores;
		String _Respuesta;
		JSONObject _RespuestaJson;
		Exception _Exception = null;

		@Override
		protected void onPreExecute() {
			Cursor CursorSincronizacion = Manager
					.CursorSincronizacionVehiculo();

			if (CursorSincronizacion.moveToFirst()) {
				do {
					IdSync = CursorSincronizacion.getString(1);
					ContadorErrores = CursorSincronizacion.getInt(2);

				} while (CursorSincronizacion.moveToNext());
			}
		}

		@Override
		protected Void doInBackground(Void... params) {

			METHOD_NAME = "WSSincronizarVehiculosInicial";
			SOAP_ACTION = NAMESPACE + "WSSincronizarVehiculosInicial";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("IdSincronizacion", IdSync);
			request.addProperty("imei", getIMEI());

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11); // la version de WS 1.1
			envelope.dotNet = true; // estamos utilizando .net
			envelope.setOutputSoapObject(request);
			HttpTransportSE transporte = new HttpTransportSE(URL);

			try {
				if (Autentificacion.equals("SI")) {
					// *****
					List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
					String Conexion = UsuarioWs + ":" + ContraseniaWs;
					headerList.add(new HeaderProperty("Authorization", "Basic "
							+ org.kobjects.base64.Base64.encode(Conexion
									.getBytes())));
					transporte.call(SOAP_ACTION, envelope, headerList);
					// /*****
				} else {
					transporte.call(SOAP_ACTION, envelope);
				}

				SoapPrimitive resultado = (SoapPrimitive) envelope
						.getResponse();

				_Respuesta = resultado.toString();
				_RespuestaJson = new JSONObject(_Respuesta);

				UID = _RespuestaJson.getString("UID");
				EstadoUID = _RespuestaJson.getString("EstadoUID");
				IdVehiculo = _RespuestaJson.getString("IdVehiculo");
				Marca = _RespuestaJson.getString("Marca");
				Modelo = _RespuestaJson.getString("Modelo");
				AnioVehiculo = _RespuestaJson.getString("AnioVehiculo");
				TipoVehiculo = _RespuestaJson.getString("TipoVehiculo");
				Capacidad = _RespuestaJson.getString("capacidad");
				AutorizacionVehiculo = _RespuestaJson.getString("Autorizacion");
				ROLEmpresa = _RespuestaJson.getString("ROLEmpresa");
				NombreEmpresa = _RespuestaJson.getString("NombreEmpresa");
				FechaConsulta = _RespuestaJson.getString("Fecha");
				Estado = _RespuestaJson.getString("Estado");
				Mensaje = _RespuestaJson.getString("Mensaje");
				Restantes = _RespuestaJson.getString("restantes");
				IdSync = _RespuestaJson.getString("idsincronizacion");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				_Exception = e;
				// e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				_Exception = e;
				// e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				_Exception = e;
			}
			return null;
		}
		

		@Override
		protected void onPostExecute(Void result) {
			
			if (Restantes != null) {
				
				int datosrestantes = (Integer.parseInt(Restantes));
				if (_Exception == null) {

					TxtSincronizacion
							.setText("Sincronizando Vehiculos.... Restan "
									+ datosrestantes + " Registros");

					if (Integer.parseInt(IdSync) != 0) {

						Manager.ActualizarSincronizacionVEHICULOS(IdVehiculo,
								IdSync, 0, "ON", "INICIAL");

						Cursor CursorVehiculo;
						CursorVehiculo = Manager.BuscarVehiculoId(IdVehiculo);
						if (CursorVehiculo.moveToFirst()) {
							int identificadorvehiculo = CursorVehiculo
									.getInt(0);
							// actualizamos vehiculo
							Manager.ActualizarDataVehiculo(
									identificadorvehiculo, UID, EstadoUID,
									IdVehiculo, Marca, Modelo, AnioVehiculo,
									TipoVehiculo,Capacidad, NombreEmpresa, ROLEmpresa,
									"0", AutorizacionVehiculo, FechaConsulta,
									Mensaje,IdSync);
						} else {
							Manager.InsertarDatosVehiculo(UID, EstadoUID,
									IdVehiculo, Marca, Modelo, AnioVehiculo,
									TipoVehiculo,Capacidad, NombreEmpresa, ROLEmpresa,
									"0", AutorizacionVehiculo, FechaConsulta,
									Mensaje,IdSync);
						}
					} else {
						Manager.ActualizarSincronizacionVEHICULOS(IdVehiculo,
								IdSync, 0, "OFF", "INICIAL");
					}

					if (!Restantes.equals("null")) {
						new HiloSincronizarVehiculos().execute();
					} else {
						TxtSincronizacion
								.setText("Sincronizando Vehiculos.... Restan "
										+ datosrestantes + " Registros");

						if (!IdSync.equals("0")) {

							Manager.ActualizarSincronizacionVEHICULOS(
									IdVehiculo, IdSync, 0, "OFF", "INICIODIA");

						} else {

							Manager.ActualizarSincronizacionVehiculoFin("OFF",
									"INICIODIA");

						}

					}

				} else {
					if (ContadorErrores >= 5) {

						Manager.ActualizarContadorSincronizacion(0, "OFF",
								"INICIAL");

						TxtSincronizacion
								.setText("Sincronizando Vehiculos.... Restan "
										+ datosrestantes + " Registros");

					} else {

						Manager.ActualizarContadorSincronizacion(
								(ContadorErrores + 1), "ON", "INICIAL");

						new HiloSincronizarVehiculos().execute();
					}
				}
			} else {

				sinc.setTextColor(Color.WHITE);
				sinc.setEnabled(true);
				regresar.setTextColor(Color.WHITE);
				regresar.setEnabled(true);
				Toast.makeText(getApplicationContext(),
						"Fin Sincronización Vehículos ...", Toast.LENGTH_LONG)
						.show();

				Cursor c = Manager.CursorHABILITAR_SINCRONIZACIONAUTOMATICA();
				if (c.moveToFirst()) {
					Manager.ActualizarHABILITAR_SINCRONIZACIONAUTOMATICA("SI");
				} else {

					Manager.InsertarHABILITAR_SINCRONIZACIONAUTOMATICA("SI");
				}

				startActivity(new Intent(Sincronizacion_buses.this,
						ConfiguracionPrincipal.class));
				finish();

			}

		}
	}

	private class HiloSincronizacionPersonasInicial2 extends
			AsyncTask<Void, Void, Void> {

		String UID = "";
		String EstadoUID = "";
		String IdFuncionario = "";
		String Nombres = "";
		String Apellidos = "";
		String NombreEmpresa = "";
		String IdEmpresa = "";
		String Ost = "", CCosto = "", TipoPase = "";
		String Autorizacion = "";
		String FechaConsulta = "";
		String AutorizacionConductor = "";
		String Estado = "";
		String Mensaje = "";
		String Imagen = "";
		int ContadorMax = 0;
		int ContadorErrores;
		String Restantes;
		String Respuesta;
		JSONObject RespuestaJson;
		Exception Exception = null;
		String IdSync = "";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// super.onPreExecute();

			Cursor CursorSincronizacion = Manager.CursorSincronizacionPersona();

			if (CursorSincronizacion.moveToFirst()) {
				do {
					IdSync = CursorSincronizacion.getString(1);
					// Toast.makeText(Sincronizacion.this, IdSync,
					// Toast.LENGTH_SHORT).show();
					ContadorErrores = CursorSincronizacion.getInt(2);

				} while (CursorSincronizacion.moveToNext());
			}

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			METHOD_NAME = "WSSincronizaInicial";
			SOAP_ACTION = NAMESPACE + "WSSincronizaInicial";
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("idsincronizacion", IdSync);
			request.addProperty("imei", getIMEI());

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11); // la version de WS 1.1
			envelope.dotNet = true; // estamos utilizando .net
			envelope.setOutputSoapObject(request);
			HttpTransportSE transporte = new HttpTransportSE(URL);
			try {
				if (Autentificacion.equals("SI")) {
					// *****
					List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
					String Conexion = UsuarioWs + ":" + ContraseniaWs;
					headerList.add(new HeaderProperty("Authorization", "Basic "
							+ org.kobjects.base64.Base64.encode(Conexion
									.getBytes())));
					transporte.call(SOAP_ACTION, envelope, headerList);
					// /*****
				} else {
					transporte.call(SOAP_ACTION, envelope);
				}

				SoapPrimitive resultado = (SoapPrimitive) envelope
						.getResponse();

				Respuesta = resultado.toString();
				RespuestaJson = new JSONObject(Respuesta);

				UID = RespuestaJson.getString("UID");
				EstadoUID = RespuestaJson.getString("EstadoUID");
				IdFuncionario = RespuestaJson.getString("IdFuncionario");
				Nombres = RespuestaJson.getString("Nombres");
				Apellidos = RespuestaJson.getString("Apellidos");
				NombreEmpresa = RespuestaJson.getString("NombreEmpresa");
				IdEmpresa = RespuestaJson.getString("IdEmpresa");
				Autorizacion = RespuestaJson.getString("Autorizacion");
				FechaConsulta = RespuestaJson.getString("FechaConsulta");
				AutorizacionConductor = RespuestaJson
						.getString("AutorizacionConductor");
				Estado = RespuestaJson.getString("Estado");
				Mensaje = RespuestaJson.getString("Mensaje");
				Imagen = RespuestaJson.getString("Imagen");
				Ost = RespuestaJson.getString("Ost");
				TipoPase = RespuestaJson.getString("TipoPase");
				CCosto = RespuestaJson.getString("CCosto");
				// ContadorMax = RespuestaJson.getInt("max");
				IdSync = RespuestaJson.getString("idsincronizacion");
				Restantes = RespuestaJson.getString("restantes");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Exception = e;
				// e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				Exception = e;
				// e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Exception = e;
				//
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			if (!Restantes.equals("null")) {

				int datosrestantes = (Integer.parseInt(Restantes));

				if (Exception == null) {
					TxtSincronizacion
							.setText("Sincronizando Personas.... Restan "
									+ datosrestantes + " Registros");

					if (Integer.parseInt(IdSync) != 0) {

						Manager.ActualizarSincronizacionPersona(IdFuncionario,
								IdSync, 0, "ON", "INICIAL");

						Cursor CursorFuncionario = Manager
								.BuscarFuncionarioId(IdFuncionario);
						if (CursorFuncionario.moveToFirst()) {
							int id = Manager
									.devolveridFuncionario(IdFuncionario);
							Manager.ActualizarDataFuncionario(UID, EstadoUID,
									IdFuncionario, Nombres, Apellidos,
									NombreEmpresa, IdEmpresa, Ost, CCosto,
									TipoPase, Imagen, Autorizacion,
									AutorizacionConductor, FechaConsulta, id);
						} else {
							Manager.InsertarDatosFuncionario(UID, EstadoUID,
									IdFuncionario, Nombres, Apellidos,
									NombreEmpresa, IdEmpresa, Ost, CCosto,
									TipoPase, Imagen, Autorizacion,
									AutorizacionConductor, FechaConsulta);
						}

					} else {
						Manager.ActualizarSincronizacionPersona(IdFuncionario,
								IdSync, 0, "OFF", "INICIAL");
					}

					new HiloSincronizacionPersonasInicial().execute();
				} else {
					if (ContadorErrores >= 5) {

						// Manager.InsertarDatosLog("Error: Sincronización Inicial Personas ",
						// getFecha(), getHora());
						Manager.ActualizarContadorSincronizacion(0, "OFF",
								"INICIAL");
						// BtnSyncInicial.setTextColor(Color.WHITE);
						// BtnSyncInicial.setEnabled(true);

						// BtnRegresar.setTextColor(Color.WHITE);
						// BtnRegresar.setEnabled(true);

						// progress.setVisibility(View.INVISIBLE);
						TxtSincronizacion
								.setText("Sincronizando Personas.... Restan "
										+ datosrestantes + " Registros");
						// Toast.makeText(getApplicationContext(),Toast2 +
						// datosrestantes + Toast3,Toast.LENGTH_LONG).show();

					} else {

						Manager.ActualizarContadorSincronizacion(
								(ContadorErrores + 1), "ON", "INICIAL");

						new HiloSincronizacionPersonasInicial().execute();
					}
				}
			} else {

				sinc.setTextColor(Color.WHITE);
				sinc.setEnabled(true);
				regresar.setTextColor(Color.WHITE);
				regresar.setEnabled(true);
				Toast.makeText(getApplicationContext(),
						"Fin Sincronización Vehículos ...", Toast.LENGTH_LONG)
						.show();

				startActivity(new Intent(Sincronizacion_buses.this,
						ConfiguracionPrincipal.class));
				finish();
			}

		}

	}

	public String getIMEI() {
		TelephonyManager phonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String id = phonyManager.getDeviceId();
		if (id == null) {
			id = "not available";
		}

		int phoneType = phonyManager.getPhoneType();
		switch (phoneType) {
		case TelephonyManager.PHONE_TYPE_NONE:
			return "NONE: " + id;
		}

		return id;
	}
}
