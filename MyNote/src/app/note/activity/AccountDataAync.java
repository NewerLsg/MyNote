package app.note.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import app.note.R;
import app.note.db.DbOperator;

public class AccountDataAync extends AsyncTask<AccountSettingActivity,Void,Void>{
	
	private final String TAG = "AccountDataAync";
	private AccountSettingActivity asView = null;
	private double properties = 0.0;
	private double debts = 0.0;
	private ArrayList<Object> dataList = new ArrayList<Object>();
	
	private List<AccountData> list = new ArrayList<AccountData>(); 

	
	@Override
	protected Void doInBackground(AccountSettingActivity... params) {

		// TODO Auto-generated method stub
		asView = params[0];
		String sql = "SELECT * FROM `TBL_ACCOUNT` ORDER BY `TYPE_ID` ASC";
		String sql4properties = "SELECT SUM(`ACCOUNT_BALANCE`) FROM `TBL_ACCOUNT` WHERE `TYPE_ID` != '3' ";
		String sql4debts = "SELECT SUM(`ACCOUNT_BALANCE`) FROM `TBL_ACCOUNT` WHERE `TYPE_ID` = '3'";
		
		DbOperator db = DbOperator.getInstance(asView);
		Cursor cr;
		

		cr = db.rawQuery(sql4properties, null);
		if(cr.moveToFirst()){
			properties = cr.getInt(0);
		}
		cr = db.rawQuery(sql4debts, null);
		if(cr.moveToFirst()){
			debts = cr.getInt(0);
		}
	
		cr = db.rawQuery(sql,null);	
		while(cr.moveToNext()) {
			AccountData data = new AccountData(cr.getInt(0),
											cr.getInt(2),
											cr.getInt(3),
											cr.getString(1),
											cr.getDouble(4));
			
			list.add(data);	
		}
	
		cr.close();
		
		ListIterator<AccountData> it =  list.listIterator();
		
		int type = Integer.MAX_VALUE;
		Resources rs = asView.getResources();
		while(it.hasNext()){	
			AccountData data = it.next();
			if(type == Integer.MAX_VALUE || type != data.firstLevelCategory){				
				type =  data.firstLevelCategory;	
				if(rs.getStringArray(R.array.TBL_ACCOUNT_TYPE).length <= type) {
					continue;
				}
				dataList.add(rs.getStringArray(R.array.TBL_ACCOUNT_TYPE)[type]);
			}
			dataList.add(data);
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		ListView lv = asView.accountLv;
		
		if(dataList.size() != 0) {
			asView.findViewById(R.id.listview_loading_tv).setVisibility(View.GONE);	
			lv.setVisibility(View.VISIBLE);
		}else{
		//	asView.findViewById(R.id.listview_loading_tv).setVisibility(View.VISIBLE);	
			lv.setVisibility(View.GONE);
		}
		
		Log.d(TAG,"come in do onPostExecute 1");
		((TextView)asView.findViewById(R.id.properties_tv)).setText(String.format("+гд%.2f", properties));
		((TextView)asView.findViewById(R.id.debts_tv)).setText(String.format("-гд%.2f", debts));

		lv.setAdapter(new AccountDataAdapter(asView, (ArrayList<Object>)dataList.clone()));	
		super.onPostExecute(result);
	}
}
