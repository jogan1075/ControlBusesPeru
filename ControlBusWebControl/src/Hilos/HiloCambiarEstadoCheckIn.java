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

import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Metodos;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;

import com.webcontrol.controlbus.IngresoPasajeros;
import com.webcontrol.controlbus.MenuBus;

public class HiloCambiarEstadoCheckIn extends AsyncTask<String, Void, String> {
	Metodos metodos;
	Context context;
	Activity activity;
	DataBaseManagerWS Manager;
	String _idProgramacion;
	String _idFuncionario;
	String NAMESPACE, URL, METHOD_NAME, SOAP_ACTION, Autentificacion,
			UsuarioWs, ContraseniaWs;
	ProgressDialog _Progress;
	String _Respuesta;
	Exception _Exception;

	public HiloCambiarEstadoCheckIn(String idProgramacion,
			String idFuncionario, Context contexto, Activity actividad) {
		this.context = contexto;
		this.activity = actividad;
		this._idFuncionario = idFuncionario;
		this._idProgramacion = idProgramacion;
		Manager = new DataBaseManagerWS(context);
		metodos = new Metodos();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		_Progress = ProgressDialog.show(context, "Cambiando Estado",
				"Cargando...", true);

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
		// METHOD_NAME = "WSCambiaEstadoCheckin";
		// SOAP_ACTION = NAMESPACE + METHOD_NAME;
		//
		// try {
		// SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		//
		// request.addProperty("idProgramacion", _idProgramacion);
		// request.addProperty("idFuncionario", _idFuncionario);
		// request.addProperty("imei", metodos.getIMEI(context));
		// request.addProperty("estado", "SI");
		// SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
		// SoapEnvelope.VER11); // la version de WS 1.1
		// envelope.dotNet = true; // estamos utilizando .net
		// envelope.setOutputSoapObject(request);
		// HttpTransportSE transporte = new HttpTransportSE(URL);
		// if (Autentificacion.equalsIgnoreCase("si")) {
		// List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
		// String Conexion = UsuarioWs + ":" + ContraseniaWs;
		// headerList.add(new HeaderProperty("Authorization",
		// "Basic "
		// + org.kobjects.base64.Base64.encode(Conexion
		// .getBytes())));
		// transporte.call(SOAP_ACTION, envelope, headerList);
		//
		// } else {
		// transporte.call(SOAP_ACTION, envelope);
		// }
		//
		// SoapPrimitive resultado = (SoapPrimitive) envelope.getResponse();
		// _Respuesta = resultado.toString();
		// }
		// catch (XmlPullParserException e) {
		// _Exception = e;
		// } catch (IOException e) {
		// _Exception = e;
		// } catch (RuntimeException e) {
		//
		// _Exception = e;
		//
		// }

		if (_idFuncionario != null && _idProgramacion != null) {

			

				Cursor cursor = Manager.BuscarReservaRut(_idFuncionario,
						_idProgramacion);
				if (cursor.moveToFirst()) {
					
					String utilizo = cursor.getString(11);
					if(utilizo.equalsIgnoreCase("no")){
					
						Manager.ActualizarUtilizoPendiente(_idProgramacion,	_idFuncionario, "SI", "SI");
						Manager.InsertarSincReservaLocales(_idProgramacion, _idFuncionario, "SI", "SI");
					}
				}
			
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
			_Progress.dismiss();
			Toast.makeText(
					context,
					"La Reserva a " + _idFuncionario
							+ " se Realizó Correctamente", Toast.LENGTH_SHORT)
					.show();

			activity.finish();
			activity.startActivity(new Intent(context, IngresoPasajeros.class));
			
			
//		} else {
//			_Progress.dismiss();
//			activity.finish();
//			activity.startActivity(new Intent(context, IngresoPasajeros.class));
//			Toast.makeText(
//					context,
//					"No se Pudo Cambiar Confirmar la Reserva a "
//							+ _idFuncionario + " Intente nuevamente",
//					Toast.LENGTH_SHORT).show();
//		}
	}
}
