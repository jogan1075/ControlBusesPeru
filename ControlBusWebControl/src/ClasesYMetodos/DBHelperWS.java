package ClasesYMetodos;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelperWS extends SQLiteOpenHelper{
	private static final String DB_NAME = "DatosWS.sqlite";
	private static final int DB_SCHEME_VERSION = 1;

	public DBHelperWS(Context context) {
		super(context, DB_NAME, null, DB_SCHEME_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(DataBaseManagerWS.CREATE_TABLE);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE1);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE2);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE3);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE4);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE5);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE6);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE7);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE8);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE9);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE10);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE11);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE12);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE13);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE14);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE15);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE16);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE17);
		db.execSQL(DataBaseManagerWS.CREATE_TABLE18);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}

