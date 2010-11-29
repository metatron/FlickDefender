package jp.arrow.angelforest.yukkuridefender.db;

import jp.angelforest.engine.util.SQLiteEngine;
import jp.arrow.angelforest.yukkuridefender.GameParameters;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;


public class ScoreHistoryTableAccess extends SQLiteEngine {

	private ScoreHistoryTableAccess(Context context) {
		super(context, GameParameters.DB_NAME, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			Log.e(null, "create table " + ScoreHistory.TBNAME);
			db.execSQL(
					"CREATE TABLE " +
					ScoreHistory.TBNAME + " (" +
					ScoreHistory.COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					ScoreHistory.COL2 + " TEXT, " + 
					ScoreHistory.COL3 + " INTEGER, " + 
					ScoreHistory.COL4 + " DATETIME)"
			);
		}
		catch(SQLException se) {
			Log.i("INFO", se.getMessage());
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			db.execSQL("DROP TABLE IF EXISTS " + ScoreHistory.TBNAME);
			onCreate(db);
		}
		catch(SQLException se) {
			Log.i("INFO", se.getMessage());
		}
	}
}
