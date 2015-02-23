package Adaptador;

import java.util.List;

import com.webcontrol.controlbus.R;
import ClasesYMetodos.ClaseLog;
import ClasesYMetodos.DataBaseManagerWS;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdaptadorLog extends ArrayAdapter<ClaseLog> {
	Context _Context;
	List<ClaseLog> arregloLog;
	DataBaseManagerWS Manager;

	public AdaptadorLog(List<ClaseLog> arregloLog, Context context) {
		super(context, R.layout.campos_pasajeros, arregloLog);
		this._Context = context;
		this.arregloLog = arregloLog;

		Manager = new DataBaseManagerWS(_Context);
	}

	public int getCount() {
		return arregloLog.size();
	}

	public ClaseLog getItem(int position) {
		return arregloLog.get(position);
	}

	public long getItemId(int position) {
		return arregloLog.get(position).hashCode();
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		Log log = new Log();
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) _Context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			v = inflater.inflate(R.layout.campos_log, null);
			TextView txt1 = (TextView) v.findViewById(R.id.txt1Log);
			TextView txt2 = (TextView) v.findViewById(R.id.txt2Log);
			TextView txt3 = (TextView) v.findViewById(R.id.txt3Log);
			TextView txt4 = (TextView) v.findViewById(R.id.txt4Log);

			log.Dato1 = txt1;
			log.Dato2 = txt2;
			log.Dato3 = txt3;
			log.Dato4 = txt4;
			v.setTag(log);
		} else {
			log = (Log) v.getTag();
		}
		
		ClaseLog dataLog = arregloLog.get(position);
		log.Dato1.setText(dataLog.getRucNombre());
		log.Dato1.setText(dataLog.getAccion());
		log.Dato1.setText(dataLog.getFecha());
		log.Dato1.setText(dataLog.getHora());
		
		return v;

	}

	public static class Log {
		public TextView Dato1;
		public TextView Dato2;
		public TextView Dato3;
		public TextView Dato4;
	}
}
