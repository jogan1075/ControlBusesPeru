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
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.webcontrol.controlbus.MenuBus;

public class HiloConsultaReserva extends AsyncTask<String, Void, String> {

	TextView txtNombre, txtDni, txtEmpresa, txtOrigen, txtDestino, txtHora,
			txtFecha, txtPlacaBus;
	ProgressDialog _Progress;
	Context context;
	Activity activity;
	Button ConfirmarReserva;
	DataBaseManagerWS Manager;
	String NAMESPACE, URL, METHOD_NAME, SOAP_ACTION, Autentificacion,
			UsuarioWs, ContraseniaWs;
	Metodos metodos;
	String tipoData = "";
	String data = "";
	String UID="";
	String idprogramacionbusseleccionado = "";
	String FechaConsulta = "";
	String _Respuesta;
	String registrosinreserva = "";
	Exception _Exception = null;
	JSONObject _RespuestaJson;
	String _idFuncionario, _idProgramacion, _Nombres, _Apellidos,
			_NombreEmpresa, _idEmpresa, _Autorizacion, _EstadoReserva,
			_NroReserva, _Origen, _Destino, _Patente, _Hora, _Fecha, _Asiento;

	public HiloConsultaReserva(Context contexto, Activity actividad,
			TextView nombre, TextView rut, TextView empresa, TextView origen,
			TextView destino, TextView hora, TextView fecha, TextView patente,
			String tipoData, String data, String fechaActual,
			String idprogramacionbusseleccionado, Button btn,
			String registrosinreserva) {
		this.txtNombre = nombre;
		this.txtDni = rut;
		this.txtEmpresa = empresa;
		this.txtOrigen = origen;
		this.txtDestino = destino;
		this.txtHora = hora;
		this.txtFecha = fecha;
		this.txtPlacaBus = patente;
		this.context = contexto;
		this.activity = actividad;
		this.ConfirmarReserva = btn;
		Manager = new DataBaseManagerWS(context);
		metodos = new Metodos();
		this.data = data;
		this.tipoData = tipoData;
		this.FechaConsulta = fechaActual;
		this.idprogramacionbusseleccionado = idprogramacionbusseleccionado;
		this.registrosinreserva = registrosinreserva;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		_Progress = ProgressDialog.show(context, "Consultando Reserva",
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
		//
		// METHOD_NAME = "WsConsultaReservaByIdPersona";
		// SOAP_ACTION = NAMESPACE + METHOD_NAME;
		//
		// try {
		// SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		//
		// // request.addProperty("data", "180873945");
		// // request.addProperty("tipodata", "IDFUNCIONARIO");
		// // request.addProperty("imei", "353718052553567");
		//
		// request.addProperty("data", data);
		// request.addProperty("tipodata", tipoData);
		// request.addProperty("imei",metodos.getIMEI(context));
		//
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
		// _RespuestaJson = new JSONObject(_Respuesta);
		//
		// _idFuncionario = _RespuestaJson.getString("IdFuncionario");
		// _idProgramacion = _RespuestaJson.getString("IdProgramacion");
		// _Nombres = _RespuestaJson.getString("Nombres");
		// _Apellidos = _RespuestaJson.getString("Apellidos");
		// _NombreEmpresa = _RespuestaJson.getString("NombreEmpresa");
		// _idEmpresa = _RespuestaJson.getString("IdEmpresa");
		// _Autorizacion = _RespuestaJson.getString("AutorizacionAcceso");
		// _FechaHoraConsulta = _RespuestaJson.getString("FechaHoraConsulta");
		// _EstadoReserva = _RespuestaJson.getString("UtilizoReserva");
		// _NroReserva = _RespuestaJson.getString("NroReserva");
		// _Origen = _RespuestaJson.getString("Origen");
		// _Destino = _RespuestaJson.getString("Destino");
		// _Patente = _RespuestaJson.getString("Patente");
		// _Horario = _RespuestaJson.getString("Horario");
		// _Asiento = _RespuestaJson.getString("Asiento");
		//
		// } catch (XmlPullParserException e) {
		// _Exception = e;
		// } catch (IOException e) {
		// _Exception = e;
		// } catch (RuntimeException e) {
		//
		// _Exception = e;
		//
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// _Exception = e;
		// }
		if (tipoData.equalsIgnoreCase("uid")) {

			if (registrosinreserva != null && registrosinreserva.equals("SI")) {

				Cursor cursorPersonaUID = Manager.BuscarFuncionarioUID(data);
				if(cursorPersonaUID.moveToFirst()){
					
					String autor = cursorPersonaUID.getString(11);
					if(autor.equalsIgnoreCase("si")){
						
						String rut =  cursorPersonaUID.getString(2);
						Cursor VerificarReserva = Manager.BuscarReservaRut(rut,idprogramacionbusseleccionado);
						if(!VerificarReserva.moveToFirst()){
							
							_idProgramacion = idprogramacionbusseleccionado;
							_idFuncionario = rut;
							_Nombres = cursorPersonaUID.getString(3);
							_Apellidos = cursorPersonaUID.getString(4);
							_idEmpresa = cursorPersonaUID.getString(6);
							_NombreEmpresa = cursorPersonaUID.getString(5);
							_Autorizacion = cursorPersonaUID.getString(11);
							_EstadoReserva="NO";
							String uid = cursorPersonaUID.getString(0);
							String estadoUID = cursorPersonaUID.getString(1);
							//String idsync = cursorPersonaUID.getString(14);
							Cursor progra = Manager
									.BuscarBusIdprogramacion(idprogramacionbusseleccionado);
							if (progra.moveToFirst()) {
								_Patente = progra.getString(1);
								_NroReserva = progra.getString(1);
								_Origen = progra.getString(2);
								_Destino = progra.getString(3);
								_Hora = progra.getString(6);
								_Fecha = progra.getString(5);
							}
							
							
							Manager.InsertarReservas(_idProgramacion, _idFuncionario, _Nombres, _Apellidos, _idEmpresa, 
									_NombreEmpresa, _Autorizacion,uid,estadoUID,"0",_EstadoReserva,"","","SI","NO","MOVIL");
							
							
						}else{
							Cursor cursor = Manager.BuscarReservaUID(data,
									idprogramacionbusseleccionado);
							if (cursor.moveToFirst()) {

								_idProgramacion = cursor.getString(1);
								_idFuncionario = cursor.getString(2);
								_Nombres = cursor.getString(3);
								_Apellidos = cursor.getString(4);
								_idEmpresa = cursor.getString(5);
								_NombreEmpresa = cursor.getString(6);
								_Autorizacion = cursor.getString(7);
								_Asiento = cursor.getString(10);
								_EstadoReserva = cursor.getString(11);

								Cursor progra = Manager
										.BuscarBusIdprogramacion(idprogramacionbusseleccionado);
								if (progra.moveToFirst()) {
									_Patente = progra.getString(1);
									_NroReserva = progra.getString(1);
									_Origen = progra.getString(2);
									_Destino = progra.getString(3);
									_Hora = progra.getString(6);
									_Fecha = progra.getString(5);
								}

								
							}
						}
					}else{
						//rechazar por no autorizado
						_EstadoReserva="";
						_idFuncionario = cursorPersonaUID.getString(2);
						_Nombres = cursorPersonaUID.getString(3);
						_Apellidos = cursorPersonaUID.getString(4);
						_idEmpresa = cursorPersonaUID.getString(6);
						_NombreEmpresa = cursorPersonaUID.getString(5);
						_Autorizacion = cursorPersonaUID.getString(11);
					}
					
					
				}else{
					//rechazar no existe uid
					_EstadoReserva="";
					_Autorizacion="";
					UID = "";
					
				}
				
				

			} else {
				Cursor cursor = Manager.BuscarReservaUID(data,
						idprogramacionbusseleccionado);
				if (cursor.moveToFirst()) {

					_idProgramacion = cursor.getString(1);
					_idFuncionario = cursor.getString(2);
					_Nombres = cursor.getString(3);
					_Apellidos = cursor.getString(4);
					_idEmpresa = cursor.getString(5);
					_NombreEmpresa = cursor.getString(6);
					_Autorizacion = cursor.getString(7);
					_Asiento = cursor.getString(10);
					_EstadoReserva = cursor.getString(11);

					Cursor progra = Manager
							.BuscarBusIdprogramacion(idprogramacionbusseleccionado);
					if (progra.moveToFirst()) {
						_Patente = progra.getString(1);
						_NroReserva = progra.getString(1);
						_Origen = progra.getString(2);
						_Destino = progra.getString(3);
						_Hora = progra.getString(6);
						_Fecha = progra.getString(5);
					}

					// _FechaHoraConsulta
				}
			}
		} else {

			if (registrosinreserva != null && registrosinreserva.equals("SI")) {

				Cursor cursorPersonaRUT = Manager.BuscarFuncionarioId(data);
				if(cursorPersonaRUT.moveToFirst()){
					
					String autorizado = cursorPersonaRUT.getString(11);
					if(autorizado.equalsIgnoreCase("si")){
						
						Cursor VerificarReserva = Manager.BuscarReservaRut(data,idprogramacionbusseleccionado);
						if(!VerificarReserva.moveToFirst()){
							
							
							_idProgramacion = idprogramacionbusseleccionado;
							_idFuncionario = data;
							_Nombres = cursorPersonaRUT.getString(3);
							_Apellidos = cursorPersonaRUT.getString(4);
							_idEmpresa = cursorPersonaRUT.getString(6);
							_NombreEmpresa = cursorPersonaRUT.getString(5);
							_Autorizacion = cursorPersonaRUT.getString(11);
							_EstadoReserva="NO";
							String uid = cursorPersonaRUT.getString(0);
							String estadoUID = cursorPersonaRUT.getString(1);
							String idsync = cursorPersonaRUT.getString(14);
							
							Cursor progra = Manager
									.BuscarBusIdprogramacion(idprogramacionbusseleccionado);
							if (progra.moveToFirst()) {
								_Patente = progra.getString(1);
								_NroReserva = progra.getString(1);
								_Origen = progra.getString(2);
								_Destino = progra.getString(3);
								_Hora = progra.getString(6);
								_Fecha = progra.getString(5);
							}
							
							
							Manager.InsertarReservas(_idProgramacion, _idFuncionario, _Nombres, _Apellidos, _idEmpresa, 
									_NombreEmpresa, _Autorizacion,uid,estadoUID,"0",_EstadoReserva,"","","SI","NO","MOVIL");
							
							
							
							
						}else
						{
							Cursor cursorRut = Manager.BuscarReservaRut(data,
									idprogramacionbusseleccionado);
							if (cursorRut.moveToFirst()) {

								_idProgramacion = cursorRut.getString(1);
								_idFuncionario = cursorRut.getString(2);
								_Nombres = cursorRut.getString(3);
								_Apellidos = cursorRut.getString(4);
								_idEmpresa = cursorRut.getString(5);
								_NombreEmpresa = cursorRut.getString(6);
								_Autorizacion = cursorRut.getString(7);
								_Asiento = cursorRut.getString(10);
								_EstadoReserva = cursorRut.getString(11);

								Cursor program = Manager
										.BuscarBusIdprogramacion(idprogramacionbusseleccionado);
								if (program.moveToFirst()) {
									_Patente = program.getString(1);
									_NroReserva = program.getString(1);
									_Origen = program.getString(2);
									_Destino = program.getString(3);
									_Hora = program.getString(6);
									_Fecha = program.getString(5);
								}
							}
						}
						
					}else
					{
						
						
						//rechazar por no autorizado para acceder
						_EstadoReserva="";
						_idFuncionario = cursorPersonaRUT.getString(2);
						_Nombres = cursorPersonaRUT.getString(3);
						_Apellidos = cursorPersonaRUT.getString(4);
						_idEmpresa = cursorPersonaRUT.getString(6);
						_NombreEmpresa = cursorPersonaRUT.getString(5);
						_Autorizacion = cursorPersonaRUT.getString(11);
						
					}
					
				}else
				{
					//rechazar por no existir la persona
					_EstadoReserva="";
					_Autorizacion="";
					//_idFuncionario="";
				}

			} else {

				Cursor cursorRut = Manager.BuscarReservaRut(data,
						idprogramacionbusseleccionado);
				if (cursorRut.moveToFirst()) {

					_idProgramacion = cursorRut.getString(1);
					_idFuncionario = cursorRut.getString(2);
					_Nombres = cursorRut.getString(3);
					_Apellidos = cursorRut.getString(4);
					_idEmpresa = cursorRut.getString(5);
					_NombreEmpresa = cursorRut.getString(6);
					_Autorizacion = cursorRut.getString(7);
					_Asiento = cursorRut.getString(10);
					_EstadoReserva = cursorRut.getString(11);

					Cursor program = Manager
							.BuscarBusIdprogramacion(idprogramacionbusseleccionado);
					if (program.moveToFirst()) {
						_Patente = program.getString(1);
						_NroReserva = program.getString(1);
						_Origen = program.getString(2);
						_Destino = program.getString(3);
						_Hora = program.getString(6);
						_Fecha = program.getString(5);
					}
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

		
		
		if (_EstadoReserva.equalsIgnoreCase("SI")) {
			ConfirmarReserva.setEnabled(false);
			ConfirmarReserva.setTextColor(Color.GRAY);
			metodos.mostrarDialogoConAccion(context, _Nombres + " "
					+ _Apellidos, "Ya Tiene una Su Reserva Confirmada",
					"Reserva", activity);
		}
		else if(tipoData.equalsIgnoreCase("uid") && UID == null || UID.equalsIgnoreCase("null")){
			
			metodos.mostrarDialogoConAccion(context, "No Existe UID!!",
					"UID no existente: " + data, "No Tiene", activity);
		}
		
		else if(_Autorizacion.equalsIgnoreCase("no")){
			
			metodos.mostrarDialogoConAccion(context, "No Autorizado!!",
					"No Autorizado para Acceso: " + data, "No Tiene", activity);
		}
		
		else if(_idFuncionario == null || _idFuncionario.equalsIgnoreCase("null")){
			
		 metodos.mostrarDialogoConAccion(context, "No Existe Funcionario!!",
					"No existe funcionario: " + data, "No Tiene", activity);
		}
		

		else if (_idProgramacion == null || _idProgramacion.equalsIgnoreCase("null")) {
			if (_Nombres == null || _Nombres.equalsIgnoreCase("null")
					|| _Apellidos == null
					|| _Apellidos.equalsIgnoreCase("null")) {
				metodos.mostrarDialogoConAccion(context, "Sin Reserva",
						"No Hay Reserva Para: " + data, "No Tiene", activity);
			} else {
				metodos.mostrarDialogoConAccion(context, "Sin Reserva",
						"Don/a " + _Nombres + " " + _Apellidos
								+ " No Tiene Reserva Habilitada", "No Tiene",
						activity);
			}

		} else {
			txtNombre.setText(_Nombres + " " + _Apellidos);
			txtDni.setText(_idFuncionario);
			txtEmpresa.setText(_NombreEmpresa);
			txtOrigen.setText(_Origen);
			txtDestino.setText(_Destino);
			txtHora.setText(_Hora);
			txtFecha.setText(_Fecha);

			txtPlacaBus.setText(_Patente);
			ConfirmarReserva.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					new HiloCambiarEstadoCheckIn(_idProgramacion,
							_idFuncionario, context, activity).execute();
				}
			});
		}

	}

}
