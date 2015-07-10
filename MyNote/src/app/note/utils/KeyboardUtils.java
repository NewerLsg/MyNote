package app.note.utils;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import app.note.R;

public class KeyboardUtils {
	private KeyboardView keyboardView;
	private Keyboard keyboard;// 鏁板瓧閿洏
	public boolean isnun = false;// 鏄惁鏁版嵁閿洏
	public boolean isupper = false;// 鏄惁澶у啓

	private EditText ed;
	private float value;
	private int lastOpCode = 0;
	private boolean needClean = false;

	public KeyboardUtils(Activity act, Context ctx, EditText edit,int viewId) {
		this.ed = edit;
		this.value = 0;
	//	k1 = new Keyboard(ctx, R.xml.qwerty);
		keyboard = new Keyboard(ctx, R.xml.symbols);
		keyboardView = (KeyboardView) act.findViewById(viewId);
		keyboardView.setKeyboard(keyboard);
		keyboardView.setEnabled(true);
		keyboardView.setPreviewEnabled(true);
		keyboardView.setOnKeyboardActionListener(listener);
	}

	private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
		@Override
		public void swipeUp() {
		}

		@Override
		public void swipeRight() {
		}

		@Override
		public void swipeLeft() {
		}

		@Override
		public void swipeDown() {
		}

		@Override
		public void onText(CharSequence text) {
		}

		@Override
		public void onRelease(int primaryCode) {
		}

		@Override
		public void onPress(int primaryCode) {
		}

		@Override
		public void onKey(int primaryCode, int[] keyCodes) {
			Editable editable = ed.getText();
			int start = ed.getSelectionStart();
			if (primaryCode == Keyboard.KEYCODE_DONE) {
				Log.d("KeyboardUtils","in  KEYCODE_DONE");
				hideKeyboard();
			}else if (primaryCode == Keyboard.KEYCODE_DELETE) {
				if (editable != null && editable.length() > 0) {
					if (start > 0) {
						editable.delete(start - 1, start);
					}
				}
			}else if(primaryCode == Keyboard.KEYCODE_CANCEL ) {
				if (editable != null && editable.length() > 0) {
					ed.setText("");;
					value = 0;	
					needClean = false;
				}
			}
			/*else if (primaryCode == 57419) { // go left
				if (start > 0) {
					ed.setSelection(start - 1);
				}
			} else if (primaryCode == 57421) { // go right
				if (start < ed.length()) {
					ed.setSelection(start + 1);
				}
			}
			*/
			else if(primaryCode == 42 || primaryCode == 47
					||primaryCode == 43 || primaryCode == 45 
					||primaryCode == 61){
				
				if(!needClean) {
					do{
					
						if("".equals(ed.getText().toString())){
							break;
						}
						
						Float temp = Float.parseFloat(ed.getText().toString());
					
						switch(lastOpCode) {
							case 42:
								value *=temp;
								break;
							case 43:
								value +=temp;				
								break;
							case 45:
								value -=temp;					
								break;
							case 47:
								if(temp != 0)
									value /=temp;						
								break;
							case 61:							
							default:
								value = temp;
						}
					}while(false);
					
					ed.setText(String.valueOf(value));
					needClean = true;
				}				
				
				lastOpCode = primaryCode;
					
			}else {					
				if(needClean) {
					editable.clear();
					needClean = false;
				}			
				editable.insert(start, Character.toString((char) primaryCode));
			}
		}
	};
	
	public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }
    
    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
        	keyboardView.setVisibility(View.GONE);
        }
    }

}
