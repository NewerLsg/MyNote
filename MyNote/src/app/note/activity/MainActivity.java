package app.note.activity;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import app.note.R;
import app.note.db.DbInfo;
import app.note.db.DbOperator;
import app.note.utils.MyDateUtils;

public class MainActivity extends Activity {
		
	@InjectView(R.id.month_tv)
	TextView mMonth_tv;
	
	@InjectView(R.id.expense_amount_tv)
	TextView mExpenseAmount;
	
	@InjectView(R.id.income_amount_tv)
	TextView mIncomeAmount;
	
	@InjectView(R.id.budget_balance_amount_tv)
	TextView mBudgetAmount;
	
	@InjectView(R.id.budget_balance_amount_tv)
	TextView mBudgetBalanceAmount;
	
	@InjectView(R.id.today_expense_amount_tv)
	TextView mTodayExpense;
	
	@InjectView(R.id.today_income_amount_tv)
	TextView mTodayIncome;
	
	@InjectView(R.id.week_expense_amount_tv)
	TextView mWeekExpense;
	
	@InjectView(R.id.week_income_amount_tv)
	TextView mWeekIncome;
	
	@InjectView(R.id.month_expense_amount_tv)
	TextView mMonthExpense;
	
	@InjectView(R.id.month_income_amount_tv)
	TextView mMonthIncome;
	
	@InjectView(R.id.date_of_month_tv)
	TextView mDayOfMonth;
	
	@InjectView(R.id.today_datestr_tv)
	TextView mTodayDateStr;
	
	@InjectView(R.id.week_datestr_tv)
	TextView mWeekDateStr;
	
	@InjectView(R.id.month_datestr_tv)
	TextView mMonthDateStr;
		
	private static final int iAddNote = 1;
	private static final int scanDetails = 2;
	
	private static final int mainSummary = 0;
	private static final int daySummary = 1;
	private static final int weekSummary = 2;
	private static final int monthSummary = 3;
	private static final int budgeSummary = 4;
	
	Integer mYear;	
	Integer mMonth;
	Integer mDay;
	Integer mWeek;
	DbOperator mDbOp;
	
	@Override
	protected void onCreate(Bundle s) {
		super.onCreate(s);
		this.setContentView(R.layout.main);
		ButterKnife.inject(this);
		init();	
	}
	
	//init
	public void init() {	
		initDate();
		initViewData();
	}
	
	//date init.
	private void initDate() {
		Calendar c = Calendar.getInstance(); 
		
		mYear = c.get(Calendar.YEAR);	
		mMonth = c.get(Calendar.MONTH)+ 1;  
		mDay = c.get(Calendar.DAY_OF_MONTH);	
		mWeek = c.get(Calendar.DAY_OF_WEEK );
				
		mMonth_tv.setText(mMonth.toString());	
		mDayOfMonth.setText(mDay.toString());
		mTodayDateStr.setText(mMonth+ "/" + mDay + "/" + mYear);
		mWeekDateStr.setText(mMonth+ "/" + (mDay - mWeek + 1) + "/" + mYear + "-" + mMonth+ "/" + (mDay - mWeek + 7) + "/" + mYear);
		mMonthDateStr.setText(mMonth+ "/1/" + mYear + "-" + mMonth+ "/" + MyDateUtils.daysOfMonth(mMonth, mYear) + "/" + mYear);	
	}
	
	//init TextView's data
	private void initViewData() {	
		
		mDbOp =  DbOperator.getInstance(this);	
		
		String expenditrueTab = "TBL_EXPENDITURE";
		String incomeTab = "TBL_INCOME";
		
		initViewData(MainActivity.mainSummary, expenditrueTab, mExpenseAmount);
		
		initViewData(MainActivity.mainSummary, incomeTab, mIncomeAmount);
		
		initViewData(MainActivity.budgeSummary, incomeTab, mBudgetAmount);	
		
		initViewData(MainActivity.daySummary, expenditrueTab, mTodayExpense);
		initViewData(MainActivity.daySummary, incomeTab ,mTodayIncome);
		
		initViewData(MainActivity.weekSummary, expenditrueTab, mWeekExpense);
		initViewData(MainActivity.weekSummary, incomeTab, mWeekIncome);
		
		initViewData(MainActivity.monthSummary, expenditrueTab, mMonthExpense);
		initViewData(MainActivity.monthSummary, incomeTab, mMonthIncome);	
		
		
		double budgetSurplus = Double.valueOf( mBudgetAmount.getText().toString()) - Double.valueOf(mExpenseAmount.getText().toString());
		
		mBudgetAmount.setText(String.valueOf(budgetSurplus));
		if(budgetSurplus < 0) {
			mBudgetAmount.setTextColor(this.getResources().getColor(R.color.red));
		}else{
			mBudgetAmount.setTextColor(this.getResources().getColor(R.color.white));
		}
	
	}
	
