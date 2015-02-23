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

public class HiloReservarPasaje extends AsyncTask<Void, Void, Void> {
	
	
	String _Respuesta;
	String Retorno;

	// String IdFuncionario = "";
	String Utilizo = "";

	int ContadorAntiguo = -1;
	int ContadorActual = 0;
	int ContadorErrores;

	JSONObject _RespuestaJson;
	Exception _Exception = null;

	Vibrator Vibrador;
	private DataBaseManagerWS Manager;

	Metodos metodos;
	Cursor CursorSincronizacion;
	Context c;
	String rut = "", id = "";
	String idprogra = "", rutFuncionario = "", fecha = "", hora = "",
			estado = "";
	String IdSync, METHOD_NAME, SOAP_ACTION, NAMESPACE, URL, UsuarioWs,
			ContraseniaWs, Autentificacion, EstadoSync, TipoSync,
			TipoSincronizacion;
	String tipo_Conexion3gWIFI;

	public HiloReservarPasaje(Context contexto, String idfuncionario,
			String idprogramacion, String fecha, String hora, String estado) {

		this.c = contexto;
		this.rutFuncionario = idfuncionario;
		this.idprogra = idprogramacion;
		this.fecha = fecha;
		this.hora = hora;
		this.estado = estado;

		Manager = new DataBaseManagerWS(c);
		metodos = new Metodos();
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
		METHOD_NAME = "WSReservaPasaje";
		SOAP_ACTION = NAMESPACE + METHOD_NAME;

		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("idProgramacion", idprogra);
			request.addProperty("idFuncionario", rutFuncionario);
			request.addProperty("imei", metodos.getIMEI(c));
			request.addProperty("fechaReserva", fecha);
			request.addProperty("horaReserva", hora);
			request.addProperty("estado", estado);
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
	}
}


