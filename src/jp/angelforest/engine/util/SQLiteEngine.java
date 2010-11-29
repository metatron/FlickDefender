package jp.angelforest.engine.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public abstract class SQLiteEngine extends SQLiteOpenHelper {
	private SQLiteDatabase db;
	private ContentValues values;
	
	public SQLiteEngine(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	/**
	 * データを挿入する
	 * @param table 挿入するテーブル名
	 * @return 挿入行/挿入できなかった場合「-1」
	 */
	public long insert(String table) {
		
		try{
			db = this.getWritableDatabase();			
		}catch(SQLiteException e){
			return -1;
		}
		
		db.beginTransaction();
		long rowID = 0;
		try{
			rowID = db.insert(table, null, values);
			db.setTransactionSuccessful();
		}catch(Exception e){
			
		}finally{
			db.endTransaction();
			db.close();
			values = null;
		}
		return rowID;
	}
	
	/**
	 * テーブルのデータを削除する
	 * @param table 削除するテーブル名
	 * @param whereClause 削除条件
	 */
	public void delete(String table,String whereClause) {
		
		try{
			db = this.getWritableDatabase();			
		}catch(SQLiteException e){
			return;
		}
		
		db.beginTransaction();
		try{
			db.delete(table, whereClause, null);			
			db.setTransactionSuccessful();
		}catch(Exception e){

		}finally{
			db.endTransaction();
			db.close();
			values = null;
		}
	}
	
	public void update(String table, String whereClause) {
		
		try{
			db = this.getWritableDatabase();			
		}catch(SQLiteException e){
			return;
		}
		
		db.beginTransaction();
		try{
			db.update(table, values, whereClause, null);			
			db.setTransactionSuccessful();
		}catch(Exception e){

		}finally{
			db.endTransaction();
			db.close();
			values = null;
		}		
	}
	
	/**
	 * 
	 * @param table テーブル名
	 * @param columns カラム
	 * @param selection WHERE句
	 * @param selectionArgs selectionの「？」を置換する文字列
	 * @param groupBy GROUPBY句
	 * @param having HAVING句
	 * @param orderBy ORDERBY句
	 * @return 取得したデータを返却
	 * @throws Exception
	 */
	public String[][] select(String table,String[] columns,
			String selection, String[] selectionArgs,
			String groupBy, String having, String orderBy) throws Exception {
		
		db = this.getReadableDatabase();			

		Cursor cursor = db.query(table,columns,selection,
				selectionArgs,groupBy,having,orderBy);
		
		cursor.moveToFirst();
		String[][] list = new String[cursor.getCount()][cursor.getColumnCount()];
		
		for (int i = 0; i < list.length; i++) {
			for(int j = 0;j < columns.length;j++){
				list[i][j] = cursor.getString(j);
			}
		    cursor.moveToNext();
		}
		cursor.close();
		db.close();
		return list;
	}
	
	/**
	 * 挿入/更新用のデータを作成する
	 * @param data 挿入する値
	 */
	public void setInsertData(String[] columns, String[] data) {
		
		values = new ContentValues();
		
		//挿入するカラム、またはデータが存在しない場合
		if(columns == null || data == null){
			return;
		}
		
		for(int i = 0;i < columns.length;i++){
			if(data[i] == null || i >= data.length){
				values.putNull(columns[i]);
			} else {
				values.put(columns[i], data[i]);
			}
		}
	}
}