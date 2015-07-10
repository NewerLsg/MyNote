package app.note.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import app.note.R;

public class SplashScreenActivity extends Activity{
	@Override
	protected void onCreate(Bundle s) {
		super.onCreate(s);
		this.setContentView(R.layout.splashscreen);	
		Handler hd = new Handler();
		
		hd.postDelayed(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}		
		}, 1500);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		Intent intent = new Intent(SplashScreenActivity.this,MainActivity.class);
		startActivity(intent);
		this.finish();
		return super.onTouchEvent(event);
	}
}
