package Hilos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.webcontrol.controlbus.ConfiguracionPrincipal;
import com.webcontrol.controlbus.MenuPrincipal;

import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;

public class HiloValidarConexionWSDos extends AsyncTask<String, Void, String> {
	String _NAMESPACE, _URL, _METHOD_NAME, _SOAP_ACTION, _Autentificacion,
			_UsuarioWs, _ContraseniaWs;
	DataBaseManagerWS Manager;
	Context context;
	Activity activity;
	ProgressDialog _Progress;
	String _Respuesta;
	Exception _Exception;
	Cursor CursorWs;
	Metodos metodos;

	public HiloValidarConexionWSDos(Context contexto, Activity actividad,
			String NAMESPACE, String URL, String AUTORIZACION,
			String USUARIOWS, String PASSWS) {
		this.context = contexto;
		this.activity = actividad;
		this._NAMESPACE = NAMESPACE;
		this._URL = URL;
		this._Autentificacion = AUTORIZACION;
		this._UsuarioWs = USUARIOWS;
		this._ContraseniaWs = PASSWS;
		metodos = new Metodos();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		_Progress = ProgressDialog.show(context, "Validando Conexión",
				"Un Momento...", true);
		Manager = new DataBaseManagerWS(context);

	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		_METHOD_NAME = "WsValidarConexion";
		_SOAP_ACTION = _NAMESPACE + _METHOD_NAME;

		try {
			SoapObject request = new SoapObject(_NAMESPACE, _METHOD_NAME);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11); // la version de WS 1.1
			envelope.dotNet = true; // estamos utilizando .net
			envelope.setOutputSoapObject(request);
			HttpTransportSE transporte = new HttpTransportSE(_URL);
			if (_Autentificacion.equalsIgnoreCase("si")) {
				List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
				String Conexion = _UsuarioWs + ":" + _ContraseniaWs;
				headerList.add(new HeaderProperty("Authorization",
						"Basic "
								+ org.kobjects.base64.Base64.encode(Conexion
										.getBytes())));
				transporte.call(_SOAP_ACTION, envelope, headerList);

			} else {
				transporte.call(_SOAP_ACTION, envelope);
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
		if (_Exception == null) {
			_Progress.dismiss();
			Manager.ActualizarConfigWs(_NAMESPACE, _URL, _UsuarioWs,
					_ContraseniaWs, _Autentificacion);
			metodos.devolverToast(context, "Conexión Ws Exitosa").show();
			activity.finish();
			activity.startActivity(new Intent(context,
					ConfiguracionPrincipal.class));

		} else {
			_Progress.dismiss();
			metodos.mostrarDialogoSinAccion(context, "Datos Erróneos",
					"Revise Los Datos Ingresados", activity);
		}
	}

}
