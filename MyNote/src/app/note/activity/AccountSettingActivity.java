package app.note.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import app.note.R;
import app.note.db.DbOperator;

public class AccountSettingActivity extends Activity {
	
	private final static int ACCOUNT_ADD = 0;
	
	@InjectView(R.id.properties_tv)
	TextView propertiesTv;
	
	@InjectView(R.id.debts_tv)
	TextView debtTv;
	
	@InjectView(R.id.account_lv)
	ListView accountLv;
	
	
	private static final String Tag = "AccountSettingActivity";
	
	@Override
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.accountsetting);
		ButterKnife.inject(this);
		refreshAccountView();
	}
	
	void refreshAccountView(){
		Log.d(Tag,"come refresh");
		new AccountDataAync().execute(this);
		Log.d(Tag,"come refresh  doen");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
			case AccountSettingActivity.ACCOUNT_ADD:
			if(resultCode == Activity.RESULT_OK) {
				new AccountDataAync().execute(this);
			}
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo itemInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		final AccountData data = (AccountData)((AdapterView<?>) findViewById(R.id.account_lv)).getItemAtPosition(itemInfo.position);
		
		switch (item.getItemId()) {
			case 0:
				Intent intent = new Intent(this, AccountAddActivity.class);
				intent.putExtra("mode", AccountAddActivity.modify);
				intent.putExtra("data", data);	
				startActivityForResult(intent, 0);	
				break;
			case 1:
				AlertDialog.Builder dg =  new AlertDialog.Builder(this);
				dg.setTitle("È·ÈÏÉ¾³ý?");
				dg.setPositiveButton("OK", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						DbOperator db = DbOperator.getInstance(AccountSettingActivity.this);
						db.delete("TBL_ACCOUNT", "ID=?", new String[]{String.valueOf(data.infoid)});
						new AccountDataAync().execute(AccountSettingActivity.this);
					}
				});
				dg.setNegativeButton("Cancel", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub	
					}					
				});
				
				dg.create();
				dg.show();
				break;
			default:
		}
		
		return false;
	}
	
	@OnItemLongClick(R.id.account_lv) 
	public boolean onItemClick4AccountLv(AdapterView<?> parent, View view, int position, long id) {
		Object data = parent.getItemAtPosition(position);
		if(data.getClass() == AccountData.class) {
			parent.setOnCreateContextMenuListener(new OnCreateContextMenuListener(){
				@Override
				public void onCreateContextMenu(ContextMenu menu, View v,
						ContextMenuInfo menuInfo) {
					// TODO Auto-generated method stub		
					menu.add(0, 0, 0, "±à¼­");  
					menu.add(0, 1, 0, "É¾³ý");             
				}			
			});
		}
		return false;
	}
	
	@OnClick(R.id.add_account_btn)
	public void AddAccount(){
		Intent intent  =  new Intent(AccountSettingActivity.this,AccountAddActivity.class);
		intent.putExtra("mode", AccountAddActivity.add);
		startActivityForResult(intent, ACCOUNT_ADD);
	}

	@OnClick(R.id.transfer_btn)
	public void Transfer(){
		
	}
}
