package ClasesYMetodos;

import java.util.ArrayList;
import java.util.List;

import com.webcontrol.controlbus.ListadoPasajeros;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManagerWS {
	
	private DBHelperWS helper;
	private SQLiteDatabase db;

	public DataBaseManagerWS(Context context) {
		helper = new DBHelperWS(context);
		db = helper.getWritableDatabase();
	}
	
	
	//#region  TABLE CONFIG WS

	public static final String TABLE_CONFIG = "CONFIG";
	public static final String CN_IDCONFIG = "Id";
	public static final String CN_NAMESPACEWS = "NameSpaceWS";
	public static final String CN_URLWS = "UrlWS";
	public static final String CN_USUARIOWS = "UsuarioWS";
	public static final String CN_CONTRASENIAWS = "ContraseniaWS";
	public static final String CN_AUTENTIFICACIONWS = "AutentificacionWS";

	public static final String CREATE_TABLE7 = "create table " + TABLE_CONFIG
			+ " (" + CN_IDCONFIG + " integer primary key, " + CN_NAMESPACEWS
			+ " text, " + CN_URLWS + " text, " + CN_USUARIOWS + " text, "
			+ CN_CONTRASENIAWS + " text, " + CN_AUTENTIFICACIONWS + " text);";



	private ContentValues ValoresConfig(int Id, String NameSpaceWs,
			String UrlWs, String UsuarioWs, String ContraseniaWs,
			String AutentificacionWs) {
		ContentValues valores = new ContentValues();
		valores.put(CN_IDCONFIG, Id);
		valores.put(CN_NAMESPACEWS, NameSpaceWs);
		valores.put(CN_URLWS, UrlWs);
		valores.put(CN_USUARIOWS, UsuarioWs);
		valores.put(CN_CONTRASENIAWS, ContraseniaWs);
		valores.put(CN_AUTENTIFICACIONWS, AutentificacionWs);

		return valores;
	}

	public void InsertarDatosConfig(String NameSpaceWs, String UrlWs,
			String UsuarioWs, String ContraseniaWs, String AutentificacionWs) {
		db.insert(
				TABLE_CONFIG,
				null,
				ValoresConfig(1, NameSpaceWs, UrlWs, UsuarioWs, ContraseniaWs,
						AutentificacionWs));
	}

	public void ActualizarConfigWs(String NameSpaceWs, String UrlWs,
			String UsuarioWs, String ContraseniaWs, String AutentificacionWs) {
		ContentValues valores = new ContentValues();
		valores.put(CN_NAMESPACEWS, NameSpaceWs);
		valores.put(CN_URLWS, UrlWs);
		valores.put(CN_USUARIOWS, UsuarioWs);
		valores.put(CN_CONTRASENIAWS, ContraseniaWs);
		valores.put(CN_AUTENTIFICACIONWS, AutentificacionWs);

		db.update(TABLE_CONFIG, valores, CN_IDCONFIG + "=" + "1", null);
	}

	public Cursor CursorConfig() {
		String[] Columnas = new String[] { CN_NAMESPACEWS, CN_URLWS,
				CN_USUARIOWS, CN_CONTRASENIAWS, CN_AUTENTIFICACIONWS };
		return db.query(TABLE_CONFIG, Columnas, CN_IDCONFIG + "=?",
				new String[] { "1" }, null, null, null);
	}

	//#endregion
	
	/********************************* DATOS BUS ***************************************/

	//#region TABLE DATOSBUS
	
	public static final String TABLE_DATOSBUS = "DATOSBUS";
	public static final String CN_IDPROGRAMACION = "IdProgramacion";
	public static final String CN_PATENTE = "Patente";
	public static final String CN_ORIGEN = "Origen";
	public static final String CN_DESTINO = "Destino";
	public static final String CN_DURACION = "Duracion";
	public static final String CN_FECHA = "Fecha";
	public static final String CN_HORA = "Hora";
	public static final String CN_ACTIVO = "Activo";
	public static final String CN_COSTO = "Costo";
	public static final String CN_CONDUCTOR1 = "Conductor1";
	public static final String CN_CONDUCTOR2 = "Conductor2";
	public static final String CN_IDSYNC = "IdSync";

	public static final String CREATE_TABLE = "create table " + TABLE_DATOSBUS
			+ " (" + CN_IDPROGRAMACION + " integer primary key, " + CN_PATENTE
			+ " text, " + CN_ORIGEN + " text, " + CN_DESTINO + " text, "
			+ CN_DURACION + " text, " + CN_FECHA + " text, " + CN_HORA
			+ " text, " + CN_ACTIVO + " text, " + CN_COSTO + " text, "
			+ CN_CONDUCTOR1 + " text," + CN_CONDUCTOR2 + " text, " + CN_IDSYNC
			+ " text);";

	public void InsertarDatosBus(String idprogramacion, String patente,
			String origen, String destino, String duracion, String fecha,
			String Hora, String activo, String costo, String conductor1,
			String conductor2, String idsync) {

		ContentValues valores = new ContentValues();
		valores.put(CN_IDPROGRAMACION, idprogramacion);
		valores.put(CN_PATENTE, patente);
		valores.put(CN_ORIGEN, origen);
		valores.put(CN_DESTINO, destino);
		valores.put(CN_DURACION, duracion);
		valores.put(CN_FECHA, fecha);
		valores.put(CN_HORA, Hora);
		valores.put(CN_ACTIVO, activo);
		valores.put(CN_COSTO, costo);
		valores.put(CN_CONDUCTOR1, conductor1);
		valores.put(CN_CONDUCTOR2, conductor2);
		valores.put(CN_IDSYNC, idsync);

		db.insert(TABLE_DATOSBUS, null, valores);

	}

	public void ActualizarDatosBus(String Id, String patente, String origen,
			String destino, String duracion, String fecha, String Hora,
			String activo, String costo, String conductor1, String conductor2,
			String idsync) {

		ContentValues valores = new ContentValues();

		valores.put(CN_PATENTE, patente);
		valores.put(CN_ORIGEN, origen);
		valores.put(CN_DESTINO, destino);
		valores.put(CN_DURACION, duracion);
		valores.put(CN_FECHA, fecha);
		valores.put(CN_HORA, Hora);
		valores.put(CN_ACTIVO, activo);
		valores.put(CN_COSTO, costo);
		valores.put(CN_CONDUCTOR1, conductor1);
		valores.put(CN_CONDUCTOR2, conductor2);
		valores.put(CN_IDSYNC, idsync);

		db.update(TABLE_DATOSBUS, valores, CN_IDPROGRAMACION + "=" + Id, null);
	}

	public Cursor CursorDatosBus() {
		String[] columnas = new String[] { CN_PATENTE, CN_ORIGEN, CN_DESTINO,
				CN_DURACION, CN_FECHA, CN_HORA, CN_ACTIVO, CN_COSTO,
				CN_CONDUCTOR1, CN_CONDUCTOR2, CN_IDSYNC };
		return db.query(TABLE_DATOSBUS, columnas, null, null, null, null, null);
	}

	public Cursor BuscarBusPatente(String patente) {
		String[] Columnas = new String[] { CN_IDPROGRAMACION, CN_PATENTE,
				CN_ORIGEN, CN_DESTINO, CN_DURACION, CN_FECHA, CN_HORA,
				CN_ACTIVO, CN_COSTO, CN_CONDUCTOR1, CN_CONDUCTOR2, CN_IDSYNC };

		return db.query(TABLE_DATOSBUS, Columnas, CN_PATENTE + "=?",
				new String[] { patente }, null, null, null);
	}

	public Cursor BuscarBusPatentefECHA(String patente, String Fecha) {

		Cursor c = db.rawQuery("SELECT * From DATOSBUS WHERE Patente ='"
				+ patente + "' AND Fecha = '" + Fecha + "'", null);

		return c;
	}

	public Cursor BuscarBusIdprogramacion(String idprogramacion) {
		String[] Columnas = new String[] { CN_IDPROGRAMACION, CN_PATENTE,
				CN_ORIGEN, CN_DESTINO, CN_DURACION, CN_FECHA, CN_HORA,
				CN_ACTIVO, CN_COSTO, CN_CONDUCTOR1, CN_CONDUCTOR2, CN_IDSYNC };

		return db.query(TABLE_DATOSBUS, Columnas, CN_IDPROGRAMACION + "=?",
				new String[] { idprogramacion }, null, null, null);
	}

	public int devolveridDatoBus(String idprogramacion) {
		Cursor c = db.rawQuery("SELECT * From DATOSBUS WHERE IdProgramacion ='"
				+ idprogramacion + "'", null);
		int id = 0;
		if (c.moveToFirst()) {
			do {
				id = c.getInt(0);
			} while (c.moveToNext());
		}
		return id;
	}

	public Cursor DevolerFechayHora(String idprogramacion) {
		String[] Columnas = new String[] { CN_IDPROGRAMACION, CN_FECHA, CN_HORA };

		return db.query(TABLE_DATOSBUS, Columnas, CN_IDPROGRAMACION + "=?",
				new String[] { idprogramacion }, null, null, null);
	}
	
	//#endregion

	/********************************** CONFIG ********************************/
	
	//#region TABLE CONFIG APP
	
	public static final String TABLE_CONFIGAPP = "CONFIGAPP";
	public static final String CN_IDCONFIGAPP = "Id";
	public static final String CN_CAMARA = "Camara";
	public static final String CN_NFC = "NFC";
	public static final String CN_LOG = "Log";

	public static final String CREATE_TABLE8 = "create table "
			+ TABLE_CONFIGAPP + " (" + CN_IDCONFIGAPP
			+ " integer primary key autoincrement, " + CN_CAMARA + " text, "
			+ CN_NFC + " text, " + CN_LOG + " text);";

	public void InsertarConfigApp(String camara, String nfc, String log) {
		ContentValues Valores = new ContentValues();
		Valores.put(CN_CAMARA, camara);
		Valores.put(CN_NFC, nfc);
		Valores.put(CN_LOG, log);

		db.insert(TABLE_CONFIGAPP, null, Valores);
	}

	public void ActualizarConfigApp(String camara, String nfc, String log) {
		ContentValues Valores = new ContentValues();
		Valores.put(CN_CAMARA, camara);
		Valores.put(CN_NFC, nfc);
		Valores.put(CN_LOG, log);

		db.update(TABLE_CONFIGAPP, Valores, CN_IDCONFIGAPP + "=" + 1, null);
	}

	public Cursor CursorConfigApp() {
		String[] Columnas = new String[] { CN_CAMARA, CN_NFC, CN_LOG };

		return db
				.query(TABLE_CONFIGAPP, Columnas, null, null, null, null, null);
	}

	
	//#endregion
	
	/****************************** SINCRONIZACION DATOS BUSES ***********************************/

	//#region TABLE SINCRINIZACION
	
	public static final String TABLE_SINCRONIZACION = "SINCRONIZACION";
	public static final String CN_IDSINCRONIZACION = "Id";
	public static final String CN_PLACA = "Placa";
	public static final String CN_IDSYNCSINC = "IdSync";
	public static final String CN_CONTADORSYNC = "Contador";
	public static final String CN_ESTADOSYNC = "EstadoSync"; // ON OFF
	public static final String CN_TIPOSYNC = "TipoSync";// INICIAL, INICIODIA,
														// INCREMENTAL

	public static final String CREATE_TABLE9 = "create table "
			+ TABLE_SINCRONIZACION + " (" + CN_IDSINCRONIZACION
			+ " integer primary key, " + CN_PLACA + " text, " + CN_IDSYNCSINC
			+ " text, " + CN_CONTADORSYNC + " int, " + CN_ESTADOSYNC
			+ " text, " + CN_TIPOSYNC + " text);";

	private ContentValues ValoresSincronizacion(String Id, String Placa,
			String IdSync, int Contador, String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_IDSINCRONIZACION, Id);
		valores.put(CN_PLACA, Placa);
		valores.put(CN_IDSYNC, IdSync);
		valores.put(CN_CONTADORSYNC, Contador);
		valores.put(CN_ESTADOSYNC, EstadoSync);
		valores.put(CN_TIPOSYNC, TipoSync);
		return valores;
	}

	public void InsertarDatosSincronizacion(String Placa, String IdSync,
			int Contador, String EstadoSync, String TipoSync) {
		db.insert(
				TABLE_SINCRONIZACION,
				null,
				ValoresSincronizacion("1", Placa, IdSync, Contador, EstadoSync,
						TipoSync));
	}

	public Cursor CursorSincronizacion() {
		String[] Columnas = new String[] { CN_IDSYNC, CN_CONTADORSYNC,
				CN_ESTADOSYNC, CN_TIPOSYNC };
		return db.query(TABLE_SINCRONIZACION, Columnas, CN_IDSINCRONIZACION
				+ "=?", new String[] { "1" }, null, null, null);
	}

	public void ActualizarSincronizacion(String placa, String IdSync,
			int Contador, String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_PLACA, placa);
		valores.put(CN_IDSYNC, IdSync);
		valores.put(CN_CONTADORSYNC, Contador);
		valores.put(CN_ESTADOSYNC, EstadoSync);
		valores.put(CN_TIPOSYNC, TipoSync);

		db.update(TABLE_SINCRONIZACION, valores, CN_IDSINCRONIZACION + "="
				+ "1", null);
	}

	public void ActualizarContadorSincronizacion(int Contador,
			String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_CONTADORSYNC, Contador);
		valores.put(CN_ESTADOSYNC, EstadoSync);
		valores.put(CN_TIPOSYNC, TipoSync);

		db.update(TABLE_SINCRONIZACION, valores, CN_IDSINCRONIZACION + "="
				+ "1", null);
	}

	//#endregion
	
	/************************************* RESERVAS **********************************/

	//#region TABLE RESERVAS

	public static final String TABLE_RESERVAS = "RESERVAS";
	public static final String CN_IDRESERVAS = "IdReservas";
	public static final String CN_IDPROGRAMACIONRESERVAS = "IdProgramacionReservas";
	public static final String CN_RUTFUNCIONARIORESERVAS = "RutFuncionarioReservas";
	public static final String CN_NOMBREFUNCIONARIORESERVAS = "NombreFuncionarioReservas";
	public static final String CN_APELLIDOFUNCIONARIORESERVAS = "ApellidoFuncionarioReservas";
	public static final String CN_RUTEMPRESARESERVAS = "RutEmpresaReservas";
	public static final String CN_NOMBREEMPRESARESERVAS = "NombreEmpresaReservas";
	public static final String CN_AUTORIZACIONACCESORESERVAS = "AutorizacionAccesoReservas";
	public static final String CN_UIDRESERVAS = "UidReservas";
	public static final String CN_ESTADOUIDRESERVAS = "EstadoUidReservas";
	public static final String CN_ASIENTORESERVAS = "AsientoReservas";
	public static final String CN_UTILIZORESERVAS = "UtilizoReservas";
	public static final String CN_IDSYNCRESERVAS = "IdsyncReservas";
	public static final String CN_RESTANTESRESERVAS = "RestantesReservas";
	public static final String CN_PENDIENTE = "Pendiente";
	public static final String CN_AUTORIZADO = "Autorizado";
	public static final String CN_TIPORESERVA = "TipoReserva";

	public static final String CREATE_TABLE1 = "create table " + TABLE_RESERVAS
			+ " (" + CN_IDRESERVAS + " integer primary key autoincrement, "
			+ CN_IDPROGRAMACIONRESERVAS + " text, " + CN_RUTFUNCIONARIORESERVAS
			+ " text, " + CN_NOMBREFUNCIONARIORESERVAS + " text, "
			+ CN_APELLIDOFUNCIONARIORESERVAS + " text, "
			+ CN_RUTEMPRESARESERVAS + " text, " + CN_NOMBREEMPRESARESERVAS
			+ " text, " + CN_AUTORIZACIONACCESORESERVAS + " text, "
			+ CN_UIDRESERVAS + " text, " + CN_ESTADOUIDRESERVAS + " text, "
			+ CN_ASIENTORESERVAS + " text, " + CN_UTILIZORESERVAS + " text, "
			+ CN_IDSYNCRESERVAS + " text, " + CN_RESTANTESRESERVAS + " text, "
			+ CN_PENDIENTE + " text, " + CN_AUTORIZADO + " text, "
			+ CN_TIPORESERVA + " text);";

	public void InsertarReservas(String IdProgramacion, String rutfuncionario,
			String nombreFuncionario, String apellidoFuncionario,
			String Rutempresa, String nombreEmpresa, String autorizacionacceso,
			String uid, String estadouid, String Asiento, String utilizo,
			String Idsync, String Restantes, String Pendiente,
			String autorizado, String tiporeserva) {

		ContentValues Valores = new ContentValues();
		Valores.put(CN_IDPROGRAMACIONRESERVAS, IdProgramacion);
		Valores.put(CN_RUTFUNCIONARIORESERVAS, rutfuncionario);
		Valores.put(CN_NOMBREFUNCIONARIORESERVAS, nombreFuncionario);
		Valores.put(CN_APELLIDOFUNCIONARIORESERVAS, apellidoFuncionario);
		Valores.put(CN_RUTEMPRESARESERVAS, Rutempresa);
		Valores.put(CN_NOMBREEMPRESARESERVAS, nombreEmpresa);
		Valores.put(CN_AUTORIZACIONACCESORESERVAS, autorizacionacceso);
		Valores.put(CN_UIDRESERVAS, uid);
		Valores.put(CN_ESTADOUIDRESERVAS, estadouid);
		Valores.put(CN_ASIENTORESERVAS, Asiento);
		Valores.put(CN_UTILIZORESERVAS, utilizo);
		Valores.put(CN_IDSYNCRESERVAS, Idsync);
		Valores.put(CN_RESTANTESRESERVAS, Restantes);
		Valores.put(CN_PENDIENTE, Pendiente);
		Valores.put(CN_AUTORIZADO, autorizado);
		Valores.put(CN_TIPORESERVA, tiporeserva);

		db.insert(TABLE_RESERVAS, null, Valores);
	}

	public void ActualizarReservas(String Id, String IdProgramacion,
			String rutfuncionario, String nombreFuncionario,
			String apellidoFuncionario, String Rutempresa,
			String nombreEmpresa, String autorizacionacceso, String uid,
			String estadouid, String Asiento, String utilizo, String Idsync,
			String Restantes, String Pendiente, String autorizado,
			String tiporeserva) {

		ContentValues Valores = new ContentValues();
		Valores.put(CN_IDPROGRAMACIONRESERVAS, IdProgramacion);
		Valores.put(CN_RUTFUNCIONARIORESERVAS, rutfuncionario);
		Valores.put(CN_NOMBREFUNCIONARIORESERVAS, nombreFuncionario);
		Valores.put(CN_APELLIDOFUNCIONARIORESERVAS, apellidoFuncionario);
		Valores.put(CN_RUTEMPRESARESERVAS, Rutempresa);
		Valores.put(CN_NOMBREEMPRESARESERVAS, nombreEmpresa);
		Valores.put(CN_AUTORIZACIONACCESORESERVAS, autorizacionacceso);
		Valores.put(CN_UIDRESERVAS, uid);
		Valores.put(CN_ESTADOUIDRESERVAS, estadouid);
		Valores.put(CN_ASIENTORESERVAS, Asiento);
		Valores.put(CN_UTILIZORESERVAS, utilizo);
		Valores.put(CN_IDSYNCRESERVAS, Idsync);
		Valores.put(CN_RESTANTESRESERVAS, Restantes);
		Valores.put(CN_PENDIENTE, Pendiente);
		Valores.put(CN_AUTORIZADO, autorizado);
		Valores.put(CN_TIPORESERVA, tiporeserva);

		db.update(TABLE_RESERVAS, Valores, CN_IDRESERVAS + "=" + Id, null);
	}

	public Cursor CursorReservas() {
		String[] columnas = new String[] { CN_IDPROGRAMACIONRESERVAS,
				CN_RUTFUNCIONARIORESERVAS, CN_NOMBREFUNCIONARIORESERVAS,
				CN_APELLIDOFUNCIONARIORESERVAS, CN_RUTEMPRESARESERVAS,
				CN_NOMBREEMPRESARESERVAS, CN_AUTORIZACIONACCESORESERVAS,
				CN_UIDRESERVAS, CN_ESTADOUIDRESERVAS, CN_ASIENTORESERVAS,
				CN_UTILIZORESERVAS, CN_IDSYNCRESERVAS, CN_RESTANTESRESERVAS,
				CN_PENDIENTE, CN_AUTORIZADO, CN_TIPORESERVA };
		return db.query(TABLE_RESERVAS, columnas, null, null, null, null, null);
	}

	public Cursor BuscarReservaRut(String rut, String idprogramacion) {

		Cursor c = db.rawQuery(
				"SELECT * From RESERVAS WHERE IdProgramacionReservas ='"
						+ idprogramacion + "' AND RutFuncionarioReservas = '"
						+ rut + "'", null);
		return c;
	}

	public Cursor ReservasLocales(String idprogramacion, String rut) {

		Cursor c = db
				.rawQuery(
						"SELECT IDPROGRAMACIONRESERVAS, RUTFUNCIONARIORESERVAS, UTILIZORESERVAS, PENDIENTE FROM RESERVAS WHERE IDPROGRAMACIONRESERVAS = '"
								+ idprogramacion
								+ "' AND RUTFUNCIONARIORESERVAS = '"
								+ rut
								+ "'", null);
		return c;
	}

	public Cursor BuscarReservaUID(String UID, String idprogramacion) {

		Cursor c = db.rawQuery(
				"SELECT * From RESERVAS WHERE IdProgramacionReservas ='"
						+ idprogramacion + "' AND UidReservas = '" + UID + "'",
				null);

		return c;
	}

	public Boolean BuscarReservaIdProgramacion(String idprogramacion, String rut) {
		Cursor c = db.rawQuery(
				"SELECT * From RESERVAS WHERE IdProgramacionReservas ='"
						+ idprogramacion + "' AND RutFuncionarioReservas = '"
						+ rut + "'", null);
		Boolean estado = false;
		if (c.moveToFirst()) {
			estado = true;
		}
		return estado;
	}

	public int devolverIdReservasRut(String rut) {
		Cursor c = db.rawQuery(
				"SELECT * From RESERVAS WHERE RutFuncionarioReservas ='" + rut
						+ "'", null);
		int id = 0;
		if (c.moveToFirst()) {
			do {
				id = c.getInt(0);
			} while (c.moveToNext());
		}
		return id;
	}

	public int BuscarReservaciones(String idprogramacion) {
		int id = 0;
		Cursor c = db
				.rawQuery(
						"SELECT COUNT(*) From RESERVAS WHERE IdProgramacionReservas ='"
								+ idprogramacion
								+ "'AND TipoReserva = 'WEB' AND UtilizoReservas = 'NO'",
						null);
		if (c.moveToFirst()) {
			id = c.getInt(0);
		}
		return id;
	}

	public int BuscarReservasMovilPendites(String idprogramacion) {
		int cont = 0;

		Cursor c = db.rawQuery(
				"SELECT COUNT(*) From RESERVAS WHERE IdProgramacionReservas ='"
						+ idprogramacion
						+ "' AND TipoReserva = 'MOVIL' AND Autorizado= 'NO'",
				null);

		if (c.moveToFirst()) {
			cont = c.getInt(0);
		}
		return cont;
	}

	public int BuscarRegistrados(String idprogramacion) {
		int id = 0;
		Cursor c = db
				.rawQuery(
						"SELECT COUNT(*) From RESERVAS WHERE IdProgramacionReservas ='"
								+ idprogramacion
								+ "' AND UtilizoReservas = 'SI' AND TipoReserva = 'WEB'",
						null);
		if (c.moveToFirst()) {
			id = c.getInt(0);
		}
		return id;
	}

	public int BuscarAutorizadosMovil(String idprogramacion) {

		int cont = 0;

		Cursor c = db.rawQuery(
				"SELECT COUNT(*) From RESERVAS WHERE IdProgramacionReservas ='"
						+ idprogramacion
						+ "' AND TipoReserva = 'MOVIL' AND Autorizado= 'SI'",
				null);
		if (c.moveToFirst()) {
			cont = c.getInt(0);
		}
		return cont;
	}

	public void ActualizarUtilizoPendiente(String IdProgramacion,
			String rutfuncionario, String utilizo, String Pendiente) {
		//
		ContentValues Valores = new ContentValues();

		Valores.put(CN_UTILIZORESERVAS, utilizo);
		Valores.put(CN_PENDIENTE, Pendiente);

		db.update(TABLE_RESERVAS, Valores, CN_IDPROGRAMACIONRESERVAS + "="
				+ IdProgramacion + " AND " + CN_RUTFUNCIONARIORESERVAS + "="
				+ rutfuncionario, null);

	}

	public void ActualizarPendiente(String IdProgramacion,
			String rutfuncionario, String Pendiente) {

		ContentValues Valores = new ContentValues();

		Valores.put(CN_PENDIENTE, Pendiente);
		db.update(TABLE_RESERVAS, Valores, CN_IDPROGRAMACIONRESERVAS + "="
				+ IdProgramacion + " AND " + CN_RUTFUNCIONARIORESERVAS + "="
				+ rutfuncionario, null);
	}
	
	//#endregion

	/****************************** SINCRONIZACION RESERVAS *************************************/

	//#region TABLE SINCRIONIZACION RESERVAS
	
	public static final String TABLE_SINCRONIZACIONRESERVAS = "SINCRONIZACIONRESERVAS";
	public static final String CN_IDSINCRONIZACIONRESERVAS = "Id";
	public static final String CN_RUTFUNCIONARIOSYNC = "rutfuncionariosync";
	public static final String CN_IDSYNCSINCRESERVAS = "IdSync";
	public static final String CN_CONTADORSYNCRESERVA = "Contador";
	public static final String CN_ESTADOSYNCRESERVA = "EstadoSync"; // ON OFF
	public static final String CN_TIPOSYNCRESERVA = "TipoSync";// INICIAL,
																// INICIODIA,
	// INCREMENTAL

	public static final String CREATE_TABLE2 = "create table "
			+ TABLE_SINCRONIZACIONRESERVAS + " (" + CN_IDSINCRONIZACIONRESERVAS
			+ " integer primary key, " + CN_RUTFUNCIONARIOSYNC + " text, "
			+ CN_IDSYNCSINCRESERVAS + " text, " + CN_CONTADORSYNCRESERVA
			+ " int, " + CN_ESTADOSYNCRESERVA + " text, " + CN_TIPOSYNCRESERVA
			+ " text);";

	private ContentValues ValoresSincronizacionReservas(String Id, String rut,
			String IdSync, int Contador, String EstadoSync, String TipoSync) {

		ContentValues valores = new ContentValues();
		valores.put(CN_IDSINCRONIZACIONRESERVAS, Id);
		valores.put(CN_RUTFUNCIONARIOSYNC, rut);
		valores.put(CN_IDSYNCSINCRESERVAS, IdSync);
		valores.put(CN_CONTADORSYNCRESERVA, Contador);
		valores.put(CN_ESTADOSYNCRESERVA, EstadoSync);
		valores.put(CN_TIPOSYNCRESERVA, TipoSync);
		return valores;
	}

	public void InsertarDatosSincronizacionReserva(String rut, String IdSync,
			int Contador, String EstadoSync, String TipoSync) {

		db.insert(
				TABLE_SINCRONIZACIONRESERVAS,
				null,
				ValoresSincronizacionReservas("1", rut, IdSync, Contador,
						EstadoSync, TipoSync));
	}

	public Cursor CursorSincronizacionReservas() {
		String[] Columnas = new String[] { CN_IDSYNCSINCRESERVAS,
				CN_CONTADORSYNCRESERVA, CN_ESTADOSYNCRESERVA,
				CN_TIPOSYNCRESERVA };
		return db.query(TABLE_SINCRONIZACIONRESERVAS, Columnas,
				CN_IDSINCRONIZACIONRESERVAS + "=?", new String[] { "1" }, null,
				null, null);
	}

	public void ActualizarSincronizacionReservas(String rut, String IdSync,
			int Contador, String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_RUTFUNCIONARIOSYNC, rut);
		valores.put(CN_IDSYNCSINCRESERVAS, IdSync);
		valores.put(CN_CONTADORSYNCRESERVA, Contador);
		valores.put(CN_ESTADOSYNCRESERVA, EstadoSync);
		valores.put(CN_TIPOSYNCRESERVA, TipoSync);

		db.update(TABLE_SINCRONIZACIONRESERVAS, valores,
				CN_IDSINCRONIZACIONRESERVAS + "=" + "1", null);
	}

	public void ActualizarContadorSincronizacionReserva(int Contador,
			String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_CONTADORSYNCRESERVA, Contador);
		valores.put(CN_ESTADOSYNCRESERVA, EstadoSync);
		valores.put(CN_TIPOSYNCRESERVA, TipoSync);

		db.update(TABLE_SINCRONIZACIONRESERVAS, valores,
				CN_IDSINCRONIZACIONRESERVAS + "=" + "1", null);
	}
	
	//#endregion

	/****************************************** BUNDLE DATOS *****************************************************/
	
	//#region TABLE BUNDLE BUS
	
	public static final String TABLE_BUNDLEBUS = "BUNDLE_BUS";
	public static final String CN_IDBUNDLE = "Id";
	public static final String CN_BUNDLEIDPROGRAMACION = "rutfuncionariosync";
	public static final String CN_BUNDLEPLACA = "IdSync";

	public static final String CREATE_TABLE3 = "create table "
			+ TABLE_BUNDLEBUS + " (" + CN_IDBUNDLE + " integer primary key, "
			+ CN_BUNDLEIDPROGRAMACION + " text, " + CN_BUNDLEPLACA + " text);";

	private ContentValues ValoresBundle(String Id, String idprogramacion,
			String placa) {

		ContentValues valores = new ContentValues();
		valores.put(CN_IDBUNDLE, Id);
		valores.put(CN_BUNDLEIDPROGRAMACION, idprogramacion);
		valores.put(CN_BUNDLEPLACA, placa);

		return valores;
	}

	public void InsertarBundle(String idprogramacion, String placa) {

		db.insert(TABLE_BUNDLEBUS, null,
				ValoresBundle("1", idprogramacion, placa));
	}

	public Cursor CursorBundle() {
		String[] Columnas = new String[] { CN_IDBUNDLE,
				CN_BUNDLEIDPROGRAMACION, CN_BUNDLEPLACA };

		return db.query(TABLE_BUNDLEBUS, Columnas, CN_IDBUNDLE + "=?",
				new String[] { "1" }, null, null, null);
	}

	public void ActualizarBundle(String idprogramacion, String placa) {

		ContentValues valores = new ContentValues();
		valores.put(CN_BUNDLEIDPROGRAMACION, idprogramacion);
		valores.put(CN_BUNDLEPLACA, placa);

		db.update(TABLE_BUNDLEBUS, valores, CN_IDBUNDLE + "=" + "1", null);
	}

	//#endregion
	
	/******************************************** SINCRONIZACION RESERVAS ***********************************************/
	
	//#region TABLE SINCRONIZACION RESERVAS LOCALES

	public static final String TABLE_SINCRESERVASLOCALES = "SINC_RESERVAS_LOCALES";
	public static final String CN_IDSINCRESERVASLOCALES = "Id";
	public static final String CN_SINCRESERVASLOCALES_IDPROGRAMACION = "Programacion";
	public static final String CN_SINCRESERVASLOCALES_RUTFUNCIONARIO = "Rut";
	public static final String CN_SINCRESERVASLOCALES_UTILIZO = "Utilizo";
	public static final String CN_SINCRESERVASLOCALES_PENDIENTE = "Pendiente";

	public static final String CREATE_TABLE4 = "create table "
			+ TABLE_SINCRESERVASLOCALES + " (" + CN_IDSINCRESERVASLOCALES
			+ " integer primary key autoincrement, "
			+ CN_SINCRESERVASLOCALES_IDPROGRAMACION + " text, "
			+ CN_SINCRESERVASLOCALES_RUTFUNCIONARIO + " text, "
			+ CN_SINCRESERVASLOCALES_UTILIZO + " text, "
			+ CN_SINCRESERVASLOCALES_PENDIENTE + " text);";

	public void InsertarSincReservaLocales(String idprogramacion, String rut,
			String utilizo, String pendiente) {

		ContentValues valores = new ContentValues();
		valores.put(CN_SINCRESERVASLOCALES_IDPROGRAMACION, idprogramacion);
		valores.put(CN_SINCRESERVASLOCALES_RUTFUNCIONARIO, rut);
		valores.put(CN_SINCRESERVASLOCALES_UTILIZO, utilizo);
		valores.put(CN_SINCRESERVASLOCALES_PENDIENTE, pendiente);

		db.insert(TABLE_SINCRESERVASLOCALES, null, valores);
	}

	public Cursor CursorSincReservaLocales() {
		String[] columnas = new String[] {
				CN_SINCRESERVASLOCALES_IDPROGRAMACION,
				CN_SINCRESERVASLOCALES_RUTFUNCIONARIO,
				CN_SINCRESERVASLOCALES_UTILIZO,
				CN_SINCRESERVASLOCALES_PENDIENTE };
		return db.query(TABLE_SINCRESERVASLOCALES, columnas, null, null, null,
				null, null);

	}

	public void EliminarSincReservaLocales(String idprogramacion, String rut) {

		db.execSQL("DELETE FROM SINC_RESERVAS_LOCALES WHERE Programacion = '"
				+ idprogramacion + "' AND Rut = '" + rut + "'");
	}

	//#endregion
	
	/*****************************************************************************************/

	//#region TABLE FUNCIONARIO
	
	public static final String TABLE_FUNCIONARIO = "FUNCIONARIO";
	public static final String CN_ID = "_id";
	public static final String CN_UID = "UID";
	public static final String CN_ESTADOUID = "EstadoUID";
	public static final String CN_IDFUNCIONARIO = "IdFuncionario";
	public static final String CN_NOMBRE = "Nombre";
	public static final String CN_APELLIDO = "Apellido";
	public static final String CN_NOMBREEMPRESA = "NombreEmpresa";
	public static final String CN_IDEMPRESA = "IdEmpresa";
	public static final String CN_OSTFUN = "Ost";
	public static final String CN_CCOSTO = "CCosto";
	public static final String CN_TIPOPASEFUN = "TipoPase";
	public static final String CN_IMAGEN = "Imagen";
	public static final String CN_AUTORIZACION = "Autorizacion";
	public static final String CN_AUTORIZACIONCONDUCTOR = "AutorizacionConductor";
	public static final String CN_FECHACONSULTA = "FechaConsulta";
	public static final String CN_IDSYNCFUN = "idSYNC";

	public static final String CREATE_TABLE5 = "create table "
			+ TABLE_FUNCIONARIO + " (" + CN_ID
			+ " integer primary key autoincrement, " + CN_UID + " text, "
			+ CN_ESTADOUID + " text, " + CN_IDFUNCIONARIO + " text, "
			+ CN_NOMBRE + " text, " + CN_APELLIDO + " text, "
			+ CN_NOMBREEMPRESA + " text, " + CN_IDEMPRESA + " text, "
			+ CN_OSTFUN + " text," + CN_CCOSTO + " text, " + CN_TIPOPASEFUN
			+ " text, " + CN_IMAGEN + " text, " + CN_AUTORIZACION + " text, "
			+ CN_AUTORIZACIONCONDUCTOR + " text, " + CN_FECHACONSULTA
			+ " text, " + CN_IDSYNCFUN + " text);";

	// FUNCION QUE CONTIENE LOS VALORES A INSERTAR A LA TABLA FUNCIONARIO
	private ContentValues ValoresDatosFuncionario(String UID, String EstadoUID,
			String IdFuncionario, String Nombre, String Apellido,
			String NombreEmpresa, String IdEmpresa, String Ost, String CCosto,
			String TipoPase, String Imagen, String Autorizacion,
			String AutorizacionConductor, String FechaConsulta) {
		ContentValues valores = new ContentValues();
		valores.put(CN_UID, UID);
		valores.put(CN_ESTADOUID, EstadoUID);
		valores.put(CN_IDFUNCIONARIO, IdFuncionario);
		valores.put(CN_NOMBRE, Nombre);
		valores.put(CN_APELLIDO, Apellido);
		valores.put(CN_NOMBREEMPRESA, NombreEmpresa);
		valores.put(CN_IDEMPRESA, IdEmpresa);
		valores.put(CN_OSTFUN, Ost);
		valores.put(CN_CCOSTO, CCosto);
		valores.put(CN_TIPOPASEFUN, TipoPase);
		valores.put(CN_IMAGEN, Imagen);
		valores.put(CN_AUTORIZACION, Autorizacion);
		valores.put(CN_AUTORIZACIONCONDUCTOR, AutorizacionConductor);
		valores.put(CN_FECHACONSULTA, FechaConsulta);

		return valores;
	}

	// INSERCION EN TABLA FUNCIONARIO
	public void InsertarDatosFuncionario(String UID, String EstadoUID,
			String IdFuncionario, String Nombre, String Apellido,
			String NombreEmpresa, String IdEmpresa, String Ost, String CCosto,
			String TipoPase, String Imagen, String Autorizacion,
			String AutorizacionConductor, String FechaConsulta) {
		// db.insert(table, nullColumnHack, values); //Explicacion
		db.insert(
				TABLE_FUNCIONARIO,
				null,
				ValoresDatosFuncionario(UID, EstadoUID, IdFuncionario, Nombre,
						Apellido, NombreEmpresa, IdEmpresa, Ost, CCosto,
						TipoPase, Imagen, Autorizacion, AutorizacionConductor,
						FechaConsulta));
	}

	// ELIMINAR DE TABLA FUNCIONARIO, LA COLUMNA CUYO IDFUNCIONARIO CORRESPONDA
	public void EliminarDatosFuncionarioId(String IdFuncionario) {
		db.delete(TABLE_FUNCIONARIO, CN_IDFUNCIONARIO + "=?",
				new String[] { IdFuncionario });
	}

	public void EliminarDatosFuncionarioUID(String UIDFuncionario) {
		db.delete(TABLE_FUNCIONARIO, CN_UID + "=?",
				new String[] { UIDFuncionario });
	}

	// RETORNA LOS DATOS DE TABLA (SELECT *) CURSOR DE LA BASE DE DATOS PARA
	// PODER RECORRERLA
	public Cursor CargarCursorFuncionario() {
		String[] Columnas = new String[] { CN_UID, CN_ESTADOUID,
				CN_IDFUNCIONARIO, CN_NOMBRE, CN_APELLIDO, CN_NOMBREEMPRESA,
				CN_IDEMPRESA, CN_OSTFUN, CN_CCOSTO, CN_TIPOPASEFUN, CN_IMAGEN,
				CN_AUTORIZACION, CN_AUTORIZACIONCONDUCTOR, CN_FECHACONSULTA };
		// db.query(TABLE_NAME, columns, selection, selectionArgs, groupBy,
		// having, orderBy) //EJEMPLO DE DATOS
		return db.query(TABLE_FUNCIONARIO, Columnas, null, null, null, null,
				null);
	}

	// RETORNA TODOS LOS DATOS DEL FUNCIONARIO SEGUN EL IDFUNCIONARIO
	public Cursor BuscarFuncionarioId(String IdFuncionario) {
		String[] Columnas = new String[] { CN_UID, CN_ESTADOUID,
				CN_IDFUNCIONARIO, CN_NOMBRE, CN_APELLIDO, CN_NOMBREEMPRESA,
				CN_IDEMPRESA, CN_OSTFUN, CN_CCOSTO, CN_TIPOPASEFUN, CN_IMAGEN,
				CN_AUTORIZACION, CN_AUTORIZACIONCONDUCTOR, CN_FECHACONSULTA,
				CN_IDSYNCFUN };

		return db.query(TABLE_FUNCIONARIO, Columnas, CN_IDFUNCIONARIO + "=?",
				new String[] { IdFuncionario }, null, null, null);
	}

	// RETORNA TODOS LOS DATOS DEL FUNCIONARIO SEGUN EL UID DEL FUNCIONARIO
	public Cursor BuscarFuncionarioUID(String UID) {
		String[] Columnas = new String[] { CN_UID, CN_ESTADOUID,
				CN_IDFUNCIONARIO, CN_NOMBRE, CN_APELLIDO, CN_NOMBREEMPRESA,
				CN_IDEMPRESA, CN_OSTFUN, CN_CCOSTO, CN_TIPOPASEFUN, CN_IMAGEN,
				CN_AUTORIZACION, CN_AUTORIZACIONCONDUCTOR, CN_FECHACONSULTA };

		return db.query(TABLE_FUNCIONARIO, Columnas, CN_UID + "=?",
				new String[] { UID }, null, null, null);
	}

	public void ActualizarAutorizacionFuncionario(String IdFuncionario,
			String EstadoUID, String Autorizacion, String FechaConsulta,
			String AutorizacionConductor) {
		ContentValues valores = new ContentValues();
		valores.put(CN_ESTADOUID, EstadoUID);
		valores.put(CN_AUTORIZACION, Autorizacion);
		valores.put(CN_FECHACONSULTA, FechaConsulta);
		valores.put(CN_AUTORIZACIONCONDUCTOR, AutorizacionConductor);

		// Actualizamos el registro en la base de datos
		db.update(TABLE_FUNCIONARIO, valores, CN_IDFUNCIONARIO + "="
				+ IdFuncionario, null);
	}

	public int devolveridFuncionario(String idFuncionario) {
		Cursor c = db.rawQuery(
				"SELECT * From FUNCIONARIO WHERE IdFuncionario ='"
						+ idFuncionario + "'", null);
		int id = 0;
		if (c.moveToFirst()) {
			do {
				id = c.getInt(0);
			} while (c.moveToNext());
		}
		return id;
	}

	public void ActualizarDataFuncionario(String UID, String EstadoUID,
			String IdFuncionario, String Nombre, String Apellido,
			String NombreEmpresa, String IdEmpresa, String Ost, String CCosto,
			String TipoPase, String Imagen, String Autorizacion,
			String AutorizacionConductor, String FechaConsulta, int id) {
		ContentValues valores = new ContentValues();

		valores.put(CN_UID, UID);
		valores.put(CN_ESTADOUID, EstadoUID);
		valores.put(CN_NOMBRE, Nombre);
		valores.put(CN_APELLIDO, Apellido);
		valores.put(CN_NOMBREEMPRESA, NombreEmpresa);
		valores.put(CN_IDEMPRESA, IdEmpresa);
		valores.put(CN_OSTFUN, Ost);
		valores.put(CN_CCOSTO, CCosto);
		valores.put(CN_TIPOPASEFUN, TipoPase);
		valores.put(CN_IMAGEN, Imagen);
		valores.put(CN_AUTORIZACION, Autorizacion);
		valores.put(CN_AUTORIZACIONCONDUCTOR, AutorizacionConductor);
		valores.put(CN_FECHACONSULTA, FechaConsulta);

		// Actualizamos el registro en la base de datos
		db.update(TABLE_FUNCIONARIO, valores, CN_ID + "=" + id, null);
	}

	public void ActualizarDataFuncionarioPORID(String UID, String EstadoUID,
			int id, String Nombre, String Apellido, String NombreEmpresa,
			String IdEmpresa, String Ost, String CCosto, String TipoPase,
			String Imagen, String Autorizacion, String AutorizacionConductor,
			String FechaConsulta) {
		ContentValues valores = new ContentValues();

		valores.put(CN_UID, UID);
		valores.put(CN_ESTADOUID, EstadoUID);
		valores.put(CN_NOMBRE, Nombre);
		valores.put(CN_APELLIDO, Apellido);
		valores.put(CN_NOMBREEMPRESA, NombreEmpresa);
		valores.put(CN_IDEMPRESA, IdEmpresa);
		valores.put(CN_OSTFUN, Ost);
		valores.put(CN_CCOSTO, CCosto);
		valores.put(CN_TIPOPASEFUN, TipoPase);
		valores.put(CN_IMAGEN, Imagen);
		valores.put(CN_AUTORIZACION, Autorizacion);
		valores.put(CN_AUTORIZACIONCONDUCTOR, AutorizacionConductor);
		valores.put(CN_FECHACONSULTA, FechaConsulta);

		// Actualizamos el registro en la base de datos
		db.update(TABLE_FUNCIONARIO, valores, CN_ID + "=" + id, null);
	}

	public int traerIDFuncionario(String rut) {
		Cursor c = db.rawQuery(
				"SELECT _id FROM FUNCIONARIO WHERE IdFuncionario = '" + rut
						+ "'", null);
		int funcionario = 0;
		if (c.moveToFirst()) {
			do {
				funcionario = c.getInt(0);
			} while (c.moveToNext());
		}
		return funcionario;
	}

	public void ActualizarFechaConsultaFuncionario(String IdFuncionario,
			String FechaConsulta) {
		ContentValues valores = new ContentValues();
		valores.put(CN_FECHACONSULTA, FechaConsulta);

		// Actualizamos el registro en la base de datos
		db.update(TABLE_FUNCIONARIO, valores, CN_IDFUNCIONARIO + "="
				+ IdFuncionario, null);
	}

	//#endregion
	
	/*************************************** TABLE VEHICULOS ************************************************/
	
	//#region TABLE VEHICULO
	
	public static final String TABLE_VEHICULO = "VEHICULO";
	public static final String CN_ID2 = "_id";
	public static final String CN_UIDVEHICULO = "UID";
	public static final String CN_ESTADOUIDVEHICULO = "EstadoUID";
	public static final String CN_IDVEHICULO = "IdVehiculo";
	public static final String CN_MARCA = "Marca";
	public static final String CN_MODELO = "Modelo";
	public static final String CN_ANIO = "Anio";
	public static final String CN_TIPOVEHICULO = "TipoVehiculo";
	public static final String CN_CAPACIDADVEHICULO="Capacidad";
	public static final String CN_NOMBREEMPRESAVEHICULO = "NombreEmpresa";
	public static final String CN_IDEMPRESAVEHICULO = "IdEmpresa";
	public static final String CN_IMAGENVEHICULO = "Imagen";
	public static final String CN_AUTORIZACIONVEHICULO = "Autorizacion";
	public static final String CN_FECHACONSULTAVEHICULO = "FechaConsulta";
	public static final String CN_MENSAJEVEHICULO = "Mensaje";
	public static final String CN_IDSYNCVEHICULO = "Idsync";
	

	public static final String CREATE_TABLE6 = "create table " + TABLE_VEHICULO
			+ " (" + CN_ID2 + " integer primary key autoincrement, "
			+ CN_UIDVEHICULO + " text, " 
			+ CN_ESTADOUIDVEHICULO + " text, "
			+ CN_IDVEHICULO + " text, "
			+ CN_MARCA + " text, " 
			+ CN_MODELO	+ " text, " 
			+ CN_ANIO + " text, " 
			+ CN_TIPOVEHICULO + " text, "
			+ CN_CAPACIDADVEHICULO + " text, "
			+ CN_NOMBREEMPRESAVEHICULO + " text, " 
			+ CN_IDEMPRESAVEHICULO + " text, " 
			+ CN_IMAGENVEHICULO + " text, "
			+ CN_AUTORIZACIONVEHICULO + " text, " 
			+ CN_FECHACONSULTAVEHICULO	+ " text, " 
			+ CN_MENSAJEVEHICULO	+ " text, " 
			+ CN_IDSYNCVEHICULO + " text);";

	// FUNCION QUE CONTIENE LOS VALORES A INSERTAR A LA TABLA VEHICULO
	private ContentValues ValoresDatosVehiculo(String UID, String EstadoUID,
			String IdVehiculo, String Marca, String Modelo, String Anio,
			String TipoVehiculo, String capacidad, String NombreEmpresa, String IdEmpresa,
			String ImagenVehiculo, String AutorizacionVehiculo,
			String FechaConsulta, String Mensaje, String idsync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_UIDVEHICULO, UID);
		valores.put(CN_ESTADOUIDVEHICULO, EstadoUID);
		valores.put(CN_IDVEHICULO, IdVehiculo);
		valores.put(CN_MARCA, Marca);
		valores.put(CN_MODELO, Modelo);
		valores.put(CN_ANIO, Anio);
		valores.put(CN_TIPOVEHICULO, TipoVehiculo);
		valores.put(CN_CAPACIDADVEHICULO, capacidad);
		valores.put(CN_NOMBREEMPRESAVEHICULO, NombreEmpresa);
		valores.put(CN_IDEMPRESAVEHICULO, IdEmpresa);
		valores.put(CN_IMAGENVEHICULO, ImagenVehiculo);
		valores.put(CN_AUTORIZACIONVEHICULO, AutorizacionVehiculo);
		valores.put(CN_FECHACONSULTAVEHICULO, FechaConsulta);
		valores.put(CN_MENSAJEVEHICULO, Mensaje);
		valores.put(CN_IDSYNCVEHICULO, idsync);

		return valores;
	}

	// INSERCION EN TABLA VEHICULO
	public void InsertarDatosVehiculo(String UID, String EstadoUID,
			String IdVehiculo, String Marca, String Modelo, String Anio,
			String TipoVehiculo,String capacidad, String NombreEmpresa, String IdEmpresa,
			String ImagenVehiculo, String AutorizacionVehiculo,
			String FechaConsulta, String Mensaje, String idsync) {
		// db.insert(table, nullColumnHack, values); //Explicacion
		db.insert(
				TABLE_VEHICULO,
				null,
				ValoresDatosVehiculo(UID, EstadoUID, IdVehiculo, Marca, Modelo,
						Anio, TipoVehiculo,capacidad, NombreEmpresa, IdEmpresa,
						ImagenVehiculo, AutorizacionVehiculo, FechaConsulta,
						Mensaje,idsync));
	}

	// RETORNA TODOS LOS DATOS DEL VEHICULO SEGUN SEA SU ID
	public Cursor BuscarVehiculoId(String IdVehiculo) {
		String[] Columnas = new String[] { CN_ID2, CN_UIDVEHICULO,
				CN_ESTADOUIDVEHICULO, CN_IDVEHICULO, CN_MARCA, CN_MODELO,
				CN_ANIO, CN_TIPOVEHICULO, CN_CAPACIDADVEHICULO, CN_NOMBREEMPRESAVEHICULO,
				CN_IDEMPRESAVEHICULO, CN_IMAGENVEHICULO,
				CN_AUTORIZACIONVEHICULO, CN_FECHACONSULTAVEHICULO,
				CN_MENSAJEVEHICULO, CN_IDSYNCVEHICULO};

		return db.query(TABLE_VEHICULO, Columnas, CN_IDVEHICULO + "=?",
				new String[] { IdVehiculo }, null, null, null);
	}

	// RETORNA TODOS LOS DATOS DEL VEHICULO SEGUN SEA SU ID
	public Cursor BuscarVehiculoUID(String UIDVehiculo) {
		String[] Columnas = new String[] { CN_ID2, CN_UIDVEHICULO,
				CN_ESTADOUIDVEHICULO, CN_IDVEHICULO, CN_MARCA, CN_MODELO,
				CN_ANIO, CN_TIPOVEHICULO, CN_CAPACIDADVEHICULO, CN_NOMBREEMPRESAVEHICULO,
				CN_IDEMPRESAVEHICULO, CN_IMAGENVEHICULO,
				CN_AUTORIZACIONVEHICULO, CN_FECHACONSULTAVEHICULO,
				CN_MENSAJEVEHICULO,CN_IDSYNCVEHICULO };

		return db.query(TABLE_VEHICULO, Columnas, CN_UIDVEHICULO + "=?",
				new String[] { UIDVehiculo }, null, null, null);
	}

	public void ActualizarAutorizacionVehiculo(String IdVehiculo,
			String EstadoUID, String Autorizacion, String FechaConsulta,
			String Mensaje) {
		ContentValues valores = new ContentValues();
		valores.put(CN_ESTADOUIDVEHICULO, EstadoUID);
		valores.put(CN_AUTORIZACIONVEHICULO, Autorizacion);
		valores.put(CN_FECHACONSULTAVEHICULO, FechaConsulta);
		valores.put(CN_MENSAJEVEHICULO, Mensaje);

		// Actualizamos el registro en la base de datos
		db.update(TABLE_VEHICULO, valores, CN_ID2 + "=" + IdVehiculo, null);

		// db.execSQL("UPDATE "+TABLE_VEHICULO
		// +" set "+CN_AUTORIZACIONVEHICULO+" = \""+FechaConsulta.toString()
		// +"\" where _id = "+ IdVehiculo +";");
	}

	public void ActualizarDataVehiculo(int IdentificadorVehiculo, String UID,
			String EstadoUID, String IdVehiculo, String Marca, String Modelo,
			String Anio, String TipoVehiculo, String capacidad, String NombreEmpresa,
			String IdEmpresa, String ImagenVehiculo,
			String AutorizacionVehiculo, String FechaConsulta, String Mensaje, String idsync) {
		ContentValues valores = new ContentValues();

		valores.put(CN_UIDVEHICULO, UID);
		valores.put(CN_ESTADOUIDVEHICULO, EstadoUID);
		valores.put(CN_MARCA, Marca);
		valores.put(CN_MODELO, Modelo);
		valores.put(CN_ANIO, Anio);
		valores.put(CN_TIPOVEHICULO, TipoVehiculo);
		valores.put(CN_CAPACIDADVEHICULO, capacidad);
		valores.put(CN_NOMBREEMPRESAVEHICULO, NombreEmpresa);
		valores.put(CN_IDEMPRESAVEHICULO, IdEmpresa);
		valores.put(CN_IMAGENVEHICULO, ImagenVehiculo);
		valores.put(CN_AUTORIZACIONVEHICULO, AutorizacionVehiculo);
		valores.put(CN_FECHACONSULTAVEHICULO, FechaConsulta);
		valores.put(CN_MENSAJEVEHICULO, Mensaje);
		valores.put(CN_IDSYNCVEHICULO, idsync);


		db.update(TABLE_VEHICULO, valores,
				CN_ID2 + "=" + IdentificadorVehiculo, null);
	}

	public void ActualizarFechaConsultaVehiculo(String IdVehiculo,
			String FechaConsulta) {
		ContentValues valores = new ContentValues();
		valores.put(CN_FECHACONSULTAVEHICULO, FechaConsulta);

		// Actualizamos el registro en la base de datos
		db.update(TABLE_VEHICULO, valores, CN_ID2 + "=" + IdVehiculo, null);
		// db.execSQL("UPDATE "+TABLE_VEHICULO
		// +" set "+CN_FECHACONSULTAVEHICULO+" = \""+FechaConsulta.toString()
		// +"\" where _id = "+ IdVehiculo +";");
	}

	// ELIMINAR DE TABLA VEHICULO, LA COLUMNA CUYO IDVEHICULO CORRESPONDA
	public void EliminarDatosVehiculoId(String IdVehiculo) {
		db.delete(TABLE_VEHICULO, CN_IDVEHICULO + "=?",
				new String[] { IdVehiculo });
	}

	// ELIMINAR DE TABLA VEHICULO, LA COLUMNA CUYO IDVEHICULO CORRESPONDA
	public void EliminarDatosVehiculoUID(String UIDVehiculo) {
		db.delete(TABLE_VEHICULO, CN_UIDVEHICULO + "=?",
				new String[] { UIDVehiculo });
	}

	//#endregion
	
	/*********************************************************************************************/

	//#region TABLE SINCRONIZACION PERSONAS

	public static final String TABLE_SINCRONIZACIONPERSONAS = "SINCRONIZACION_PERSONAS";
	public static final String CN_IDSINCRONIZACIONPERSONAS = "Id";
	public static final String CN_RUTPERSONAS = "Rut";
	// public static final String CN_PLACAPERSONASYVEHICULOS = "Placa";
	// public static final String CN_IDSYNCGUARDIA = "IdSyncGuardia";
	public static final String CN_IDSYNCPERSONA = "IdSyncPersona";
	// public static final String CN_IDSYNCVEHICULO = "IdSyncVehiculo";
	public static final String CN_CONTADORSYNCPERSONAS = "Contador";
	public static final String CN_ESTADOSYNCPERSONAS = "EstadoSync"; // ON
	public static final String CN_TIPOSYNCPERSONAS = "TipoSync";// INICIAL,
																// INICIODIA,
	// INCREMENTAL

	public static final String CREATE_TABLE10 = "create table "
			+ TABLE_SINCRONIZACIONPERSONAS + " (" + CN_IDSINCRONIZACIONPERSONAS
			+ " integer primary key, " + CN_RUTPERSONAS + " text, "
			+ CN_IDSYNCPERSONA + " text, " + CN_CONTADORSYNCPERSONAS + " int, "
			+ CN_ESTADOSYNCPERSONAS + " text, " + CN_TIPOSYNCPERSONAS
			+ " text);";

	private ContentValues ValoresSincronizacionPersona(String Id, String Rut,
			String IdSyncPersona, int Contador, String EstadoSync,
			String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_IDSINCRONIZACIONPERSONAS, Id);
		valores.put(CN_RUTPERSONAS, Rut);
		// valores.put(CN_PLACAPERSONASYVEHICULOS, Placa);
		// valores.put(CN_IDSYNCGUARDIA, IdSyncGuardia);
		valores.put(CN_IDSYNCPERSONA, IdSyncPersona);
		// valores.put(CN_IDSYNCVEHICULO, IdSyncVehiculo);
		valores.put(CN_CONTADORSYNCPERSONAS, Contador);
		valores.put(CN_ESTADOSYNCPERSONAS, EstadoSync);
		valores.put(CN_TIPOSYNCPERSONAS, TipoSync);
		return valores;
	}

	// INSERCION EN TABLA SINCRONIZACION
	public void InsertarDatosSincronizacionPERSONAS(String Rut,
			String IdSyncPersona, int Contador, String EstadoSync,
			String TipoSync) {
		db.insert(
				TABLE_SINCRONIZACIONPERSONAS,
				null,
				ValoresSincronizacionPersona("1", Rut, IdSyncPersona, Contador,
						EstadoSync, TipoSync));
	}

	public Cursor CursorSincronizacionPersona() {
		String[] Columnas = new String[] { CN_RUTPERSONAS, CN_IDSYNCPERSONA,
				CN_CONTADORSYNCPERSONAS, CN_ESTADOSYNCPERSONAS,
				CN_TIPOSYNCPERSONAS };
		return db.query(TABLE_SINCRONIZACIONPERSONAS, Columnas,
				CN_IDSINCRONIZACIONPERSONAS + "=?", new String[] { "1" }, null,
				null, null);
	}

	// public Cursor CursorSincronizacionVehiculo() {
	// String[] Columnas = new String[] { CN_PLACAPERSONASYVEHICULOS,
	// CN_IDSYNCVEHICULO, CN_CONTADORSYNCPERSONASYVEHICULOS,
	// CN_ESTADOSYNCPERSONASYVEHICULOS, CN_TIPOSYNCPERSONASYVEHICULOS };
	// return db.query(TABLE_SINCRONIZACIONPERSONASYVEHICULOS, Columnas,
	// CN_IDSINCRONIZACIONPERSONASYVEHICULOS + "=?",
	// new String[] { "1" }, null, null, null);
	// }

	public void ActualizarSincronizacionPersona(String Rut, String IdSync,
			int Contador, String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_RUTPERSONAS, Rut);
		valores.put(CN_IDSYNCPERSONA, IdSync);
		valores.put(CN_CONTADORSYNCPERSONAS, Contador);
		valores.put(CN_ESTADOSYNCPERSONAS, EstadoSync);
		valores.put(CN_TIPOSYNCPERSONAS, TipoSync);

		db.update(TABLE_SINCRONIZACIONPERSONAS, valores,
				CN_IDSINCRONIZACIONPERSONAS + "=" + "1", null);
	}

	// public void ActualizarSincronizacionVehiculo(String Placa, String IdSync,
	// int Contador, String EstadoSync, String TipoSync) {
	// ContentValues valores = new ContentValues();
	// valores.put(CN_PLACAPERSONASYVEHICULOS, Placa);
	// valores.put(CN_IDSYNCVEHICULO, IdSync);
	// valores.put(CN_CONTADORSYNCPERSONASYVEHICULOS, Contador);
	// valores.put(CN_ESTADOSYNCPERSONASYVEHICULOS, EstadoSync);
	// valores.put(CN_TIPOSYNCPERSONASYVEHICULOS, TipoSync);
	//
	// db.update(TABLE_SINCRONIZACIONPERSONASYVEHICULOS, valores,
	// CN_IDSINCRONIZACIONPERSONASYVEHICULOS + "=" + "1", null);
	// }

	public void ActualizarContadorSincronizacionPERSONAS(int Contador,
			String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_CONTADORSYNCPERSONAS, Contador);
		valores.put(CN_ESTADOSYNCPERSONAS, EstadoSync);
		valores.put(CN_TIPOSYNCPERSONAS, TipoSync);

		db.update(TABLE_SINCRONIZACIONPERSONAS, valores,
				CN_IDSINCRONIZACIONPERSONAS + "=" + "1", null);
	}

	//#endregion
	
	/*************************************************************************************************************************/

	//#region TABLE REGISTRO SIN RESERVA
	
	public static final String TABLE_REGISTROSINRESERVA = "REGISTRO_SIN_RESERVA";
	public static final String CN_IDREGISTROSINRESERVA = "Id";
	public static final String CN_REGISTROSINRESERVAESTADO = "Estado";
	public static final String CN_REGISTROSINRESERVAIDPROGRAMACION = "Idprogramcion";

	public static final String CREATE_TABLE11 = "create table "
			+ TABLE_REGISTROSINRESERVA + " (" + CN_IDREGISTROSINRESERVA
			+ " integer primary key, " + CN_REGISTROSINRESERVAESTADO
			+ " text, " + CN_REGISTROSINRESERVAIDPROGRAMACION + " text);";

	private ContentValues ValoresRegitroSinReserva(String Id, String Estado,
			String idprogramacion) {

		ContentValues valores = new ContentValues();
		valores.put(CN_IDREGISTROSINRESERVA, Id);
		valores.put(CN_REGISTROSINRESERVAESTADO, Estado);
		valores.put(CN_REGISTROSINRESERVAIDPROGRAMACION, idprogramacion);

		return valores;
	}

	public void InsertarRegitroSinReserva(String estado, String idprogramacion) {

		db.insert(TABLE_REGISTROSINRESERVA, null,
				ValoresRegitroSinReserva("1", estado, idprogramacion));
	}

	public Cursor CursorBRegitroSinReserva() {
		String[] Columnas = new String[] { CN_IDREGISTROSINRESERVA,
				CN_REGISTROSINRESERVAESTADO,
				CN_REGISTROSINRESERVAIDPROGRAMACION };

		return db.query(TABLE_REGISTROSINRESERVA, Columnas,
				CN_IDREGISTROSINRESERVA + "=?", new String[] { "1" }, null,
				null, null);
	}

	public void ActualizarRegitroSinReserva(String estado, String idprogramacion) {

		ContentValues valores = new ContentValues();
		valores.put(CN_REGISTROSINRESERVAESTADO, estado);
		valores.put(CN_REGISTROSINRESERVAIDPROGRAMACION, idprogramacion);

		db.update(TABLE_REGISTROSINRESERVA, valores, CN_IDREGISTROSINRESERVA
				+ "=" + "1", null);
	}

	
	//#endregion
	
	/********************************************************************************************************/

	//#region TABLE SINCRONIZACION VEHICULOS
	
	public static final String TABLE_SINCRONIZACIONVEHICULOS = "SINCRONIZACION_VEHICULOS";
	public static final String CN_IDSINCRONIZACIONVEHICULOS = "Id";
	public static final String CN_PLACA_VEHICULOS = "placa";
	public static final String CN_IDSYNC_VEHICULOS = "IdSyncVehiculo";
	public static final String CN_CONTADORSYNCVEHICULOS = "Contador";
	public static final String CN_ESTADOSYNCVEHICULOS = "EstadoSync";
	public static final String CN_TIPOSYNCVEHICULOS = "TipoSync";

	public static final String CREATE_TABLE16 = "create table "
			+ TABLE_SINCRONIZACIONVEHICULOS + " ("
			+ CN_IDSINCRONIZACIONVEHICULOS + " integer primary key, "
			+ CN_PLACA_VEHICULOS + " text, " + CN_IDSYNC_VEHICULOS + " text, "
			+ CN_CONTADORSYNCVEHICULOS + " int, " + CN_ESTADOSYNCVEHICULOS
			+ " text, " + CN_TIPOSYNCVEHICULOS + " text);";

	private ContentValues ValoresSincronizacionVEHICULOS(String Id,
			String placa, String IdSync, int Contador, String EstadoSync,
			String TipoSync) {

		ContentValues valores = new ContentValues();
		valores.put(CN_IDSINCRONIZACIONVEHICULOS, Id);
		valores.put(CN_PLACA_VEHICULOS, placa);
		valores.put(CN_IDSYNC_VEHICULOS, IdSync);
		valores.put(CN_CONTADORSYNCVEHICULOS, Contador);
		valores.put(CN_ESTADOSYNCVEHICULOS, EstadoSync);
		valores.put(CN_TIPOSYNCVEHICULOS, TipoSync);
		return valores;
	}

	public void InsertarDatosSincronizacionVEHICULOS(String placa,
			String IdSync, int Contador, String EstadoSync, String TipoSync) {

		db.insert(
				TABLE_SINCRONIZACIONVEHICULOS,
				null,
				ValoresSincronizacionVEHICULOS("1", placa, IdSync, Contador,
						EstadoSync, TipoSync));
	}

	public Cursor CursorSincronizacionVehiculo() {
		String[] Columnas = new String[] { CN_PLACA_VEHICULOS,
				CN_IDSYNC_VEHICULOS, CN_CONTADORSYNCVEHICULOS,
				CN_ESTADOSYNCVEHICULOS, CN_TIPOSYNCVEHICULOS };

		return db.query(TABLE_SINCRONIZACIONVEHICULOS, Columnas,
				CN_IDSINCRONIZACIONVEHICULOS + "=?", new String[] { "1" },
				null, null, null);
	}

	public void ActualizarSincronizacionVEHICULOS(String placa, String IdSync,
			int Contador, String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_PLACA_VEHICULOS, placa);
		valores.put(CN_IDSYNC_VEHICULOS, IdSync);
		valores.put(CN_CONTADORSYNCVEHICULOS, Contador);
		valores.put(CN_ESTADOSYNCVEHICULOS, EstadoSync);
		valores.put(CN_TIPOSYNCVEHICULOS, TipoSync);

		db.update(TABLE_SINCRONIZACIONVEHICULOS, valores,
				CN_IDSINCRONIZACIONVEHICULOS + "=" + "1", null);
	}

	public void ActualizarContadorSincronizacionVEHICULOS(int Contador,
			String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_CONTADORSYNCVEHICULOS, Contador);
		valores.put(CN_ESTADOSYNCVEHICULOS, EstadoSync);
		valores.put(CN_TIPOSYNCVEHICULOS, TipoSync);

		db.update(TABLE_SINCRONIZACIONVEHICULOS, valores,
				CN_IDSINCRONIZACIONVEHICULOS + "=" + "1", null);
	}

	public void ActualizarSincronizacionVehiculoFin(String EstadoSync,
			String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_ESTADOSYNCVEHICULOS, EstadoSync);
		valores.put(CN_TIPOSYNCVEHICULOS, TipoSync);

		db.update(TABLE_SINCRONIZACIONVEHICULOS, valores,
				CN_IDSINCRONIZACIONVEHICULOS + "=" + "1", null);
	}
	
	//#endregion

	/******************************************** SINCRONIZACION_PERSONAS_SERVICE ************************************************/

	//#region TABLE SINCRONIZACION PERSONAS SERVICE

	public static final String TABLE_SINCRONIZACIONPERSONASSERVICE = "SINCRONIZACION_PERSONAS_SERVICE";
	public static final String CN_IDSINCRONIZACIONPERSONASSERVICE = "Id";
	public static final String CN_RUTPERSONASSERVICE = "Rut";
	public static final String CN_IDSYNCPERSONASERVICE = "IdSyncPersona";

	public static final String CN_CONTADORSYNCPERSONASSERVICE = "Contador";
	public static final String CN_ESTADOSYNCPERSONASSERVICE = "EstadoSync"; // ON
	public static final String CN_TIPOSYNCPERSONASSERVICE = "TipoSync";// INICIAL,
	// INICIODIA,
	// INCREMENTAL

	public static final String CREATE_TABLE12 = "create table "
			+ TABLE_SINCRONIZACIONPERSONASSERVICE + " ("
			+ CN_IDSINCRONIZACIONPERSONAS + " integer primary key, "
			+ CN_RUTPERSONAS + " text, " + CN_IDSYNCPERSONA + " text, "
			+ CN_CONTADORSYNCPERSONAS + " int, " + CN_ESTADOSYNCPERSONAS
			+ " text, " + CN_TIPOSYNCPERSONAS + " text);";

	private ContentValues ValoresSincronizacionPersonaSERVICE(String Id,
			String Rut, String IdSyncPersona, int Contador, String EstadoSync,
			String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_IDSINCRONIZACIONPERSONAS, Id);
		valores.put(CN_RUTPERSONAS, Rut);
		valores.put(CN_IDSYNCPERSONA, IdSyncPersona);
		valores.put(CN_CONTADORSYNCPERSONAS, Contador);
		valores.put(CN_ESTADOSYNCPERSONAS, EstadoSync);
		valores.put(CN_TIPOSYNCPERSONAS, TipoSync);
		return valores;
	}

	// INSERCION EN TABLA SINCRONIZACION
	public void InsertarDatosSincronizacionPERSONASSERVICE(String Rut,
			String IdSyncPersona, int Contador, String EstadoSync,
			String TipoSync) {
		db.insert(
				TABLE_SINCRONIZACIONPERSONASSERVICE,
				null,
				ValoresSincronizacionPersona("1", Rut, IdSyncPersona, Contador,
						EstadoSync, TipoSync));
	}

	public Cursor CursorSincronizacionPersonaSERVICE() {
		String[] Columnas = new String[] { CN_RUTPERSONAS, CN_IDSYNCPERSONA,
				CN_CONTADORSYNCPERSONAS, CN_ESTADOSYNCPERSONAS,
				CN_TIPOSYNCPERSONAS };
		return db.query(TABLE_SINCRONIZACIONPERSONASSERVICE, Columnas,
				CN_IDSINCRONIZACIONPERSONAS + "=?", new String[] { "1" }, null,
				null, null);
	}

	public void ActualizarSincronizacionPersonaSERVICE(String Rut,
			String IdSync, int Contador, String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_RUTPERSONAS, Rut);
		valores.put(CN_IDSYNCPERSONA, IdSync);
		valores.put(CN_CONTADORSYNCPERSONAS, Contador);
		valores.put(CN_ESTADOSYNCPERSONAS, EstadoSync);
		valores.put(CN_TIPOSYNCPERSONAS, TipoSync);

		db.update(TABLE_SINCRONIZACIONPERSONASSERVICE, valores,
				CN_IDSINCRONIZACIONPERSONAS + "=" + "1", null);
	}

	public void ActualizarContadorSincronizacionPERSONASSERVICE(int Contador,
			String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_CONTADORSYNCPERSONAS, Contador);
		valores.put(CN_ESTADOSYNCPERSONAS, EstadoSync);
		valores.put(CN_TIPOSYNCPERSONAS, TipoSync);

		db.update(TABLE_SINCRONIZACIONPERSONASSERVICE, valores,
				CN_IDSINCRONIZACIONPERSONAS + "=" + "1", null);
	}

	//#endregion
	
	/********************************************************************************************************/

	//#region TABLE SINCRONIZACION VEHICULOS SERVICE
	
	public static final String TABLE_SINCRONIZACIONVEHICULOSSERVICE = "SINCRONIZACION_VEHICULOS_SERVICE";
	public static final String CN_IDSINCRONIZACIONVEHICULOSSERVICE = "Id";
	public static final String CN_PLACA_VEHICULOSSERVICE = "placa";
	public static final String CN_IDSYNC_VEHICULOSSERVICE = "IdSyncVehiculo";
	public static final String CN_CONTADORSYNCVEHICULOSSERVICE = "Contador";
	public static final String CN_ESTADOSYNCVEHICULOSSERVICE = "EstadoSync";
	public static final String CN_TIPOSYNCVEHICULOSSERVICE = "TipoSync";

	public static final String CREATE_TABLE13 = "create table "
			+ TABLE_SINCRONIZACIONVEHICULOSSERVICE + " ("
			+ CN_IDSINCRONIZACIONVEHICULOS + " integer primary key, "
			+ CN_PLACA_VEHICULOS + " text, " + CN_IDSYNC_VEHICULOS + " text, "
			+ CN_CONTADORSYNCVEHICULOS + " int, " + CN_ESTADOSYNCVEHICULOS
			+ " text, " + CN_TIPOSYNCVEHICULOS + " text);";

	private ContentValues ValoresSincronizacionVEHICULOSSERVICE(String Id,
			String placa, String IdSync, int Contador, String EstadoSync,
			String TipoSync) {

		ContentValues valores = new ContentValues();
		valores.put(CN_IDSINCRONIZACIONVEHICULOS, Id);
		valores.put(CN_PLACA_VEHICULOS, placa);
		valores.put(CN_IDSYNC_VEHICULOS, IdSync);
		valores.put(CN_CONTADORSYNCVEHICULOS, Contador);
		valores.put(CN_ESTADOSYNCVEHICULOS, EstadoSync);
		valores.put(CN_TIPOSYNCVEHICULOS, TipoSync);
		return valores;
	}

	public void InsertarDatosSincronizacionVEHICULOSSERVICE(String placa,
			String IdSync, int Contador, String EstadoSync, String TipoSync) {

		db.insert(
				TABLE_SINCRONIZACIONVEHICULOSSERVICE,
				null,
				ValoresSincronizacionVEHICULOS("1", placa, IdSync, Contador,
						EstadoSync, TipoSync));
	}

	public Cursor CursorSincronizacionVehiculoSERVICE() {
		String[] Columnas = new String[] { CN_PLACA_VEHICULOS,
				CN_IDSYNC_VEHICULOS, CN_CONTADORSYNCVEHICULOS,
				CN_ESTADOSYNCVEHICULOS, CN_TIPOSYNCVEHICULOS };

		return db.query(TABLE_SINCRONIZACIONVEHICULOSSERVICE, Columnas,
				CN_IDSINCRONIZACIONVEHICULOS + "=?", new String[] { "1" },
				null, null, null);
	}

	public void ActualizarSincronizacionVEHICULOSSERVICE(String placa,
			String IdSync, int Contador, String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_PLACA_VEHICULOS, placa);
		valores.put(CN_IDSYNC_VEHICULOS, IdSync);
		valores.put(CN_CONTADORSYNCVEHICULOS, Contador);
		valores.put(CN_ESTADOSYNCVEHICULOS, EstadoSync);
		valores.put(CN_TIPOSYNCVEHICULOS, TipoSync);

		db.update(TABLE_SINCRONIZACIONVEHICULOSSERVICE, valores,
				CN_IDSINCRONIZACIONVEHICULOS + "=" + "1", null);
	}

	public void ActualizarContadorSincronizacionVEHICULOSSERVICE(int Contador,
			String EstadoSync, String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_CONTADORSYNCVEHICULOS, Contador);
		valores.put(CN_ESTADOSYNCVEHICULOS, EstadoSync);
		valores.put(CN_TIPOSYNCVEHICULOS, TipoSync);

		db.update(TABLE_SINCRONIZACIONVEHICULOSSERVICE, valores,
				CN_IDSINCRONIZACIONVEHICULOS + "=" + "1", null);
	}

	public void ActualizarSincronizacionVehiculoFinSERVICE(String EstadoSync,
			String TipoSync) {
		ContentValues valores = new ContentValues();
		valores.put(CN_ESTADOSYNCVEHICULOS, EstadoSync);
		valores.put(CN_TIPOSYNCVEHICULOS, TipoSync);

		db.update(TABLE_SINCRONIZACIONVEHICULOSSERVICE, valores,
				CN_IDSINCRONIZACIONVEHICULOS + "=" + "1", null);
	}
	
	//#endregion

	/********************************************************************************************************************/

	//#region TABLE USUARIOS RESERVAS
	
	public static final String TABLA_USUARIOS_RESERVAS = "USUARIOS_RESERVAS";
	public static final String COLUMNA_ID = "_id";
	public static final String COLUMNA_TEXTO = "nombre";
	public static final String RUTFUNCIONARIORESERVAS = "RutFuncionarioReservas";
	public static final String NOMBREFUNCIONARIORESERVAS = "NombreFuncionarioReservas";
	public static final String APELLIDOFUNCIONARIORESERVAS = "ApellidoFuncionarioReservas";
	public static final String RUTEMPRESARESERVAS = "RutEmpresaReservas";
	public static final String NOMBREEMPRESARESERVAS = "NombreEmpresaReservas";
	public static final String ESTADOUIDRESERVAS = "EstadoUidReservas";
	public static final String UTILIZORESERVAS = "UtilizoReservas";
	public static final String PENDIENTE = "Pendiente";
	public static final String AUTORIZADO = "Autorizado";
	public static final String TIPORESERVA = "TipoReserva";

	public static final String CREATE_TABLE17 = "create table "
			+ TABLA_USUARIOS_RESERVAS + " (" + COLUMNA_ID
			+ " integer primary key, " + COLUMNA_TEXTO + " text, "
			+ RUTFUNCIONARIORESERVAS + " int, " + APELLIDOFUNCIONARIORESERVAS
			+ " text, " + RUTEMPRESARESERVAS + " int, " + NOMBREEMPRESARESERVAS
			+ " text, " + ESTADOUIDRESERVAS + " text, " + UTILIZORESERVAS
			+ " text, " + PENDIENTE + " text, " + AUTORIZADO + " text, "
			+ TIPORESERVA + " text);";

	public void Insertar(String nombre, String RutFuncionarioReservas,
			String ApellidoFuncionarioReservas, String RutEmpresaReservas,
			String NombreEmpresaReservas, String EstadoUidReservas,
			String UtilizoReservas, String Pendiente, String Autorizado,
			String TipoReserva) {

		ContentValues valores = new ContentValues();
		valores.put(COLUMNA_TEXTO, nombre);
		valores.put(RUTFUNCIONARIORESERVAS, RutFuncionarioReservas);
		valores.put(APELLIDOFUNCIONARIORESERVAS, ApellidoFuncionarioReservas);
		valores.put(RUTEMPRESARESERVAS, RutEmpresaReservas);
		valores.put(NOMBREEMPRESARESERVAS, NombreEmpresaReservas);
		// valores.put(AUTORIZACIONACCESORESERVAS, AutorizacionAccesoReservas);
		// valores.put(UIDRESERVAS, UidReservas);
		valores.put(ESTADOUIDRESERVAS, EstadoUidReservas);
		// valores.put(ASIENTORESERVAS, AsientoReservas);
		valores.put(UTILIZORESERVAS, UtilizoReservas);
		// valores.put(RESTANTESRESERVAS, RestantesReservas);
		valores.put(PENDIENTE, Pendiente);
		valores.put(AUTORIZADO, Autorizado);
		valores.put(TIPORESERVA, TipoReserva);
		db.insert(TABLA_USUARIOS_RESERVAS, null, valores);
	}

	public Cursor BuscarUsuarioNO(String IdProgramador) {
		Cursor a = db.rawQuery(
				"SELECT * FROM RESERVAS WHERE IdProgramacionReservas='"
						+ IdProgramador
						+ "' AND UtilizoReservas='NO' AND TipoReserva='WEB'",
				null);
		return a;
	}

	public Cursor BuscarUsuario(int posicion) {
		Cursor c = db.rawQuery("SELECT * FROM USUARIOS_RESERVAS WHERE  _id='"
				+ posicion + "' ", null);
		return c;
	}

	public void deleteUsuariosSi() {
		db.delete(TABLA_USUARIOS_RESERVAS, " _id < 100 ", null);
	}

	public Cursor BuscarPatente(String Fecha) {
		Cursor c = db.rawQuery("SELECT * From DATOSBUS WHERE Fecha = '" + Fecha
				+ "'", null);
		return c;
	}

	public List<ListadoPasajeros> getAllNO(String IdProgramador) {
		List<ListadoPasajeros> listarUsu = new ArrayList<ListadoPasajeros>();

		Cursor cursor = db.rawQuery(
				"SELECT * FROM RESERVAS WHERE IdProgramacionReservas='"
						+ IdProgramador
						+ "' AND UtilizoReservas='NO' AND TipoReserva='WEB' ",
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ListadoPasajeros nuevo = cursorToNota(cursor);
			listarUsu.add(nuevo);
			cursor.moveToNext();
		}
		cursor.close();
		return listarUsu;
	}

	private ListadoPasajeros cursorToNota(Cursor cursor) {
		ListadoPasajeros dato = new ListadoPasajeros();

		dato.setNombre(cursor.getString(3) + " " + cursor.getString(4));
		return dato;
	}

	public Cursor BuscarUsuarioSI(String IdProgramador) {
		Cursor b = db.rawQuery(
				"SELECT * FROM RESERVAS WHERE IdProgramacionReservas='"
						+ IdProgramador
						+ "' AND UtilizoReservas='SI' AND TipoReserva='WEB'",
				null);
		return b;

	}

	public List<ListadoPasajeros> getAllSI(String IdProgramador) {
		List<ListadoPasajeros> listarUsu = new ArrayList<ListadoPasajeros>();

		Cursor cursor = db.rawQuery(
				"SELECT * FROM RESERVAS WHERE IdProgramacionReservas='"
						+ IdProgramador
						+ "' AND UtilizoReservas='SI' AND TipoReserva='WEB' ",
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ListadoPasajeros nuevo = cursorToNota(cursor);
			listarUsu.add(nuevo);
			cursor.moveToNext();
		}
		cursor.close();
		return listarUsu;
	}

	public Cursor Buscar(String IdProgramador) {
		Cursor c = db.rawQuery(
				"SELECT * From RESERVAS WHERE IdProgramacionReservas='"
						+ IdProgramador + "'", null);
		return c;
	}

	public List<ListadoPasajeros> getAll(String IdProgramador, String Utilizo,
			String TipoReserva) {
		List<ListadoPasajeros> listarUsu = new ArrayList<ListadoPasajeros>();

		Cursor cursor = db.rawQuery(
				"SELECT * FROM RESERVAS WHERE IdProgramacionReservas='"
						+ IdProgramador + "' AND UtilizoReservas='" + Utilizo
						+ "' AND TipoReserva='" + TipoReserva + "' ", null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ListadoPasajeros nuevo = cursorToNota(cursor);
			listarUsu.add(nuevo);
			cursor.moveToNext();
		}
		cursor.close();
		return listarUsu;
	}

	public Cursor BuscarUsuarioReservas(String IdProgramador, String dato,
			String TipoReserva) {
		Cursor a = db.rawQuery(
				"SELECT * FROM RESERVAS WHERE IdProgramacionReservas='"
						+ IdProgramador + "' AND UtilizoReservas='" + dato
						+ "' AND TipoReserva='" + TipoReserva + "' ", null);
		return a;
	}

	public Cursor Usuario() {
		Cursor c = db.rawQuery("SELECT * FROM USUARIOS_RESERVAS WHERE  ", null);
		return c;
	}
	
	//#endregion

	/****************************************************************************************************************************************/

	//#region TABLE VALOR MINUTOS DE ESPERA
	
	public static final String TABLE_VALORMINUTOS = "VALORMINUTOS";
	public static final String CN_IDVALORMINUTOS = "Id";
	public static final String CN_VALORMINUTOS = "Minutos";
	// public static final String CN_VALORMINUTOS = "IdSync";

	public static final String CREATE_TABLE14 = "create table "
			+ TABLE_VALORMINUTOS + " (" + CN_IDVALORMINUTOS
			+ " integer primary key, " + CN_VALORMINUTOS + " text);";

	private ContentValues ValoresVALORMINUTOS(String Id, String minutos) {

		ContentValues valores = new ContentValues();
		valores.put(CN_IDVALORMINUTOS, Id);
		valores.put(CN_VALORMINUTOS, minutos);

		return valores;
	}

	public void InsertarVALORMINUTOS(String minutos) {

		db.insert(TABLE_VALORMINUTOS, null, ValoresVALORMINUTOS("1", minutos));
	}

	public Cursor CursorVALORMINUTOS() {
		String[] Columnas = new String[] { CN_IDVALORMINUTOS, CN_VALORMINUTOS };

		return db.query(TABLE_VALORMINUTOS, Columnas, CN_IDVALORMINUTOS + "=?",
				new String[] { "1" }, null, null, null);
	}

	public void ActualizarVALORMINUTOS(String minutos) {

		ContentValues valores = new ContentValues();

		valores.put(CN_VALORMINUTOS, minutos);

		db.update(TABLE_VALORMINUTOS, valores, CN_IDVALORMINUTOS + "=" + "1",
				null);
	}
	
	//#endregion

	/****************************************************************************************************************************************/

	//#region TABLE HABILITAR SINCRONIZACION AUTOMATICA
	
	public static final String TABLE_HABILITARSINCRONIZACIONAUTOMATICA = "HABILITAR_SINCRONIZACION_AUTO";
	public static final String CN_IDHABILITARINCRONIZACIONAUTOMATICA = "Id";
	public static final String CN_HABILITAR = "habilitar";

	public static final String CREATE_TABLE15 = "create table "
			+ TABLE_HABILITARSINCRONIZACIONAUTOMATICA + " ("
			+ CN_IDHABILITARINCRONIZACIONAUTOMATICA + " integer primary key, "
			+ CN_HABILITAR + " text);";

	private ContentValues ValoresHABILITAR_SINCRONIZACIONAUTOMATICA(String Id,
			String habilitar) {

		ContentValues valores = new ContentValues();
		valores.put(CN_IDHABILITARINCRONIZACIONAUTOMATICA, Id);
		valores.put(CN_HABILITAR, habilitar);

		return valores;
	}

	public void InsertarHABILITAR_SINCRONIZACIONAUTOMATICA(String habilitar) {

		db.insert(TABLE_HABILITARSINCRONIZACIONAUTOMATICA, null,
				ValoresHABILITAR_SINCRONIZACIONAUTOMATICA("1", habilitar));
	}

	public Cursor CursorHABILITAR_SINCRONIZACIONAUTOMATICA() {
		String[] Columnas = new String[] {
				CN_IDHABILITARINCRONIZACIONAUTOMATICA, CN_HABILITAR };

		return db.query(TABLE_HABILITARSINCRONIZACIONAUTOMATICA, Columnas,
				CN_IDHABILITARINCRONIZACIONAUTOMATICA + "=?",
				new String[] { "1" }, null, null, null);
	}

	public void ActualizarHABILITAR_SINCRONIZACIONAUTOMATICA(String habilitar) {
		ContentValues valores = new ContentValues();
		valores.put(CN_HABILITAR, habilitar);

		db.update(TABLE_HABILITARSINCRONIZACIONAUTOMATICA, valores,
				CN_IDHABILITARINCRONIZACIONAUTOMATICA + "=" + "1", null);
	}

	//#endregion
	
	/*******************************************************************************************************************************************/

	//#region TABLE TRASLADO PASAJEROS
	
	public static final String TABLE_TRASLADOPASAJEROS = "TRASLADO_PASAJEROS";
	public static final String CN_ID_TRASLADOPASAJEROS = "Id";
	public static final String CN_PATENTEORIGEN_TRASLADOPASAJEROS = "Patente_Origen";
	public static final String CN_PATENTEDESTINO_TRASLADOPASAJEROS = "Patente_Destino";
	public static final String CN_RUTFUNCIONARIO_TRASLADO_PASAJEROS = "RutFuncionario";
	public static final String CN_FECHA_TRASLADO_PASAJEROS = "Fecha";
	public static final String CN_HORA_TRASLADO_PASAJEROS = "Hora";

	public static final String CREATE_TABLE18 = "create table "
			+ TABLE_TRASLADOPASAJEROS + " (" + CN_ID_TRASLADOPASAJEROS
			+ " integer primary key, " + CN_PATENTEORIGEN_TRASLADOPASAJEROS
			+ " text, " + CN_PATENTEDESTINO_TRASLADOPASAJEROS + " text, "
			+ CN_RUTFUNCIONARIO_TRASLADO_PASAJEROS + " text, "
			+ CN_FECHA_TRASLADO_PASAJEROS + " text, "
			+ CN_HORA_TRASLADO_PASAJEROS + " text);";

	private ContentValues ValoresDatosTrasladoPasajero(String PatenteOrigen,
			String PatenteDestino, String RutFuncionario, String fecha,
			String hora) {

		ContentValues valores = new ContentValues();
		valores.put(CN_PATENTEORIGEN_TRASLADOPASAJEROS, PatenteOrigen);
		valores.put(CN_PATENTEDESTINO_TRASLADOPASAJEROS, PatenteDestino);
		valores.put(CN_RUTFUNCIONARIO_TRASLADO_PASAJEROS, RutFuncionario);
		valores.put(CN_FECHA_TRASLADO_PASAJEROS, fecha);
		valores.put(CN_HORA_TRASLADO_PASAJEROS, hora);

		return valores;
	}

	public void InsertarTrasladoPasajeros(String PatenteOrigen,
			String PatenteDestino, String RutFuncionario, String fecha,
			String hora) {

		db.insert(
				TABLE_TRASLADOPASAJEROS,
				null,
				ValoresDatosTrasladoPasajero(PatenteOrigen, PatenteDestino,
						RutFuncionario, fecha, hora));
	}

	public void ActualizarTrasladoPasajeros(int Id, String PatenteOrigen,
			String PatenteDestino, String RutFuncionario, String fecha,
			String hora) {

		ContentValues valores = new ContentValues();
		valores.put(CN_PATENTEORIGEN_TRASLADOPASAJEROS, PatenteOrigen);
		valores.put(CN_PATENTEDESTINO_TRASLADOPASAJEROS, PatenteDestino);
		valores.put(CN_RUTFUNCIONARIO_TRASLADO_PASAJEROS, RutFuncionario);
		valores.put(CN_FECHA_TRASLADO_PASAJEROS, fecha);
		valores.put(CN_HORA_TRASLADO_PASAJEROS, hora);

		db.update(TABLE_TRASLADOPASAJEROS, valores, CN_ID_TRASLADOPASAJEROS
				+ "=" + Id, null);
	}
	
	 public Cursor BuscarTrasladoPasajeros() {
	 String[] Columnas = new String[] { CN_ID_TRASLADOPASAJEROS, CN_PATENTEORIGEN_TRASLADOPASAJEROS,
			 CN_PATENTEDESTINO_TRASLADOPASAJEROS, CN_RUTFUNCIONARIO_TRASLADO_PASAJEROS, CN_FECHA_TRASLADO_PASAJEROS, CN_HORA_TRASLADO_PASAJEROS };
	
	 return db.query(TABLE_VEHICULO, Columnas, null,null, null, null, null);
	 }
	
	// // RETORNA TODOS LOS DATOS DEL VEHICULO SEGUN SEA SU ID
	// public Cursor BuscarVehiculoUID(String UIDVehiculo) {
	// String[] Columnas = new String[] { CN_ID2, CN_UIDVEHICULO,
	// CN_ESTADOUIDVEHICULO, CN_IDVEHICULO, CN_MARCA, CN_MODELO,
	// CN_ANIO, CN_TIPOVEHICULO, CN_NOMBREEMPRESAVEHICULO,
	// CN_IDEMPRESAVEHICULO, CN_IMAGENVEHICULO,
	// CN_AUTORIZACIONVEHICULO, CN_FECHACONSULTAVEHICULO,
	// CN_MENSAJEVEHICULO };
	//
	// return db.query(TABLE_VEHICULO, Columnas, CN_UIDVEHICULO + "=?",
	// new String[] { UIDVehiculo }, null, null, null);
	// }
	//
	// public void ActualizarAutorizacionVehiculo(String IdVehiculo,
	// String EstadoUID, String Autorizacion, String FechaConsulta,
	// String Mensaje) {
	// ContentValues valores = new ContentValues();
	// valores.put(CN_ESTADOUIDVEHICULO, EstadoUID);
	// valores.put(CN_AUTORIZACIONVEHICULO, Autorizacion);
	// valores.put(CN_FECHACONSULTAVEHICULO, FechaConsulta);
	// valores.put(CN_MENSAJEVEHICULO, Mensaje);
	//
	// // Actualizamos el registro en la base de datos
	// db.update(TABLE_VEHICULO, valores, CN_ID2 + "=" + IdVehiculo, null);
	//
	// // db.execSQL("UPDATE "+TABLE_VEHICULO
	// // +" set "+CN_AUTORIZACIONVEHICULO+" = \""+FechaConsulta.toString()
	// // +"\" where _id = "+ IdVehiculo +";");
	// }
	//

	//
	// public void ActualizarFechaConsultaVehiculo(String IdVehiculo,
	// String FechaConsulta) {
	// ContentValues valores = new ContentValues();
	// valores.put(CN_FECHACONSULTAVEHICULO, FechaConsulta);
	//
	// // Actualizamos el registro en la base de datos
	// db.update(TABLE_VEHICULO, valores, CN_ID2 + "=" + IdVehiculo, null);
	// // db.execSQL("UPDATE "+TABLE_VEHICULO
	// // +" set "+CN_FECHACONSULTAVEHICULO+" = \""+FechaConsulta.toString()
	// // +"\" where _id = "+ IdVehiculo +";");
	// }
	//
	// // ELIMINAR DE TABLA VEHICULO, LA COLUMNA CUYO IDVEHICULO CORRESPONDA
	// public void EliminarDatosVehiculoId(String IdVehiculo) {
	// db.delete(TABLE_VEHICULO, CN_IDVEHICULO + "=?",
	// new String[] { IdVehiculo });
	// }
	//
	// // ELIMINAR DE TABLA VEHICULO, LA COLUMNA CUYO IDVEHICULO CORRESPONDA
	// public void EliminarDatosVehiculoUID(String UIDVehiculo) {
	// db.delete(TABLE_VEHICULO, CN_UIDVEHICULO + "=?",
	// new String[] { UIDVehiculo });
	// }
	 
	//#endregion
	 
	 /**************************************************************************************************************************/

}
