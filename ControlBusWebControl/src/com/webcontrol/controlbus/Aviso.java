package com.webcontrol.controlbus;

import ClasesYMetodos.Metodos;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Aviso extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
//	
String titulo ="Titulo";
String mensaje ="Mensaje";
String tituloNotificacion="Titulo Provisorio";
		Metodos metodos = new Metodos();
		metodos.Notificacion(context, titulo, mensaje, tituloNotificacion);
		
	}

}
