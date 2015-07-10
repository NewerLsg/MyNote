// Generated code from Butter Knife. Do not modify!
package app.note.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class AccountSettingActivity$$ViewInjector {
  public static void inject(Finder finder, final app.note.activity.AccountSettingActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361808, "field 'accountLv' and method 'onItemClick4AccountLv'");
    target.accountLv = (android.widget.ListView) view;
    ((android.widget.AdapterView<?>) view).setOnItemLongClickListener(
      new android.widget.AdapterView.OnItemLongClickListener() {
        @Override public boolean onItemLongClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          return target.onItemClick4AccountLv(p0, p1, p2, p3);
        }
      });
    view = finder.findRequiredView(source, 2131361806, "field 'propertiesTv'");
    target.propertiesTv = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361807, "field 'debtTv'");
    target.debtTv = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361810, "method 'AddAccount'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.AddAccount();
        }
      });
    view = finder.findRequiredView(source, 2131361811, "method 'Transfer'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.Transfer();
        }
      });
  }

  public static void reset(app.note.activity.AccountSettingActivity target) {
    target.accountLv = null;
    target.propertiesTv = null;
    target.debtTv = null;
  }
}