	private void initViewData(int type, String whichTab, TextView v) {

		String sql = null;
		String selectArgs[] =  null;
		Cursor cr;
	
		sql = "SELECT SUM(AMOUNT) FROM `" + whichTab  + "` WHERE `DATE` LIKE ?";	
		
		switch(type) {
			case MainActivity.mainSummary:
			case MainActivity.monthSummary:
				selectArgs =  new String[1];
				selectArgs[0] = MyDateUtils.dateToString(mYear, mMonth) + "%";
				break;		
				
			case MainActivity.daySummary:
				selectArgs =  new String[1];
				selectArgs[0] = MyDateUtils.dateToString(mYear, mMonth, mDay) + "%";
				break;				
				
			case MainActivity.weekSummary:
				selectArgs =  new String[mWeek];
				String dateStr = MyDateUtils.dateToString(mYear, mMonth);		
				for(int i = mDay - mWeek  + 1, j = 0; i <= mDay; i++ ,j++) {			
					selectArgs[j] = dateStr + "-" + (i>10?i:("0"+i)) + "%" ;
					if(j > 0) {
						sql += " OR `DATE` LIKE ?";
					}
				}
				break;
			case MainActivity.budgeSummary:
				sql = "SELECT SUM(AMOUNT) FROM `TBL_BUDGET`";
				selectArgs = null;
				break;
			default:
				break;
		}
		
		cr = mDbOp.rawQuery(sql,selectArgs);
		cr.moveToFirst();
		if(cr.getString(0) == null){
			v.setText(String.valueOf(0.0));
		}else {
			v.setText(String.valueOf(Double.parseDouble(cr.getString(0))));
		}
		cr.close();
	}	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
			case iAddNote:
				if(resultCode == Activity.RESULT_OK ){
					Toast.makeText(this,"记账成功", Toast.LENGTH_SHORT).show();	
					init();
				}else if( resultCode == Activity.RESULT_CANCELED ) {
					//Toast.makeText(this,"取消添加", Toast.LENGTH_SHORT).show();;			
				}
				break;
			case scanDetails:
				if(resultCode == Activity.RESULT_OK) {
					init();
				}
				break;
		}
	}
	
	@OnClick(R.id.add_expense_quickly_btn) 
	public void add_expense_quickly(){
		Intent intent = new Intent(MainActivity.this,AddOrEditCountActivity.class);
		this.startActivityForResult(intent, iAddNote);
	}
	
	@OnClick(R.id.today_row_rl) 
	public void todayRowBt(){
		Intent intent = new Intent(MainActivity.this,NoteDetailsActivity.class);
		intent.putExtra("type", NoteDetailsActivity.day);
		intent.putExtra("date", MyDateUtils.dateToString(mYear, mMonth, mDay));
		startActivityForResult(intent,scanDetails);
	}
	
	@OnClick(R.id.week_row_rl) 
	public void weekRowBt(){
		Intent intent = new Intent(MainActivity.this,NoteDetailsActivity.class);
		intent.putExtra("type", NoteDetailsActivity.week);
		intent.putExtra("date", MyDateUtils.dateToString(mYear, mMonth, mDay));
		startActivityForResult(intent,scanDetails);
	}
	
	@OnClick(R.id.month_row_rl) 
	public void monthRowBt(){
		Intent intent = new Intent(MainActivity.this,NoteDetailsActivity.class);
		intent.putExtra("type", NoteDetailsActivity.month);
		intent.putExtra("date", MyDateUtils.dateToString(mYear, mMonth));
		startActivityForResult(intent,scanDetails);
	}
	
	@OnClick(R.id.nav_account_btn)
	public void goToAccountSetting(){
		Intent intent = new Intent(MainActivity.this,AccountSettingActivity.class);
		startActivity(intent);
	}
	
	@OnClick(R.id.nav_budget_btn)
	public void goToBudgetSetting(){
		Intent intent = new Intent(MainActivity.this,BudgetActivity.class);
		startActivity(intent);
	}
}
