// Generated code from Butter Knife. Do not modify!
package app.note.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AddOrEditCountActivity$$ViewInjector {
  public static void inject(Finder finder, final app.note.activity.AddOrEditCountActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361824, "field 'corporationSpn'");
    target.corporationSpn = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131361829, "field 'memoBtn' and method 'memoBtn'");
    target.memoBtn = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.memoBtn(p0);
        }
      });
    view = finder.findRequiredView(source, 2131361828, "field 'projectSpn'");
    target.projectSpn = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131361821, "field 'subCategorySpn'");
    target.subCategorySpn = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131361826, "field 'tradeTimeBtn' and method 'tradeTimeBtn'");
    target.tradeTimeBtn = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.tradeTimeBtn(p0);
        }
      });
    view = finder.findRequiredView(source, 2131361831, "field 'saveBtn' and method 'saveBtn'");
    target.saveBtn = (android.widget.Button) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.saveBtn();
        }
      });
    view = finder.findRequiredView(source, 2131361822, "field 'accountSpn'");
    target.accountSpn = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131361817, "field 'costTv', method 'costFocusChange', and method 'costTouch'");
    target.costTv = (android.widget.EditText) view;
    view.setOnFocusChangeListener(
      new android.view.View.OnFocusChangeListener() {
        @Override public void onFocusChange(
          android.view.View p0,
          boolean p1
        ) {
          target.costFocusChange(p0, p1);
        }
      });
    view.setOnTouchListener(
      new android.view.View.OnTouchListener() {
        @Override public boolean onTouch(
          android.view.View p0,
          android.view.MotionEvent p1
        ) {
          return target.costTouch(p0, p1);
        }
      });
    view = finder.findRequiredView(source, 2131361818, "field 'firstLevelCategorySpn' and method 'OnItemSelect4FirstLevelCategorySpn'");
    target.firstLevelCategorySpn = (android.widget.Spinner) view;
    ((android.widget.AdapterView<?>) view).setOnItemSelectedListener(
      new android.widget.AdapterView.OnItemSelectedListener() {
        @Override public void onItemSelected(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.OnItemSelect4FirstLevelCategorySpn(p2);
        }
        @Override public void onNothingSelected(
          android.widget.AdapterView<?> p0
        ) {
          
        }
      });
    view = finder.findRequiredView(source, 2131361814, "field 'tabRg'");
    target.tabRg = (android.widget.RadioGroup) view;
    view = finder.findRequiredView(source, 2131361813, "field 'titleTv'");
    target.titleTv = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361816, "method 'CheckIncome'");
    ((android.widget.CompoundButton) view).setOnCheckedChangeListener(
      new android.widget.CompoundButton.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(
          android.widget.CompoundButton p0,
          boolean p1
        ) {
          target.CheckIncome(p1);
        }
      });
    view = finder.findRequiredView(source, 2131361815, "method 'CheckExpense'");
    ((android.widget.CompoundButton) view).setOnCheckedChangeListener(
      new android.widget.CompoundButton.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(
          android.widget.CompoundButton p0,
          boolean p1
        ) {
          target.CheckExpense(p1);
        }
      });
    view = finder.findRequiredView(source, 2131361832, "method 'cancelBtn'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.cancelBtn();
        }
      });
  }

  public static void reset(app.note.activity.AddOrEditCountActivity target) {
    target.corporationSpn = null;
    target.memoBtn = null;
    target.projectSpn = null;
    target.subCategorySpn = null;
    target.tradeTimeBtn = null;
    target.saveBtn = null;
    target.accountSpn = null;
    target.costTv = null;
    target.firstLevelCategorySpn = null;
    target.tabRg = null;
    target.titleTv = null;
  }
}
