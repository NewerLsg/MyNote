package app.note.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import app.note.R;
import app.note.db.DbOperator;
import app.note.utils.MyDateUtils;

public class NoteDataAnysc extends AsyncTask<NoteDetailsActivity,Void,Void>{
	
	NoteDetailsActivity ndaView;
	ArrayList<Object> dataList = new ArrayList<Object>();
	
	private List<NoteData> list = new ArrayList<NoteData>(); 
	private double income = 0,expense = 0 ;
	String timeinternal =  null;

	@Override
	protected Void doInBackground(NoteDetailsActivity... params) {
		// TODO Auto-generated method stub
		Log.d("NoteDataAnysc","come in doinbackground");
		
		ndaView = params[0];
		Calendar c = ndaView.c;
		int type = ndaView.type;
		
		String sqlIncomeSum = "SELECT SUM(AMOUNT) FROM `TBL_INCOME` WHERE `DATE` LIKE ?";
		String sqlExpenditureSum = "SELECT SUM(AMOUNT) FROM `TBL_EXPENDITURE` WHERE `DATE` LIKE ?";
		String sqlIncome = "SELECT * FROM `TBL_INCOME` WHERE `DATE` LIKE ?";
		String sqlExpenditure = "SELECT * FROM `TBL_EXPENDITURE` WHERE `DATE` LIKE ?";
		
		String selectArgs[] = null;
		Cursor cr;
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		Log.d("NoteDataAnysc","year:" + year + "month:" + month + "day:" + day);
		
		switch(type){
			case NoteDetailsActivity.day :
				timeinternal = MyDateUtils.dateToString(year,month,day);
				selectArgs = new String[1];				
				selectArgs[0] =  timeinternal + "%";
				Log.d("NoteDataAnysc",selectArgs[0]);
				break;
				
			case NoteDetailsActivity.week :
				Log.d("NoteDataAnysc","week");
				selectArgs =  new String[7];			
				Calendar tempc = Calendar.getInstance();
				tempc.setTime(c.getTime());
				tempc.add(Calendar.DAY_OF_MONTH,1 - tempc.get(Calendar.DAY_OF_WEEK));		
				
				for(int i = 0 ; i < 7 ; i++ ) {		
					selectArgs[i] =  MyDateUtils.dateToString(tempc.get(Calendar.YEAR), tempc.get(Calendar.MONTH) + 1 ,tempc.get(Calendar.DAY_OF_MONTH)) + "%";
					tempc.add(Calendar.DAY_OF_MONTH,1);
					Log.d("NoteDataAnysc",selectArgs[i]);
					if(i > 0) {
						sqlIncomeSum += " OR `DATE` LIKE ?";
						sqlExpenditureSum += " OR `DATE` LIKE ?";
						sqlIncome += " OR `DATE` LIKE ?";			
						sqlExpenditure += " OR `DATE` LIKE ?";
					}	
				}
				timeinternal = selectArgs[0].substring(0, 10) + " to " + selectArgs[6].substring(0, 10) ;
				break;
				
			case NoteDetailsActivity.month :	
				timeinternal = MyDateUtils.dateToString(year, month) ;
				selectArgs = new String[1];
				selectArgs[0] = timeinternal + "%";
				timeinternal = timeinternal + "-1" + " to " + timeinternal + "-" + MyDateUtils.daysOfMonth(month, year);
				break;	
				
			default:
				break;
		}
		
		Log.d("NoteDataAnysc",timeinternal);
		
		DbOperator dbOp = DbOperator.getInstance(ndaView);
		
		cr = dbOp.rawQuery(sqlIncomeSum, selectArgs);
		cr.moveToFirst();
		if(cr.getString(0) != null){
			income = Double.parseDouble(cr.getString(0));
		}
		
		cr = dbOp.rawQuery(sqlExpenditureSum, selectArgs);
		cr.moveToFirst();
		if(cr.getString(0) != null){
			expense = Double.parseDouble(cr.getString(0));
		}	
		
		cr = dbOp.rawQuery(sqlExpenditure, selectArgs);
		while(cr.moveToNext()) {
			NoteData nd = new NoteData(AddOrEditCountActivity.expenseType,
								cr.getInt(0),
								cr.getDouble(1),
								cr.getInt(2),
								cr.getInt(3),
								cr.getInt(4),
								cr.getInt(5),
								cr.getInt(6),
								cr.getString(7),
								cr.getString(8));
			list.add(nd);
		}
		Log.d("NoteDataAnysc","expense size:" + String.valueOf(list.size()));
		
		cr = dbOp.rawQuery(sqlIncome, selectArgs);
		while(cr.moveToNext()) {
			NoteData nd = new NoteData(AddOrEditCountActivity.incomeType,
								cr.getInt(0),
								cr.getDouble(1),
								cr.getInt(2),
								cr.getInt(3),
								cr.getInt(4),
								0,
								cr.getInt(5),
								cr.getString(6),
								cr.getString(7));
			list.add(nd);
		}
		Log.d("NoteDataAnysc","income size:" + String.valueOf(list.size()));
		cr.close();
		Collections.sort(list, new Comparator<NoteData>(){  	  
            public int compare(NoteData o1, NoteData o2)  
            {  
                //取出操作时间  
                int ret = 0;  
                try  
                {  
                	SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm");
                	ret = df.parse(o2.date).compareTo(df.parse(o1.date));   
                } catch (Exception e)  
                {                     
                    throw new RuntimeException(e);  
                }  
                return  ret;  
            }     
        });  
		
		ListIterator<NoteData> iterator = list.listIterator();
		
		Log.d("NoteDataAnysc","size:" + String.valueOf(list.size()));
		String date = null;
		
		while(iterator.hasNext()) {
			NoteData da = iterator.next();
			if(date == null || !date.equals(da.date.substring(0, 10)))
			{
				date = da.date.substring(0, 10); //中间加入一个日期将数据分割(分割线)
				Log.d("NoteDataAnysc",date);
				dataList.add(date);
			}
			dataList.add(da);
		}
		
		
		Log.d("NoteDataAnysc","after added:" + dataList.size());
		
		Log.d("NoteDataAnysc","doinbackground done");
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		
		ndaView.findViewById(R.id.listview_loading_tv).setVisibility(View.GONE);
		
		if (dataList.size() == 0) 
			ndaView.findViewById(R.id.lv_empty_iv).setVisibility(View.VISIBLE);
		else
			ndaView.findViewById(R.id.lv_empty_iv).setVisibility(View.GONE);

		((TextView)ndaView.findViewById(R.id.income_amount_tv)).setText(String.format("+￥%.2f", income));
		((TextView)ndaView.findViewById(R.id.payout_amount_tv)).setText(String.format("-￥%.2f", expense));
		
		((TextView)ndaView.findViewById(R.id.time_interval_tv)).setText(timeinternal);
		
		ListView lv = (ListView) ndaView.findViewById(R.id.expense_lv);
		lv.setAdapter(new DataListViewAdapter(ndaView, (ArrayList<Object>)dataList.clone()));
		super.onPostExecute(result);
	}

}
