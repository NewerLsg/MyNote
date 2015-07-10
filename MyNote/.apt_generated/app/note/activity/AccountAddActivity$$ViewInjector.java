// Generated code from Butter Knife. Do not modify!
package app.note.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AccountAddActivity$$ViewInjector {
  public static void inject(Finder finder, final app.note.activity.AccountAddActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361793, "field 'accountNameEt'");
    target.accountNameEt = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131361794, "field 'accountBalanceEt' and method 'balanceTouch'");
    target.accountBalanceEt = (android.widget.EditText) view;
    view.setOnTouchListener(
      new android.view.View.OnTouchListener() {
        @Override public boolean onTouch(
          android.view.View p0,
          android.view.MotionEvent p1
        ) {
          return target.balanceTouch(p0, p1);
        }
      });
    view = finder.findRequiredView(source, 2131361795, "field 'accountFirstCagegorySp' and method 'onItemtClickFirstLevelCategory'");
    target.accountFirstCagegorySp = (android.widget.Spinner) view;
    ((android.widget.AdapterView<?>) view).setOnItemSelectedListener(
      new android.widget.AdapterView.OnItemSelectedListener() {
        @Override public void onItemSelected(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.onItemtClickFirstLevelCategory(p2);
        }
        @Override public void onNothingSelected(
          android.widget.AdapterView<?> p0
        ) {
          
        }
      });
    view = finder.findRequiredView(source, 2131361797, "field 'accountSubCagegorySp'");
    target.accountSubCagegorySp = (android.widget.Spinner) view;
    view = finder.findRequiredView(source, 2131361799, "method 'addComfirm'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.addComfirm();
        }
      });
    view = finder.findRequiredView(source, 2131361800, "method 'addCancel'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.addCancel();
        }
      });
  }

  public static void reset(app.note.activity.AccountAddActivity target) {
    target.accountNameEt = null;
    target.accountBalanceEt = null;
    target.accountFirstCagegorySp = null;
    target.accountSubCagegorySp = null;
  }
}
