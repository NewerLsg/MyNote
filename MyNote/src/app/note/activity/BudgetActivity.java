package app.note.activity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import app.note.R;
import app.note.db.DbOperator;

public class BudgetActivity extends Activity{
	
	public final String Tag = "BudgetActivity";

	public @InjectView(R.id.budget_amount_tv)
	TextView budgetAmount;
	
	public  @InjectView(R.id.budget_lv)
	ListView budgetLv;
	
	DbOperator db;
	
	public  ArrayList<BudgetData> dataList;
	
	private int[] icon = {
			R.drawable.icon_qtzx,
			R.drawable.icon_jrbx,
			R.drawable.icon_ylbj,
			R.drawable.icon_rqwl,
			R.drawable.icon_xxjx,
			R.drawable.icon_xxyl,
			R.drawable.icon_jltx,
			R.drawable.icon_xcjt,
			R.drawable.icon_jjwy,
			R.drawable.icon_spjs,
			R.drawable.icon_yfsp
	};
	
	@Override
	protected void onCreate(Bundle s) {
		
		super.onCreate(s);
		setContentView(R.layout.budgetdatails);
		ButterKnife.inject(this);
		
		db = DbOperator.getInstance(this);
		
		initDataArray();
	}
	
	public void initDataArray() 
	{
		dataList = new ArrayList<BudgetData>();
		
		for(int i = 0;i < getResources().getStringArray(R.array.TBL_EXPENDITURE_CATEGORY).length ;i++) {
			BudgetData data = new BudgetData();
			data.iconId = icon[i];
			data.categoryId = i;
			dataList.add(data);
		}
	
		new BudgetListAnysc().execute();
	}
	
