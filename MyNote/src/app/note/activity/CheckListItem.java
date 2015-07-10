package app.note.activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.RelativeLayout;
import app.note.R;

public class CheckListItem extends RelativeLayout implements Checkable{

	private boolean isCheck;
	
	private int[] states = {android.R.attr.state_checked};
	
	private Drawable radio;
	
	private int paddingRight;
	
	public CheckListItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CheckListItem(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public CheckListItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Drawable drawable = context.getResources().getDrawable(R.drawable.widget_btn_radio);
		if (drawable != null)
			init(drawable);
	}

	public void init(Drawable drawable) {
		
		if(radio != null) {
			radio.setCallback(null);
			unscheduleDrawable(radio);	
		}	
		drawable.setCallback(this);
		if(getVisibility() == View.VISIBLE) {
			drawable.setVisible(true, false);
			drawable.setState(getDrawableState());
			radio = drawable;
		}
		requestLayout();
	}
	
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		radio.setBounds(getWidth() - paddingRight - radio.getIntrinsicWidth(),	(getHeight() - radio.getIntrinsicHeight()) / 2,  
						getWidth() - paddingRight,	(getHeight() + radio.getIntrinsicHeight()) / 2);
		radio.draw(canvas);
	}
	
	@Override
	public void setPadding(int left, int top, int right, int bottom) {
		super.setPadding(left, top, right, bottom);
		this.paddingRight = right;
	}
	
	@Override 
	public void  drawableStateChanged(){
		super.drawableStateChanged();
		if(radio !=null){
			radio.setState(getDrawableState());
			invalidate();
		}
	}
		
	@Override
	protected int[] onCreateDrawableState(int extraSpace){
		int[] rStates = super.onCreateDrawableState(extraSpace + 1);	
		if(isChecked()) {
			mergeDrawableStates(rStates,states);
		}	
		return rStates;
	}
	
	@Override
	public void setChecked(boolean checked) {
		// TODO Auto-generated method stub
		if(isCheck != checked){
			isCheck = checked;
			refreshDrawableState();
		}
	}

	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return isCheck;
	}

	@Override
	public void toggle() {
		// TODO Auto-generated method stub
		setChecked(!isCheck);
	}

	
}
