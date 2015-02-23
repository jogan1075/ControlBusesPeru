package Hilos;

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
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Vibrator;

public class HiloSincronizacionProgamacionBus extends
		AsyncTask<Void, Void, Void> {

	Vibrator Vibrador;
	private DataBaseManagerWS Manager;

	Metodos metodos;

	String METHOD_NAME = "", SOAP_ACTION = "", NAMESPACE = "", URL = "",
			UsuarioWs = "", ContraseniaWs = "", Autentificacion = "",
			EstadoSync = "", TipoSync = "", TipoSincronizacion = "";
	String tipo_Conexion3gWIFI = "";

	String IdProgramacion = "";
	String Patente = "";
	String Origen = "";
	String Destino = "";
	String Duracion = "";
	String Fecha = "";
	String Hora = "";
	String Activo = "";
	String Costo = "";
	String Conductor1 = "";
	String Conductor2 = "";
	String IdSync = "";
	String Restantes;
	String Capacidad = "";
	int datosrestantes;
	int ContadorErrores;
	String id = "";

	String Respuesta;
	JSONObject RespuestaJson;
	Exception Exception = null;
	Context c;

	public HiloSincronizacionProgamacionBus(String idsync, Context contexto) {
		this.id = idsync;
		c = contexto;
		metodos = new Metodos();
		Manager = new DataBaseManagerWS(c);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
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
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		METHOD_NAME = "WSSincronizarProgramacion";
		SOAP_ACTION = NAMESPACE + "WSSincronizarProgramacion";
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		request.addProperty("idsincronizacion", id);
		request.addProperty("imei", metodos.getIMEI(c));

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
				headerList.add(new HeaderProperty("Authorization",
						"Basic "
								+ org.kobjects.base64.Base64.encode(Conexion
										.getBytes())));
				transporte.call(SOAP_ACTION, envelope, headerList);
				// /*****
			} else {
				transporte.call(SOAP_ACTION, envelope);
			}

			SoapPrimitive resultado = (SoapPrimitive) envelope.getResponse();

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
		if (!Restantes.equals("null")) {

			datosrestantes = (Integer.parseInt(Restantes));

			if (Exception == null) {

				if (Integer.parseInt(IdSync) != 0) {
					Manager.ActualizarSincronizacion(Patente, IdSync, 0, "ON",
							"INICIAL");

					Cursor CursorBusPorPatente = Manager
							.BuscarBusIdprogramacion(IdProgramacion);
					if (CursorBusPorPatente.moveToFirst()) {

						Manager.ActualizarDatosBus(IdProgramacion, Patente,
								Origen, Destino, Duracion, Fecha, Hora, Activo,
								Costo, Conductor1, Conductor2, IdSync);

					} else {
						Manager.InsertarDatosBus(IdProgramacion, Patente,
								Origen, Destino, Duracion, Fecha, Hora, Activo,
								Costo, Conductor1, Conductor2, IdSync);
					}

				} else {
					Manager.ActualizarSincronizacion(Patente, IdSync, 0, "OFF",
							"INICIAL");
				}

				new HiloSincronizacionProgamacionBus(IdSync, c).execute();
			} else {
				if (ContadorErrores >= 5) {

					Manager.ActualizarContadorSincronizacion(0, "OFF",
							"INICIAL");

				} else {

					Manager.ActualizarContadorSincronizacion(
							(ContadorErrores + 1), "ON", "INICIAL");

					new HiloSincronizacionProgamacionBus(IdSync, c).execute();
				}
			}
		} else {

			

		}
	}

}
