// Generated code from Butter Knife. Do not modify!
package app.note.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class BudgetActivity$$ViewInjector {
  public static void inject(Finder finder, final app.note.activity.BudgetActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361842, "field 'budgetAmount'");
    target.budgetAmount = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361844, "field 'budgetLv' and method 'clickBudgetLv'");
    target.budgetLv = (android.widget.ListView) view;
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.clickBudgetLv(p0, p1, p2, p3);
        }
      });
  }

  public static void reset(app.note.activity.BudgetActivity target) {
    target.budgetAmount = null;
    target.budgetLv = null;
  }
}
