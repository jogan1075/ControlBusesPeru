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

public class HiloSincronizacionVehiculos extends AsyncTask<Void, Void, Void> {
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
	String id = "";
	String Capacidad="";

	String _Respuesta;
	JSONObject _RespuestaJson;
	Exception _Exception = null;
	Context c;

	Vibrator Vibrador;
	private DataBaseManagerWS Manager;

	Metodos metodos;

	String IdSync = "", METHOD_NAME = "", SOAP_ACTION = "", NAMESPACE = "",
			URL = "", UsuarioWs = "", ContraseniaWs = "", Autentificacion = "",
			EstadoSync = "", TipoSync = "", TipoSincronizacion = "";
	String tipo_Conexion3gWIFI = "";
	int ContadorErrores;

	public HiloSincronizacionVehiculos(String idsync, Context contexto) {
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
				UsuarioWs = CursorWs.getString(2);
				ContraseniaWs = CursorWs.getString(3);
				Autentificacion = CursorWs.getString(4);
			} while (CursorWs.moveToNext());

		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		METHOD_NAME = "WSSincronizarVehiculos";
		SOAP_ACTION = NAMESPACE + "WSSincronizarVehiculos";
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		request.addProperty("IdSincronizacion", id);
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
			// Restantes = _RespuestaJson.getString("restantes");
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
		// TODO Auto-generated method stub
		if (_Exception == null && IdSync != null
				&& !IdSync.equalsIgnoreCase("null")) {

			if (Integer.parseInt(IdSync) != 0) {

				Manager.ActualizarSincronizacionVEHICULOS(IdVehiculo, IdSync,
						0, "ON", "INICIAL");

				Cursor CursorVehiculo;
				CursorVehiculo = Manager.BuscarVehiculoId(IdVehiculo);
				if (CursorVehiculo.moveToFirst()) {
					int identificadorvehiculo = CursorVehiculo.getInt(0);
					// actualizamos vehiculo
					Manager.ActualizarDataVehiculo(identificadorvehiculo, UID,
							EstadoUID, IdVehiculo, Marca, Modelo, AnioVehiculo,
							TipoVehiculo,Capacidad, NombreEmpresa, ROLEmpresa, "0",
							AutorizacionVehiculo, FechaConsulta, Mensaje,IdSync);
				} else {
					Manager.InsertarDatosVehiculo(UID, EstadoUID, IdVehiculo,
							Marca, Modelo, AnioVehiculo, TipoVehiculo,Capacidad,
							NombreEmpresa, ROLEmpresa, "0",
							AutorizacionVehiculo, FechaConsulta, Mensaje,IdSync);
				}
				new HiloSincronizacionVehiculos(IdSync,c).execute();
			} else {

				Manager.ActualizarSincronizacionVehiculoFin("OFF", "INICIODIA");
			}
		} else {
			if (ContadorErrores >= 5) {

				Manager.ActualizarContadorSincronizacion(0, "OFF", "INICIAL");

			} else {

				Manager.ActualizarContadorSincronizacion((ContadorErrores + 1),
						"ON", "INICIAL");
			}
			new HiloSincronizacionVehiculos(IdSync,c).execute();
		}
	}
}
