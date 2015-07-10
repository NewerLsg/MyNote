// Generated code from Butter Knife. Do not modify!
package app.note.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class TestActivity$$ViewInjector {
  public static void inject(Finder finder, final app.note.activity.TestActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361850, "method 'update'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.update();
        }
      });
    view = finder.findRequiredView(source, 2131361849, "method 'del'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.del();
        }
      });
    view = finder.findRequiredView(source, 2131361851, "method 'query'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.query();
        }
      });
    view = finder.findRequiredView(source, 2131361848, "method 'add'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.add();
        }
      });
  }

  public static void reset(app.note.activity.TestActivity target) {
  }
}
