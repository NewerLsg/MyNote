package app.note.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnItemSelected;
import butterknife.OnTouch;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import app.note.R;
import app.note.db.DbOperator;
import app.note.utils.DateTimePickDialogUtil;
import app.note.utils.KeyboardUtils;

public class AddOrEditCountActivity extends Activity{
	
	public final static int add 	= 0;
	public final static int modify  = 1;
	
	public final static int incomeType 	= 0;
	public final static int expenseType  = 1;

	@InjectView(R.id.title_tv)
	TextView titleTv;
	
	@InjectView(R.id.trans_type_tab_rg)
	RadioGroup tabRg;
	
	@InjectView(R.id.cost_tv)
	EditText costTv;
	
	@InjectView(R.id.first_level_category_spn)
	Spinner firstLevelCategorySpn;
	
	@InjectView(R.id.sub_category_spn)
	Spinner subCategorySpn;
	
	@InjectView(R.id.account_spn)
	Spinner accountSpn;
	
	@InjectView(R.id.corporation_spn)
	Spinner corporationSpn;
	
	@InjectView(R.id.project_spn)
	Spinner projectSpn;
	
	@InjectView(R.id.trade_time_btn)
	Button tradeTimeBtn;
	
	@InjectView(R.id.memo_btn)
	Button memoBtn;
	
	@InjectView(R.id.save_btn)
	Button saveBtn;
	
	private int type;
	private Intent intent;
	private int mode;
	private NoteData data;
	
	@Override
	public void onCreate(Bundle s) {
		super.onCreate(s);		
		this.setContentView(R.layout.addnote);
		ButterKnife.inject(this);
		intent = getIntent();
		init();
	}
	
	private void init() {	
		mode = intent.getIntExtra("mode", 0);
		Log.d("AddOrEditCountActivity", String.valueOf(mode));
		if( mode == AddOrEditCountActivity.modify) {
			titleTv.setVisibility(View.VISIBLE);
			tabRg.setVisibility(View.GONE);
			data = intent.getParcelableExtra("data");	
			initData(data);
		}else {
			titleTv.setVisibility(View.GONE);
			tabRg.setVisibility(View.VISIBLE);
			initData();
		}
	}
	
	private void initData() {
		
		type = AddOrEditCountActivity.expenseType;
		costTv.setInputType(InputType.TYPE_NULL);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date d = new Date();
		tradeTimeBtn.setText(sdf.format(d));
		
		
		ArrayAdapter<CharSequence> categoryEntries = ArrayAdapter
				.createFromResource(this,R.array.TBL_EXPENDITURE_CATEGORY,android.R.layout.simple_spinner_item);
		categoryEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		firstLevelCategorySpn.setAdapter(categoryEntries);

	}
	
	private void initData(NoteData data) {
		type = data.type;

		int firstLevelEntries = 0;
		int subLevelEntries = 0;	
		if(type == AddOrEditCountActivity.expenseType){
			firstLevelEntries = R.array.TBL_EXPENDITURE_CATEGORY;
			subLevelEntries = R.array.TBL_EXPENDITURE_SUB_CATEGORY_1 + data.category_id;
			corporationSpn.setSelection(data.shop_id );	
		}else {
			firstLevelEntries = R.array.TBL_INCOME_CATEGORY;
			subLevelEntries = R.array.TBL_INCOME_SUB_CATEGORY_1 + data.category_id;
		}
	
		ArrayAdapter<CharSequence> categoryEntries = ArrayAdapter
				.createFromResource(this,firstLevelEntries,android.R.layout.simple_spinner_item);
		categoryEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		firstLevelCategorySpn.setAdapter(categoryEntries);
		
		/*
		ArrayAdapter<CharSequence> subCategoryEntries = ArrayAdapter
				.createFromResource(this,subLevelEntries,android.R.layout.simple_spinner_item);
		subCategoryEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		subCategorySpn.setAdapter(subCategoryEntries);
		*/
		costTv.setInputType(InputType.TYPE_NULL);	
		costTv.setText(String.valueOf(data.money));		
		firstLevelCategorySpn.setSelection(data.category_id);	
		subCategorySpn.setSelection(data.subcategory_id);	
		
		accountSpn.setSelection(data.account_id );
		projectSpn.setSelection(data.item_id );
		tradeTimeBtn.setText(data.date);
		memoBtn.setText(data.memo);
	}
	
	@OnTouch(R.id.cost_tv)
	public boolean costTouch(View v, MotionEvent event) {		
		new KeyboardUtils(this,this,costTv,R.id.keyboard_view).showKeyboard();
		return false;
	}
	
	@OnFocusChange(R.id.cost_tv)
	public void costFocusChange(View v, boolean hasFocus){
		 EditText textView = (EditText)v;
		if(!hasFocus){
			String hint = textView.getHint().toString();
			textView.setTag(hint);
			((EditText)v).setHint("0");	
		}else {
			String hint = (String)textView.getTag();
			textView.setHint(hint);
		}	
	}
	
	@OnItemSelected(R.id.first_level_category_spn)
	public void OnItemSelect4FirstLevelCategorySpn(int position){	

		int entries = 0;
		
		if(type == AddOrEditCountActivity.incomeType) {
			entries = R.array.TBL_INCOME_SUB_CATEGORY_1 + position;
		}else{
			entries = R.array.TBL_EXPENDITURE_SUB_CATEGORY_1 + position;
		}
		
		 ArrayAdapter<CharSequence> subCategoryEntries = ArrayAdapter
				 										.createFromResource(this,entries,android.R.layout.simple_spinner_item);
		 subCategoryEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 subCategorySpn.setAdapter(subCategoryEntries);
	}
	
