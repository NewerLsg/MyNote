package app.note.activity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTouch;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import app.note.R;
import app.note.db.DbOperator;
import app.note.utils.KeyboardUtils;

public class AccountAddActivity extends Activity{

	public static final int add = 0;
	public static final int modify = 1;
	
	private int mode;
	private AccountData data;
	
	private final String Tag = "AccountAddActivity";
	@InjectView(R.id.account_add_accountname)
	EditText accountNameEt;
	
	@InjectView(R.id.account_add_balance)
	EditText accountBalanceEt;
	
	@InjectView(R.id.account_add_firstlevelcategory)
	Spinner accountFirstCagegorySp;
	
	@InjectView(R.id.account_add_subcategory)
	Spinner accountSubCagegorySp;
	
	
	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.account_add);	
		ButterKnife.inject(this);
		initData(getIntent());
		accountBalanceEt.setInputType(InputType.TYPE_NULL);
		
	}
	
	public void initData(Intent intent){
		mode = intent.getIntExtra("mode", 0);
		if(mode == AccountAddActivity.modify) {
			Log.d(Tag,"modify");
			data = intent.getParcelableExtra("data");	
			accountNameEt.setText(data.name);
			accountBalanceEt.setText(String.valueOf(data.sum));
			accountFirstCagegorySp.setSelection(data.firstLevelCategory);
			accountSubCagegorySp.setSelection(data.subCategory);
			return ;
		}
		Log.d(Tag,"add");
	}
	
	@OnItemSelected(R.id.account_add_firstlevelcategory)
	public void onItemtClickFirstLevelCategory(int position) {
		
		 ArrayAdapter<CharSequence> subCategoryEntries = ArrayAdapter
					.createFromResource(this, R.array.TBL_ACCOUNT_SUB_TYPE_1 + position, android.R.layout.simple_spinner_item);
		 subCategoryEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 
		((Spinner)findViewById(R.id.account_add_subcategory)).setAdapter(subCategoryEntries);
	}
	
	
	@OnTouch(R.id.account_add_balance)
	public boolean balanceTouch(View v, MotionEvent event) {	
		Log.d(Tag,"come touch balcance");
		new KeyboardUtils(this,this,accountBalanceEt,R.id.keyboard_view).showKeyboard();
		return false;
	}
	
	@OnClick(R.id.account_add_comfirm) 
	public void addComfirm() {
		if("".equals(accountNameEt.getText().toString())|| null == accountNameEt.getText().toString()) {
			Toast.makeText(this, "No name!", Toast.LENGTH_SHORT).show();
			return ;
		}
		if("".equals(accountBalanceEt.getText().toString())|| null == accountBalanceEt.getText().toString()) {
			Toast.makeText(this, "No balance!", Toast.LENGTH_SHORT).show();
			return ;
		}
		
		DbOperator db = DbOperator.getInstance(this);
		String[] fields = { "NAME",
							"TYPE_ID",
							"SUB_TYPE_ID",
							"ACCOUNT_BALANCE"};
		
		String[] values = {	accountNameEt.getText().toString(),
							String.valueOf(accountFirstCagegorySp.getSelectedItemId()),
							String.valueOf(accountSubCagegorySp.getSelectedItemId()),
							accountBalanceEt.getText().toString()};
		
		long ret ;
		if(mode == AccountAddActivity.modify) {
			ret = db.update("TBL_ACCOUNT", fields, values, "ID=?", new String[]{String.valueOf(data.infoid)});
		}else{
			ret  = db.insert("TBL_ACCOUNT", fields, values);
		}
		
		if(ret == -1) {
			Toast.makeText(this, "add fail!", Toast.LENGTH_SHORT).show();
		}
		
		Intent intent  = new Intent();
		setResult(Activity.RESULT_OK, intent);
		this.finish();
	}
	
	@OnClick(R.id.account_add_cancel) 
	public void addCancel(){
		this.finish();
	}
}
