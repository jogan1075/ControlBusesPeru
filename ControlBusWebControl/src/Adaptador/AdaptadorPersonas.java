package Adaptador;

import java.util.ArrayList;

import ClasesYMetodos.Personas;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.webcontrol.controlbus.R;


public class AdaptadorPersonas extends ArrayAdapter<Personas> {

	public ArrayList<Personas> lista;
	Context _Context;

	public AdaptadorPersonas(Context context, int textViewResourceId,
			ArrayList<Personas> stateList) {
		super(context, textViewResourceId, stateList);
		this.lista = new ArrayList<Personas>();
		this.lista.addAll(stateList);
		 _Context = context;
	}

	private class ViewHolder {
		CheckBox name;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Log.v("ConvertView", String.valueOf(position));

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) _Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = vi.inflate(R.layout.campos_pasajeros, null);
			holder = new ViewHolder();
			holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
			convertView.setTag(holder);

			holder.name.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					Personas per = (Personas) cb.getTag();
					per.setSelected(cb.isChecked());
				}
			});

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Personas per = lista.get(position);
		holder.name.setText(per.getNombre() + " " + per.getApellido());
		holder.name.setChecked(per.isSelected());
		holder.name.setTag(per);
		return convertView;
	}

}
