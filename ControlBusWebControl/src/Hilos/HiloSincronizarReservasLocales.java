package Hilos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class HiloSincronizarReservasLocales extends AsyncTask<Void, Void, Void> {

	Vibrator Vibrador;
	private DataBaseManagerWS Manager;

	Metodos metodos;
	Cursor CursorSincronizacion;

	String rut = "", id = "";
	String idprogra = "", rutFuncionario = "";
	String IdSync, METHOD_NAME, SOAP_ACTION, NAMESPACE, URL, UsuarioWs,
			ContraseniaWs, Autentificacion, EstadoSync, TipoSync,
			TipoSincronizacion, Idioma, Toast1, Toast2, Toast3, Toast4, Toast5,
			Toast6, Toast7, Toast8, Toast9, Toast10;
	String tipo_Conexion3gWIFI;
	String _Respuesta;
	String Retorno;

	String idProgramacion = "";
	String IdFuncionario = "";
	String Utilizo = "";

	int ContadorAntiguo = -1;
	int ContadorActual = 0;
	int ContadorErrores;
	Context c;

	JSONObject _RespuestaJson;
	Exception _Exception = null;

	public HiloSincronizarReservasLocales(String idProgramacion,
			String idFuncionario, String utilizo, Context contexto) {
		// this.context = contexto;
		// this.activity = actividad;
		this.IdFuncionario = idFuncionario;
		this.idProgramacion = idProgramacion;
		this.Utilizo = utilizo;
		c= contexto;

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
		METHOD_NAME = "WSCambiaEstadoCheckin";
		SOAP_ACTION = NAMESPACE + METHOD_NAME;

		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("idProgramacion", idProgramacion);
			request.addProperty("idFuncionario", IdFuncionario);
			request.addProperty("imei", metodos.getIMEI(c));
			request.addProperty("estado", Utilizo);
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11); // la version de WS 1.1
			envelope.dotNet = true; // estamos utilizando .net
			envelope.setOutputSoapObject(request);
			HttpTransportSE transporte = new HttpTransportSE(URL);
			if (Autentificacion.equalsIgnoreCase("si")) {
				List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
				String Conexion = UsuarioWs + ":" + ContraseniaWs;
				headerList.add(new HeaderProperty("Authorization", "Basic "
						+ org.kobjects.base64.Base64.encode(Conexion
								.getBytes())));
				transporte.call(SOAP_ACTION, envelope, headerList);

			} else {
				transporte.call(SOAP_ACTION, envelope);
			}

			SoapPrimitive resultado = (SoapPrimitive) envelope
					.getResponse();
			_Respuesta = resultado.toString();
		} catch (XmlPullParserException e) {
			_Exception = e;
		} catch (IOException e) {
			_Exception = e;
		} catch (RuntimeException e) {

			_Exception = e;

		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if (_Exception == null) {	
			
			 Manager.EliminarSincReservaLocales(idProgramacion,IdFuncionario);
			
			 //cambiar pendiente por NO
			 
			 Manager.ActualizarPendiente(idProgramacion,IdFuncionario,"NO");

			 
		}
	}

}