	@OnItemClick(R.id.budget_lv)
	public void clickBudgetLv(AdapterView<?> parent, View view, int position, long id) {
		final BudgetData data = dataList.get(position);
		final EditText et = new EditText(BudgetActivity.this);
		
		AlertDialog.Builder ad = new  AlertDialog.Builder(BudgetActivity.this);
		
		ad.setTitle(" ‰»Î‘§À„(" + BudgetActivity.this.getResources().getStringArray(R.array.TBL_EXPENDITURE_CATEGORY)[data.categoryId ]+"):");
		et.setText(String.valueOf(data.amount));
		ad.setView(et);
		ad.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Cursor cr;
				String sqlquery = "SELECT * FROM `TBL_BUDGET` WHERE `ID` = ?";
				// TODO Auto-generated method stub
					
				cr = BudgetActivity.this.db.rawQuery(sqlquery, new String[]{String.valueOf(data.categoryId)});
						
				if(!cr.moveToFirst()){
					Log.d(Tag,"insert");
					BudgetActivity.this.db.insert("TBL_BUDGET", new String[]{"ID","AMOUNT"}, 
											new String[]{String.valueOf(data.categoryId),et.getText().toString()});
				}else{
					Log.d(Tag,"update");
					BudgetActivity.this.db.update("TBL_BUDGET", new String[]{"AMOUNT"}, 
											new String[]{et.getText().toString()}, 
											"ID = ?",
											new String[]{String.valueOf(data.categoryId)});
				}
				
				cr.close();
				new BudgetListAnysc().execute();
				Log.d(Tag,"positivie onclick done.");
			}
			
		});
		
		ad.setNegativeButton("Cancle", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}	
		});
		
		ad.create();
		ad.show();	
		
	}
	
	
	public class BudgetListAnysc extends AsyncTask<Void,Void,Void>{
		
		private String amount = null;
		@Override
		protected Void doInBackground(Void... params) {
			
			Log.d(Tag,"come in doinbackground.");
			// TODO Auto-generated method stub
			
			String sqlSum = "SELECT SUM(AMOUNT) FROM `TBL_BUDGET` ";
			String sqlbudget = "SELECT `AMOUNT` FROM `TBL_BUDGET` WHERE `ID` = ?";
			String sqlexpenditure = "SELECT SUM(AMOUNT) FROM `TBL_EXPENDITURE` WHERE `EXPENDITURE_CATEGORY_ID` = ?";
	
			Cursor cr;
			
			for(int i = 0;i < BudgetActivity.this.dataList.size(); i++ ) {
				
				int cid = BudgetActivity.this.dataList.get(i).categoryId;
				
				cr = BudgetActivity.this.db.rawQuery(sqlbudget, new String[]{String.valueOf(cid)});
				
				if(cr.moveToFirst()) {
					BudgetActivity.this.dataList.get(cid).amount = cr.getDouble(0);
				}
				
				cr = BudgetActivity.this.db.rawQuery(sqlexpenditure, new String[]{String.valueOf(cid)});
				
				if(cr.moveToFirst()) {
					BudgetActivity.this.dataList.get(cid).surplus = BudgetActivity.this.dataList.get(cid).amount - cr.getDouble(0);
				}	
				Log.d(Tag,"" + BudgetActivity.this.dataList.get(cid).surplus );
				cr.close();
			}	

			
			Log.d(Tag,"request amount.");
			cr = BudgetActivity.this.db.rawQuery(sqlSum, null);
			Log.d(Tag,"shut down 1");
			
			if(cr.moveToFirst() && ( cr.getString(0) != null )){
				
				Log.d(Tag,"shut down 2");
				amount = cr.getString(0);
				
			//	BudgetActivity.this.budgetAmount.setText(amount);
			}
			Log.d(Tag,"get amount.");
			cr.close();		
			return null;
		}
		
		@Override
		protected void onPostExecute (Void result) {	
			BudgetActivity.this.findViewById(R.id.budget_loading_tv).setVisibility(View.GONE);	
			BudgetActivity.this.budgetAmount.setText(amount);
			((ListView) BudgetActivity.this.findViewById(R.id.budget_lv)).setAdapter(new BudgetListAdapter());
			super.onPostExecute(result);
		}
	}
	
	public class BudgetListAdapter extends BaseAdapter {

		int[] budget_bar_bg = new int[]{
				R.drawable.widget_progress_bg_left,
				R.drawable.widget_progress_bg_middle,
				R.drawable.widget_progress_bg_right,
				R.drawable.widget_progress_red_left,
				R.drawable.widget_progress_red_middle,
				R.drawable.widget_progress_red_right,
		};
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return BudgetActivity.this.dataList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return BudgetActivity.this.dataList.size();
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			BudgetData data =  BudgetActivity.this.dataList.get(position);
			convertView = LayoutInflater.from(BudgetActivity.this).inflate(R.layout.budgetdata_lv_item, null);
	
			((ImageView)convertView.findViewById(R.id.category_icon)).setBackgroundResource(data.iconId);
	
			
			((TextView)convertView.findViewById(R.id.category_name))
								  .setText(BudgetActivity.this.getResources()
										  					.getStringArray(R.array.TBL_EXPENDITURE_CATEGORY)[data.categoryId]);
			((TextView)convertView.findViewById(R.id.budget_sum)).setText(String.valueOf(data.amount));
			
			((TextView)convertView.findViewById(R.id.balance_amount_tv)).setText("”‡∂Ó:"+ data.surplus);
			
			if(data.surplus  < 0) {
				((TextView)convertView.findViewById(R.id.balance_amount_tv))
									.setTextColor(BudgetActivity.this.getResources().getColor(R.color.red));
			}
			

			if(data.amount != 0){		
				((ImageView)convertView.findViewById(R.id.line_bar_left)).setBackgroundResource(budget_bar_bg[3]);
				((ImageView)convertView.findViewById(R.id.line_bar_middle)).setBackgroundResource(budget_bar_bg[4]);
				((ImageView)convertView.findViewById(R.id.line_bar_right)).setBackgroundResource(budget_bar_bg[5]);
			}else{
			
				((ImageView)convertView.findViewById(R.id.line_bar_left)).setBackgroundResource(budget_bar_bg[0]);
				((ImageView)convertView.findViewById(R.id.line_bar_middle)).setBackgroundResource(budget_bar_bg[1]);
				((ImageView)convertView.findViewById(R.id.line_bar_right)).setBackgroundResource(budget_bar_bg[2]);
			}
			
			
			return convertView;
		}
	}
	
	public class BudgetData implements Parcelable{
		int iconId;
		int categoryId;
		double amount;
		double surplus;

		BudgetData() {
		}
	
		
		BudgetData(	int iconId,
					int categoryId,
					double amount,
					double surplus) {
			this.iconId = iconId;
			this.categoryId = categoryId;
			this.amount = amount;
			this.surplus = surplus;
		}
		
		BudgetData(Parcel in) {
			this.iconId = in.readInt();
			this.categoryId =  in.readInt();
			this.amount = in.readDouble();
			this.surplus = in.readDouble();
		}
		
		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			// TODO Auto-generated method stub
			dest.writeInt(iconId);
			dest.writeInt(categoryId);
			dest.writeDouble(amount);
			dest.writeDouble(surplus);
		} 
		
		public final Parcelable.Creator<BudgetData> CREATOR = new Parcelable.Creator<BudgetData>() 
				{
					public BudgetData createFromParcel(Parcel in) {
						return new BudgetData(in);
					}
				
					public BudgetData[] newArray(int size) {
						return new BudgetData[size];
					}
				};
	}
}
