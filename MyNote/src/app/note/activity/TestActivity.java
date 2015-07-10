package app.note.activity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import app.note.R;
import app.note.db.DbOperator;

public class TestActivity extends Activity{
	
	DbOperator mDbOp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.layout_test);
		mDbOp =  DbOperator.getInstance(this);
		ButterKnife.inject(this);
	}
	
	@OnClick(R.id.add)
	public void add() {
		Log.d("TestActivity","in add.");
		String table = "TBL_EXPENDITURE_CATEGORY";	
		ContentValues cv = new ContentValues();
		cv.put("NAME", "Tom");
		cv.put("BUDGET", "123");
	
		mDbOp.insert(table, null, cv);
		Log.d("TestActivity", "add done");
		
	}

	@OnClick(R.id.del)
	public void del() {
		Log.d("TestActivity","come del");
		String table = "TBL_EXPENDITURE_CATEGORY";	
		mDbOp.delete(table, "ID=?", new String[]{"2"});	
		Log.d("TestActivity","del done");
	}
	
	@OnClick(R.id.update)
	public void update() {
		Log.d("TestActivity","come in update");
		String table = "TBL_EXPENDITURE_CATEGORY";
		ContentValues cv = new ContentValues();
		cv.put("NAME", "Jim");
		cv.put("BUDGET", "678");
	//	mDbOp.update(table, cv, "ID=?", new String[]{"3"});
		Log.d("TestActivity","update done");
	}
	
	@OnClick(R.id.query)
	public void query() {
		Log.d("TestActivity","come in query");
		String table = "TBL_EXPENDITURE_CATEGORY";
		Cursor cr = mDbOp.query(table, null, "ID=?", new String[]{"3"}, null,	null, null);
		while(cr.moveToNext()) {
			Log.d("TestActivity",cr.getString(0));
			Log.d("TestActivity",cr.getString(1));
			Log.d("TestActivity",cr.getString(2));
		}
		Log.d("TestActivity","query done");
	}
	
	
}
