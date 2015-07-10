// Generated code from Butter Knife. Do not modify!
package app.note.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final app.note.activity.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361875, "field 'mMonthExpense'");
    target.mMonthExpense = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361871, "field 'mWeekIncome'");
    target.mWeekIncome = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361876, "field 'mMonthIncome'");
    target.mMonthIncome = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361865, "field 'mTodayExpense'");
    target.mTodayExpense = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361864, "field 'mTodayDateStr'");
    target.mTodayDateStr = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361857, "field 'mBudgetBalanceAmount' and field 'mBudgetAmount'");
    target.mBudgetBalanceAmount = (android.widget.TextView) view;
    target.mBudgetAmount = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361862, "field 'mDayOfMonth'");
    target.mDayOfMonth = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361855, "field 'mIncomeAmount'");
    target.mIncomeAmount = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361874, "field 'mMonthDateStr'");
    target.mMonthDateStr = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361856, "field 'mExpenseAmount'");
    target.mExpenseAmount = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361870, "field 'mWeekExpense'");
    target.mWeekExpense = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361869, "field 'mWeekDateStr'");
    target.mWeekDateStr = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361854, "field 'mMonth_tv'");
    target.mMonth_tv = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361866, "field 'mTodayIncome'");
    target.mTodayIncome = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361859, "method 'todayRowBt'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.todayRowBt();
        }
      });
    view = finder.findRequiredView(source, 2131361867, "method 'weekRowBt'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.weekRowBt();
        }
      });
    view = finder.findRequiredView(source, 2131361858, "method 'add_expense_quickly'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.add_expense_quickly();
        }
      });
    view = finder.findRequiredView(source, 2131361879, "method 'goToAccountSetting'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.goToAccountSetting();
        }
      });
    view = finder.findRequiredView(source, 2131361880, "method 'goToBudgetSetting'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.goToBudgetSetting();
        }
      });
    view = finder.findRequiredView(source, 2131361872, "method 'monthRowBt'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.monthRowBt();
        }
      });
  }

  public static void reset(app.note.activity.MainActivity target) {
    target.mMonthExpense = null;
    target.mWeekIncome = null;
    target.mMonthIncome = null;
    target.mTodayExpense = null;
    target.mTodayDateStr = null;
    target.mBudgetBalanceAmount = null;
    target.mBudgetAmount = null;
    target.mDayOfMonth = null;
    target.mIncomeAmount = null;
    target.mMonthDateStr = null;
    target.mExpenseAmount = null;
    target.mWeekExpense = null;
    target.mWeekDateStr = null;
    target.mMonth_tv = null;
    target.mTodayIncome = null;
  }
}
