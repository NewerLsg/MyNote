// Generated code from Butter Knife. Do not modify!
package app.note.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class NoteDetailsActivity$$ViewInjector {
  public static void inject(Finder finder, final app.note.activity.NoteDetailsActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361891, "field 'timeIntervalTx'");
    target.timeIntervalTx = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131361892, "method 'Btn'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.Btn();
        }
      });
    view = finder.findRequiredView(source, 2131361890, "method 'preBtn'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.preBtn();
        }
      });
    view = finder.findRequiredView(source, 2131361895, "method 'expenseLvItemOnclick' and method 'expenseLvItemLongOnclick'");
    ((android.widget.AdapterView<?>) view).setOnItemClickListener(
      new android.widget.AdapterView.OnItemClickListener() {
        @Override public void onItemClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          target.expenseLvItemOnclick(p0, p1, p2, p3);
        }
      });
    ((android.widget.AdapterView<?>) view).setOnItemLongClickListener(
      new android.widget.AdapterView.OnItemLongClickListener() {
        @Override public boolean onItemLongClick(
          android.widget.AdapterView<?> p0,
          android.view.View p1,
          int p2,
          long p3
        ) {
          return target.expenseLvItemLongOnclick(p0, p1, p2, p3);
        }
      });
  }

  public static void reset(app.note.activity.NoteDetailsActivity target) {
    target.timeIntervalTx = null;
  }
}
