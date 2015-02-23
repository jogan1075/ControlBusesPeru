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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.TextView;

public class HiloPantallaBus extends AsyncTask<String, Void, String> {
	TextView _PlacaBus;
	TextView _OrigenBus;
	TextView _DestinoBus;
	TextView _FechaBus;
	TextView _HoraBus;
	TextView _Conductor1;
	TextView _Conductor2;
	TextView _AsientosRestantes;
	Context _Context;
	ProgressDialog _Progress;
	DataBaseManagerWS Manager;
	String NAMESPACE;
	String URL;
	String UsuarioWs;
	String ContraseniaWs;
	String Autentificacion;
	String METHOD_NAME;
	String SOAP_ACTION;
	String _Respuesta;
	Exception _Exception = null;

	public HiloPantallaBus(TextView PlacaBus, TextView OrigenBus,
			TextView DestinoBus, TextView FechaBus, TextView HoraBus,
			TextView Conductor1, TextView Conductor2,
			TextView AsientosRestantes, Context Context) {
		
		super();
		this._PlacaBus = PlacaBus;
		this._OrigenBus = OrigenBus;
		this._DestinoBus = DestinoBus;
		this._FechaBus = FechaBus;
		this._HoraBus = HoraBus;
		this._Conductor1 = Conductor1;
		this._Conductor2 = Conductor2;
		this._AsientosRestantes = AsientosRestantes;
		this._Context = Context;
		Manager = new DataBaseManagerWS(_Context);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		_Progress = ProgressDialog.show(_Context, "Cargando Pasajeros...",
				"Un Momento", true);
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
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		METHOD_NAME = "WsConsultaReservaByIdPersona";
		SOAP_ACTION = NAMESPACE + METHOD_NAME;

		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			// request.addProperty("data", data);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11); // la version de WS 1.1
			envelope.dotNet = true; // estamos utilizando .net
			envelope.setOutputSoapObject(request);
			HttpTransportSE transporte = new HttpTransportSE(URL);
			if (Autentificacion.equalsIgnoreCase("si")) {
				List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
				String Conexion = UsuarioWs + ":" + ContraseniaWs;
				headerList.add(new HeaderProperty("Authorization",
						"Basic "
								+ org.kobjects.base64.Base64.encode(Conexion
										.getBytes())));
				transporte.call(SOAP_ACTION, envelope, headerList);

			} else {
				transporte.call(SOAP_ACTION, envelope);
			}

			SoapPrimitive resultado = (SoapPrimitive) envelope.getResponse();
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
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		_Progress.dismiss();
	}
}
