package app.note.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DbOperator{
	private static DbOperator mDbOp;
	private static final String mDBNAME = "Note";
	private static final int mVersion = 1;
	private static Context mCtx;
	private static DbConnection mDBconn;
	private static  SQLiteDatabase mDBhandler;
	
	private DbOperator(Context ctx) {
		mCtx = ctx;
	}
	
	public static DbOperator getInstance(Context ctx) {
		if(mDbOp == null){
			Log.d("DbOperator","new instance");
			mDbOp = new DbOperator(ctx);
		}
		return mDbOp;
	}
	
	public DbOperator open() {
		Log.d("DbOperator","come in open");
		mDBconn = new DbConnection(mCtx,mDBNAME,null,mVersion);
		Log.d("DbOperator","connected");
		mDBhandler = mDBconn.getWritableDatabase();
		Log.d("DbOperator","get db handle");
		return this;
	}
	
	public void close() {
		mDBconn.close();
	}
	
	public void execSQL(String sql) {
		mDBhandler.execSQL(sql);
	}
	
	//insert
	public long insert(String table,String[] fields,String[] values) {
		if(fields.length != values.length ) {
			return -1;
		}	
		if(mDBconn == null) {
			open();
		}
		ContentValues cv =  new ContentValues();
		for(int i = 0; i < fields.length; i++ ) {
			cv.put(fields[i], values[i]);
		}	
		return mDBhandler.insert(table, null, cv);		
		
	}

	public long insert(String table,String nullColumnHack,ContentValues cv) {
		Log.d("DbOperator","come in insert by default.");
		if(mDBconn == null) {
			Log.d("DbOperator","reconnect.");
			open();
		}			
		return mDBhandler.insert(table, nullColumnHack, cv);			
	}
	
	public long delete(String table, String whereClause, String[] whereArgs) {
		if(mDBconn == null) {
			open();
		}
		return mDBhandler.delete(table, whereClause, whereArgs);
	}
	
	public void  update()
	{
		Log.d("DbOperator","in update");
	}
	//update
	public long update(String table,String[] fields, String[] values, String whereClause, String[] whereArgs) {	
		Log.d("DbOperator","in update 123123");
			
		if(fields.length != values.length ) {
			return -1;
		}	
		if(mDBconn == null) {
			open();
		}
		ContentValues cv =  new ContentValues();
		for(int i = 0; i < fields.length; i++ ) {
			cv.put(fields[i], values[i]);
		}	
		
		return mDBhandler.update(table, cv, whereClause, whereArgs);	
	}

	public long update(String table,ContentValues cv, String whereClause, String[] whereArgs) {
		Log.d("DbOperator","123123");
		if(mDBconn == null) {
			open();
		}	
		return mDBhandler.update(table, cv, whereClause, whereArgs);
	}
	
	//query
	public Cursor query(String table, String[] columns, 
			String selection, String[] selectionArgs, String groupBy, 
			String having, String orderBy) {
			if(mDBhandler == null) {
				open();
			}
			Cursor cursor = mDBhandler.query
			(
					table, columns, selection, selectionArgs, 
					groupBy, having, orderBy
			);
			return cursor;
	}
	
	public Cursor rawQuery(String sql,String[] selectionArgs) {
		if(mDBhandler == null) {
			open();
		}
		Log.d("SQLiteDatabase",sql);
		return mDBhandler.rawQuery(sql, selectionArgs);
	}	

	public void beginTransaction() {
		mDBhandler.beginTransaction();
	}
	
	public void endTransaction() {
		mDBhandler.endTransaction();
	}
	
	public SQLiteDatabase getHandler()
	{
		if(mDBhandler == null) {
			open();
		}
		return mDBhandler;
	}
	
	
	private static class DbConnection extends SQLiteOpenHelper{

		public DbConnection(Context context, String name, CursorFactory factory,
				int version) {
		
			super(context, name, factory, version);
			Log.d("DbConnection","come in DbConnection");		
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("DbConnection", "come in onCreate");
			// TODO Auto-generated method stub
			String[] tables = DbInfo.getTables();
			String[][] fields = DbInfo.getFields();
			String[][] Attrs = DbInfo.getAttrs();
			
			for(int i = 0; i < tables.length ; i++) {
				String sql = "create table " + tables[i] + " (";
				for(int j = 0; j < fields[i].length; j++) {
					sql +=  fields[i][j] + " " + Attrs[i][j];
					if(j !=  fields[i].length - 1) {
						sql += ",";
					}				
				}
				
				//sql = sql.substring(0, sql.length() - 1);//这个似乎更好
				sql += " )";
				db.execSQL(sql);
			}
		}

		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			String[] tables = DbInfo.getTables();
			for(int i = 0; i < tables.length; i++) {			
				db.execSQL("drop table if exists " + tables[1]);
			}
			this.onCreate(db);
		}

	}

}
