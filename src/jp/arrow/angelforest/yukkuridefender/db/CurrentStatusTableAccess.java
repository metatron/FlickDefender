package jp.arrow.angelforest.yukkuridefender.db;

import jp.angelforest.engine.util.SQLiteEngine;
import jp.arrow.angelforest.yukkuridefender.GameParameters;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class CurrentStatusTableAccess extends SQLiteEngine {
	//save current state
	private String[] columns;

	public CurrentStatusTableAccess(Context context) {
		super(context, GameParameters.DB_NAME, null, 1);
		columns = new String[]{
				"uid", 
				"current_money", 
				"currentoption_bullet_number", 
				"currentoption_bullet_size", 
				"currentoption_bullet_power", 
				"currentoption_bullet_life", 
				"current_life"};
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(
					"CREATE TABLE " +
					CurrentStatusTable.TBNAME + " (" +
					CurrentStatusTable.COL1 + " TEXT PRIMARY KEY," +
					CurrentStatusTable.COL2 + " INTEGER, " + 
					CurrentStatusTable.COL3 + " INTEGER, " + 
					CurrentStatusTable.COL4 + " DOUBLE, " + 
					CurrentStatusTable.COL5 + " INTEGER, " + 
					CurrentStatusTable.COL6 + " INTEGER, " + 
					CurrentStatusTable.COL7 + " INTEGER)"
			);
		}
		catch(SQLException se) {
			Log.i("INFO", se.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL("DROP TABLE IF EXISTS " + CurrentStatusTable.TBNAME);
			onCreate(db);
		}
		catch(SQLException se) {
			Log.i("INFO", se.getMessage());
		}
	}
	
	public void saveOrUpdateData(String[] data) {
		if (selectData().length > 0) {
			ContentValues cv = new ContentValues();
			cv.put("current_money", data[1]);
			cv.put("currentoption_bullet_number", data[2]);
			cv.put("currentoption_bullet_size", data[3]);
			cv.put("currentoption_bullet_power", data[4]);
			cv.put("currentoption_bullet_life", data[5]);
			cv.put("current_life", data[6]);
			setInsertData(columns, data);
			update(CurrentStatusTable.TBNAME, "uid = '" + data[0] + "'");
		}
		else {
			setInsertData(columns, data);
			insert(CurrentStatusTable.TBNAME);
		}
	}
	
	public String[][] selectData() {
		try {
			return select(CurrentStatusTable.TBNAME, columns, null, null, null, null, null);
		} catch (Exception e) {
			Log.i(CurrentStatusTable.TBNAME, e.getMessage());
		}
		
		return null;
	}
}
