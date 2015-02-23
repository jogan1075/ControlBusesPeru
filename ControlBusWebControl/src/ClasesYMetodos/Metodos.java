package ClasesYMetodos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.NfcAdapter;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.widget.Toast;

import com.webcontrol.controlbus.Aviso;
import com.webcontrol.controlbus.MenuBus;
import com.webcontrol.controlbus.MenuPrincipal;
import com.webcontrol.controlbus.R;

public class Metodos {

	public String getIMEI(Context context) {
		TelephonyManager phonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String id = phonyManager.getDeviceId();
		if (id == null) {
			id = "Dispositivo no Cuenta con IMEI";
		}

		int phoneType = phonyManager.getPhoneType();
		switch (phoneType) {
		case TelephonyManager.PHONE_TYPE_NONE:
			return "IMEI: " + id;
		}

		return id;
	}
	public String devolverFecha(){
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int mes = (today.month + 1);
		String Fecha = today.monthDay + "/" + mes + "/"
				+ today.year;
		return Fecha;
	}
	public String devolverVersionApp(Context context) {
		String versionName;
		try {
			versionName = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			versionName = "";
		}
		return versionName;
	}

	public void mostrarDialogoConAccion(Context context, String titulo,
			String mensaje, final String accion, final Activity act) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(titulo);

		alertDialog.setMessage(mensaje);

		alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				
				if (accion.equalsIgnoreCase("No tiene")) {
					act.finish();
					act.startActivity(new Intent(act, MenuBus.class));
				} else if (accion.equalsIgnoreCase("Reserva")) {
					act.finish();
					act.startActivity(new Intent(act, MenuBus.class));
				}
				
			}
		});
		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	public void mostrarDialogoSinAccion(Context context, String titulo,
			String mensaje, final Activity act) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(titulo);
		alertDialog.setMessage(mensaje);
		alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	@SuppressWarnings("deprecation")
	public void NFCError(final Context context, String titulo, String mensaje,
			final String accion, final NfcAdapter adapterNFC,
			final Activity activity) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(titulo);
		alertDialog.setMessage(mensaje);
		alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
			@SuppressLint("NewApi")
			public void onClick(DialogInterface dialog, int which) {
				// aquí puedes añadir funciones
				if (accion.equalsIgnoreCase("Habilitar")) {

					// Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
					// startActivity(intent);
					if (!adapterNFC.isNdefPushEnabled()) {
						context.startActivity(new Intent(
								Settings.ACTION_NFC_SETTINGS));
					}

				} else if (accion.equalsIgnoreCase("Cerrar")) {
					activity.finish();
				}
			}
		});
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
		alertDialog.setCancelable(false);
	}

	public AlertDialog dialogoConfirmacion(String titulo, String mensaje,
			Context contexto, final String accion, final Activity actividad) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				contexto);
		alertDialogBuilder.setTitle(titulo);
		alertDialogBuilder.setMessage(mensaje);

		DialogInterface.OnClickListener listenerOk = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (accion.equalsIgnoreCase("salir")) {
					actividad.finish();
					System.runFinalization();
	                System.exit(0);
	               // SegonaActivity.this.finish();
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.addCategory(Intent.CATEGORY_HOME);
					actividad.startActivity(intent);
				}
			

			}
		};

		// Creamos un nuevo OnClickListener para el boton Cancelar
		DialogInterface.OnClickListener listenerCancelar = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		};

		// Asignamos los botones positivo y negativo a sus respectivos listeners
		alertDialogBuilder.setPositiveButton("Si", listenerOk);
		alertDialogBuilder.setNegativeButton("No", listenerCancelar);
		alertDialogBuilder.setIcon(R.drawable.ic_launcher);
		alertDialogBuilder.setCancelable(false);

		return alertDialogBuilder.create();
	}
	
	
	

	public boolean isOnline(Context context) {
		boolean enabled = true;

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();

		if ((info == null || !info.isConnected() || !info.isAvailable())) {
			enabled = false;
		}
		return enabled;
	}

	public String devolverAño() {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();

		return String.valueOf(today.year);

	}

	public String devolverHora() {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();

		return today.format("%k%M");

	}

	public String RestarHoraParaAvisar() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, +5);

		return c.getTime().toString().substring(11, 19);

	}

	public void recibirHora(Context context, String hora, String minuto) {
		
		Calendar calNow = Calendar.getInstance();
		Calendar calSet = (Calendar) calNow.clone();
		calSet.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
		calSet.set(Calendar.MINUTE, Integer.parseInt(minuto)-5);

		Intent intent = new Intent(context, Aviso.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1,
				intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),
				pendingIntent);
	}

	public void Notificacion(Context context, String titulo, String mensaje,
			String tituloNotificacion) {
		NotificationManager mManager;
		mManager = (NotificationManager) context.getApplicationContext()
				.getSystemService(
						context.getApplicationContext().NOTIFICATION_SERVICE);
		Intent intent1 = new Intent(context.getApplicationContext(),
				MenuPrincipal.class);

		Notification notification = new Notification(R.drawable.ic_launcher,
				tituloNotificacion, System.currentTimeMillis());
		intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
				| Intent.FLAG_ACTIVITY_CLEAR_TOP);

		PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
				context.getApplicationContext(), 0, intent1,
				PendingIntent.FLAG_UPDATE_CURRENT);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.setLatestEventInfo(context.getApplicationContext(),
				titulo, mensaje, pendingNotificationIntent);

		mManager.notify(0, notification);
	}
	
	public Vibrator devolverVibrador(Context context, int vibracion){
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(vibracion);
		return vibrator;
		
	}
	
	public Toast devolverToast(Context context,String texto){
		Toast toast = Toast.makeText(context, texto, Toast.LENGTH_SHORT);
		return toast;
				
	}
	
	public String getFecha() {
		String FechaHora = getDateTime();
		String Fecha = FechaHora.substring(0, 10);
		String Hora = FechaHora.substring(11, FechaHora.length());
		Fecha = Fecha.replace("/", "");
		return Fecha;
	}

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		return dateFormat.format(date);
	}
}
