package com.webcontrol.controlbus;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class Escanear extends Activity {

	Button volver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		cargar();

	}

//	public void cargar() {
//
//		Intent intent = new Intent(this, Pdf417ScanActivity.class);
//
//		intent.putExtra(BaseBarcodeActivity.EXTRAS_LICENSE_KEY,
//				"MTS6-4K52-KVSL-J6QW-55X7-FZ2U-YUP2-ZBIU");
//		// intent.putExtra(Pdf417ScanActivity.EXTRAS_BEEP_RESOURCE, R.raw.beep);
//
//		Pdf417MobiSettings sett = new Pdf417MobiSettings();
//		sett.setRemoveOverlayEnabled(true);
//
//		sett.setPdf417Enabled(true);
//		sett.setQrCodeEnabled(true);
//
//		sett.setDontShowDialog(true);
//		sett.setNullQuietZoneAllowed(true);
//
//		intent.putExtra(BaseBarcodeActivity.EXTRAS_SETTINGS, sett);
//		startActivityForResult(intent, 1);
//
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//
//		if (data != null) {
//			Pdf417MobiScanData scanData = data
//					.getParcelableExtra(BaseBarcodeActivity.EXTRAS_RESULT);
//
//			String barcodeData = scanData.getBarcodeData().trim();
//			String barcodeType = scanData.getBarcodeType();
//			String Lectura = "";
//			if (barcodeType.equalsIgnoreCase("pdf417")) {
//				String primerCaracterRut = barcodeData.substring(0, 1);
//				if (primerCaracterRut.equalsIgnoreCase("1")
//						|| primerCaracterRut.equalsIgnoreCase("2")
//						|| primerCaracterRut.equals("3")) {
//					Lectura = barcodeData.substring(0, 9);
//				} else {
//					Lectura = barcodeData.substring(0, 8);
//				}
//				// Toast.makeText(getApplicationContext(), primerCaracterRut,
//				// Toast.LENGTH_SHORT).show();
//
//			}
//			else if (barcodeType.equalsIgnoreCase("QR CODE")) {
////				int a = barcodeData.indexOf("RUN=");
////				int b = barcodeData.indexOf("&TYPE");
////				String valor = barcodeData.substring(a + 4, b);
////				String sacarguion = valor.replace("-", "").trim();
////				Lectura = sacarguion.replace("&", "");
//				// Toast.makeText(getApplicationContext(), sacaricono,
//				// Toast.LENGTH_SHORT).show();
//				// txt.setText(sacaricono);
//				int a = barcodeData.indexOf("RUN=");
//				int b = barcodeData.indexOf("&type");
//				String valor = barcodeData.substring(a + 4, b);
//				String sacarguion = valor.replace("-", "").trim();
//				Lectura = sacarguion;
//			} 
////			else {
////				Lectura = barcodeData;
////			}
//
//			Intent intent = new Intent(getApplicationContext(),
//					ReservaBus.class);
//			intent.putExtra("data", Lectura);
//			intent.putExtra("tipodata", "IDFUNCIONARIO");
//
//			finish();
//			startActivity(intent);
//
//		} else {
//			finish();
//			startActivity(new Intent(getApplicationContext(),
//					MenuPrincipal.class));
//		}
//
//	}

}
