package app.note.activity;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;
import android.widget.Toast;
import app.note.R;
import app.note.db.DbOperator;
import app.note.utils.MyDateUtils;

public class NoteDetailsActivity extends Activity{
	
	@InjectView(R.id.time_interval_tv)
	TextView timeIntervalTx;
	
	public static final int day = 1;
	public static final int week = 2;
	public static final int month = 3;
	
	public int type;
	public Calendar c;
	
	@Override
	protected void onCreate(Bundle s) {
		
		super.onCreate(s);
		setContentView(R.layout.notedetails);
		ButterKnife.inject(this);
		initData();
	}
	
	private void initData(){
		Intent intent = getIntent();	
		type =  intent.getIntExtra("type", 0);
		c =  MyDateUtils.stringToDate(intent.getStringExtra("date"));
		
		new NoteDataAnysc().execute(this);
	}	
	
	
	@OnClick(R.id.pre_btn)
	public void preBtn() {
		switch(type) {
			case day:
				c.add(Calendar.DAY_OF_MONTH, -1);

				break;
				
			case week:
				c.add(Calendar.WEEK_OF_YEAR, -1);
				break;
				
			case month:
				c.add(Calendar.MONTH, -1);
				break;
			default:		
		}	
		new NoteDataAnysc().execute(this);
	}
	
	@OnClick(R.id.next_btn)
	public void Btn() {
		switch(type) {
			case day:
				c.add(Calendar.DAY_OF_MONTH, 1);
				break;
				
			case week:
				c.add(Calendar.WEEK_OF_YEAR, 1);
		
				break;
				
			case month:
				c.add(Calendar.MONTH, 1);
				break;
			default:		
		}	
		new NoteDataAnysc().execute(this);
	}
	
	@OnItemClick(R.id.expense_lv)
	public void expenseLvItemOnclick(AdapterView<?> parent, View view, int position, long id) {
		NoteData data = (NoteData)parent.getItemAtPosition(position);
		if(data != null){
			Intent intent = new Intent(this, AddOrEditCountActivity.class);
			intent.putExtra("mode", AddOrEditCountActivity.modify);
			intent.putExtra("data", data);	
			startActivityForResult(intent, 0);	
		}
	}
	
	@OnItemLongClick(R.id.expense_lv)
	public boolean expenseLvItemLongOnclick(AdapterView<?> parent, View view, int position, long id) {	
		NoteData data = (NoteData)parent.getItemAtPosition(position);
		if(data != null){
			parent.setOnCreateContextMenuListener(new OnCreateContextMenuListener(){
				@Override
				public void onCreateContextMenu(ContextMenu menu, View v,
						ContextMenuInfo menuInfo) {
					// TODO Auto-generated method stub		
					menu.add(0, 0, 0, "编辑");  
					menu.add(0, 1, 0, "删除");             
				}			
			});
		}
		return false;
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo itemInfo = (AdapterContextMenuInfo) item.getMenuInfo();	
		
		final NoteData data = (NoteData)((AdapterView<?>) findViewById(R.id.expense_lv)).getItemAtPosition(itemInfo.position);
		
		switch (item.getItemId()) {
			case 0:	
				Intent intent = new Intent(this, AddOrEditCountActivity.class);
				intent.putExtra("mode", AddOrEditCountActivity.modify);
				intent.putExtra("data", data);	
				startActivityForResult(intent, 0);	
				break;
			case 1:
				AlertDialog.Builder ad = new AlertDialog.Builder(this);
				ad.setTitle("确定删除?");
				ad.setPositiveButton("OK", new  DialogInterface.OnClickListener(){		
					@Override
					public void onClick(DialogInterface dialog, int which) {
						DbOperator db = DbOperator.getInstance(NoteDetailsActivity.this);	
						String table = null;						
						// TODO Auto-generated method stub
						
						if( data.type == AddOrEditCountActivity.incomeType ){
							table = "TBL_INCOME";
						}else {
							table = "TBL_EXPENDITURE";
						}				
						db.delete(table, "ID=?", new String[]{String.valueOf(data.infoId)});
						new NoteDataAnysc().execute(NoteDetailsActivity.this);
					}
					
				});
				
				ad.setNegativeButton("Cancel", new  DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						return ;
					}
					
				});
				
				ad.create();
				ad.show();
				
				break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
			case 0:
				if(resultCode == Activity.RESULT_OK ){
					Toast.makeText(this,"修改成功", Toast.LENGTH_SHORT).show();	
					new NoteDataAnysc().execute(this);
				}else if( resultCode == Activity.RESULT_CANCELED ) {
							
				}
		}
	}
	
	@Override
	public void onBackPressed(){
		Intent intent = new Intent();
		this.setResult(Activity.RESULT_OK, intent);
		this.finish();
	}
	
}