	@OnClick(R.id.trade_time_btn)
	public void tradeTimeBtn(View v)  {
		Log.d("AddOrEditCountActivity","Come in trade btn");	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateStr = (String) tradeTimeBtn.getText();
		Log.d("AddOrEditCountActivity","begin:" + dateStr);
		DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(AddOrEditCountActivity.this,dateStr,sdf);		
		dateTimePicKDialog.dateTimePicKDialog(tradeTimeBtn);	
	}
	
	@OnClick(R.id.memo_btn)
	public void memoBtn(View v) {
		final Button btn = (Button)v;
		final EditText edit = new EditText(this);
		edit.setLines(5);
		edit.setText(btn.getText());
		
		new AlertDialog.Builder(this)
		.setTitle(getString(R.string.dialog_memo_title))
		.setView(edit)
		.setPositiveButton(getString(R.string.dialog_memo_ok), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				btn.setText(edit.getText());
			}
		}).setNegativeButton(getString(R.string.dialog_memo_cancle), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		}).show();
	}
	
	@OnClick(R.id.save_btn) 
	public void saveBtn() {
		
		if("".equals(costTv.getText().toString()) || costTv.getText().toString() == null) {
			Toast.makeText(this, "金额忘记输入了!", Toast.LENGTH_SHORT).show();
			return ;
		}
			
		DbOperator db =  DbOperator.getInstance(this);
	
		String[] fields;
		String[] values;
		String table;
		
		if(type == AddOrEditCountActivity.expenseType){
			table = "TBL_EXPENDITURE";
			fields = new String[]{ "AMOUNT",
								"EXPENDITURE_CATEGORY_ID", 
								"EXPENDITURE_SUB_CATEGORY_ID", 
								"ACCOUNT_ID", 
								"STORE_ID",
								"ITEM_ID", 
								"DATE", 
								"MEMO"};
			
			values = new String[]{ costTv.getText().toString(),
					String.valueOf(firstLevelCategorySpn.getSelectedItemId()),
					String.valueOf(subCategorySpn.getSelectedItemId()),
					String.valueOf(accountSpn.getSelectedItemId()),
					String.valueOf(corporationSpn.getSelectedItemId()),
					String.valueOf(projectSpn.getSelectedItemId()),
					tradeTimeBtn.getText().toString(),
					memoBtn.getText().toString() };
		}else {
			
			table = "TBL_INCOME";
			fields = new String[]{ "AMOUNT",
								"INCOME_CATEGORY_ID", 
								"INCOME_SUB_CATEGORY_ID", 
								"ACCOUNT_ID", 
								"ITEM_ID", 
								"DATE", 
								"MEMO"};
			
			values = new String[]{ costTv.getText().toString(),
					String.valueOf(firstLevelCategorySpn.getSelectedItemId()),
					String.valueOf(subCategorySpn.getSelectedItemId()),
					String.valueOf(accountSpn.getSelectedItemId()),
					String.valueOf(projectSpn.getSelectedItemId()),
					tradeTimeBtn.getText().toString(),
					memoBtn.getText().toString() };
		
		}
	
		if(mode == AddOrEditCountActivity.add) {
			db.insert(table,fields,values);
		}else {
			Log.d("AddOrEditCountActivity","update");
			//Log.d("AddOrEditCountActivity",String.valueOf(data.infoId));
			db.update(table,fields,values,"ID=?",new String[]{String.valueOf(data.infoId)});
			//Log.d("AddOrEditCountActivity","update.....");
		}
		
		Intent intent = new Intent();
		setResult(Activity.RESULT_OK, intent);
		this.finish();
	}
	
	@OnClick(R.id.cancel_btn)
	public void cancelBtn(){
		Intent intent = new Intent();
		setResult(Activity.RESULT_CANCELED, intent);
		this.finish();
	}
	
	@OnCheckedChanged(R.id.payout_tab_rb)
	public void CheckExpense(boolean isChecked) {
		if(isChecked){
			findViewById(R.id.corporation_fl).setVisibility(View.VISIBLE);
			type = AddOrEditCountActivity.expenseType;
			
			ArrayAdapter<CharSequence> categoryEntries = ArrayAdapter
					.createFromResource(this,R.array.TBL_EXPENDITURE_CATEGORY,android.R.layout.simple_spinner_item);
			categoryEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			firstLevelCategorySpn.setAdapter(categoryEntries);
			
			ArrayAdapter<CharSequence> subCategoryEntries = ArrayAdapter
					.createFromResource(this,R.array.TBL_EXPENDITURE_SUB_CATEGORY_1,android.R.layout.simple_spinner_item);
			subCategoryEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			subCategorySpn.setAdapter(subCategoryEntries);
		}
	}
	
	@OnCheckedChanged(R.id.income_tab_rb)
	public void CheckIncome(boolean checked) {
		if(checked){
			findViewById(R.id.corporation_fl).setVisibility(View.GONE);
			type = AddOrEditCountActivity.incomeType;
			
			ArrayAdapter<CharSequence> categoryEntries = ArrayAdapter
					.createFromResource(this,R.array.TBL_INCOME_CATEGORY,android.R.layout.simple_spinner_item);
			categoryEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			firstLevelCategorySpn.setAdapter(categoryEntries);
			
			ArrayAdapter<CharSequence> subCategoryEntries = ArrayAdapter
					.createFromResource(this,R.array.TBL_INCOME_SUB_CATEGORY_1,android.R.layout.simple_spinner_item);
			subCategoryEntries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			subCategorySpn.setAdapter(subCategoryEntries);
		}
	}
	
}
