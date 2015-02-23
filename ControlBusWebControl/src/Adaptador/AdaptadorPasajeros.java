package Adaptador;

import java.util.ArrayList;
import java.util.List;
import com.webcontrol.controlbus.ListadoPasajeros;
import com.webcontrol.controlbus.R;
import ClasesYMetodos.DataBaseManagerWS;
import ClasesYMetodos.Pasajeros;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.TextView;

public class AdaptadorPasajeros extends ArrayAdapter<Pasajeros> implements
		Filterable {
	Context _Context;
	List<Pasajeros> listaPasajeros;
	Filter filtrarPasajeros;
	List<Pasajeros> resetearPasajeros;
	DataBaseManagerWS Manager;

	public AdaptadorPasajeros(List<Pasajeros> listaPasajeros, Context context) {
		super(context, R.layout.campos_pasajeros, listaPasajeros);
		this._Context = context;
		this.listaPasajeros = listaPasajeros;
		this.resetearPasajeros = listaPasajeros;
		Manager = new DataBaseManagerWS(_Context);
	}

	public int getCount() {
		return listaPasajeros.size();
	}

	public Pasajeros getItem(int position) {
		return listaPasajeros.get(position);
	}

	public long getItemId(int position) {
		return listaPasajeros.get(position).hashCode();
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		CamposPasajeros campos = new CamposPasajeros();
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) _Context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			v = inflater.inflate(R.layout.campos_pasajeros, null);

//			RadioButton radio1 = (RadioButton) v
//					.findViewById(R.id.radioButton1);
			TextView txt1 = (TextView) v.findViewById(R.id.textView1);
			TextView txt2 = (TextView) v.findViewById(R.id.txtconductor1);
			CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox1);

			//campos.rd1 = radio1;
			campos.txt1 = txt1;
			campos.txt2 = txt2;
			campos.check = checkBox;

			v.setTag(campos);

		} else {
			campos = (CamposPasajeros) v.getTag();

		}

		Pasajeros p = listaPasajeros.get(position);
		if (p.getEstadoCheckIn().equalsIgnoreCase("si")) {
			campos.rd1.setChecked(true);
		} else {
			campos.rd1.setChecked(true);
		}
		campos.txt1.setText(p.getNombreFuncionario());
		campos.txt2.setText(p.getEmpresaFuncionario());
		if (p.getCambiarReserva().equalsIgnoreCase("si")) {
			campos.check.setChecked(true);
		} else {
			campos.check.setChecked(true);
		}

		return v;
	}

	public void resetData() {
		listaPasajeros = resetearPasajeros;
	}

	@Override
	public Filter getFilter() {
		if (filtrarPasajeros == null) {
			filtrarPasajeros = new FiltrarPasajeros();
		}
		return filtrarPasajeros;
	}

	private static class CamposPasajeros {
		public RadioButton rd1;
		public TextView txt1;
		public TextView txt2;
		public CheckBox check;

	}

	private class FiltrarPasajeros extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if (constraint == null || constraint.length() == 0) {
				results.values = resetearPasajeros;
				results.count = resetearPasajeros.size();
			} else {
				List<Pasajeros> pasajeros = new ArrayList<Pasajeros>();

				for (Pasajeros p : pasajeros) {
					if (p.getNombreFuncionario().toUpperCase()
							.startsWith(constraint.toString().toUpperCase())) {
						pasajeros.add(p);
					}

				}

				results.values = pasajeros;
				results.count = pasajeros.size();

			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			listaPasajeros = (List<Pasajeros>) results.values;
			notifyDataSetChanged();

		}

	}

}
