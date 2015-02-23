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

public class HiloSincronizacionReservas extends AsyncTask<Void, Void, Void> {

	Vibrator Vibrador;
	private DataBaseManagerWS Manager;

	Metodos metodos;

	String IdSync = "", METHOD_NAME = "", SOAP_ACTION = "", NAMESPACE = "",
			URL = "", UsuarioWs = "", ContraseniaWs = "", Autentificacion = "",
			EstadoSync = "", TipoSync = "", TipoSincronizacion = "";
	String tipo_Conexion3gWIFI = "";
	
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
	String id = "";
	int datosrestantes;
	int ContadorErrores;
	String Autorizado = "";
	String TipoReserva = "";
	String Respuesta;
	JSONObject RespuestaJson;
	Exception Exception = null;
	Context c;
	
	public HiloSincronizacionReservas(String idsync, Context contexto) {
		// this.context = contexto;
		// this.activity = actividad;
		this.id = idsync;
		c=contexto;
		metodos = new Metodos();
		Manager= new DataBaseManagerWS(c);

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
		METHOD_NAME = "WSSincronizarReservas";
		SOAP_ACTION = NAMESPACE + "WSSincronizarReservas";
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
			Restantes = RespuestaJson.getString("Restantes");
			Autorizado = RespuestaJson.getString("Autorizado");
			TipoReserva = RespuestaJson.getString("TipoReserva");

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

			//datosrestantes = (Integer.parseInt(Restantes));

			if (Exception == null) {

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
								Asiento, utilizo, Idsync, Restantes, "NO",Autorizado,TipoReserva);

					} else {

						Manager.InsertarReservas(IdProgramacion,
								rutfuncionario, nombreFuncionario,
								apellidoFuncionario, Rutempresa,
								nombreEmpresa, autorizacionacceso, uid,
								estadouid, Asiento, utilizo, Idsync,
								Restantes, "NO",Autorizado,TipoReserva);

					}

				} else {
					Manager.ActualizarSincronizacionReservas(
							rutfuncionario, Idsync, 0, "OFF", "INICIAL");
				}

				new HiloSincronizacionReservas(Idsync,c).execute();
			} else {
				if (ContadorErrores >= 5) {

					Manager.ActualizarContadorSincronizacionReserva(0,
							"OFF", "INICIAL");

				} else {

					Manager.ActualizarContadorSincronizacionReserva(
							(ContadorErrores + 1), "ON", "INICIAL");

					new HiloSincronizacionReservas(Idsync,c).execute();
				}
			}
		} else {
			
		}
	}

}
