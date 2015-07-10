package app.note.activity;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import app.note.R;

public class AccountDataAdapter extends BaseAdapter{

	private final String Tag = "AccountDataAdapter";
	private LayoutInflater inflater;
	private Context cnt;
	private ArrayList<Object> objects;
	
	public AccountDataAdapter(Context context, ArrayList<Object> objects) {

		// TODO Auto-generated constructor stub
		this.inflater = LayoutInflater.from(context);
		this.cnt = context;
		this.objects = objects;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return objects.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Object item = this.objects.get(position);
		if(item.getClass() == AccountData.class){
			
			convertView = inflater.inflate(R.layout.accountdata_lv_item, null);
			AccountData data = (AccountData)item;
			((TextView)convertView
					.findViewById(R.id.account_name_tv))
					.setText(data.name);;
			((TextView)convertView
					.findViewById(R.id.second_level_account_group_name_tv))
					.setText(cnt.getResources()
					.getStringArray(R.array.TBL_ACCOUNT_TYPE)[data.subCategory]);
			
			String  sum ;
			if(data.sum > 0){
				sum = String.format("гд%.2f", data.sum);
			}else{
				sum = String.format("-гд%.2f", data.sum);
			}
			
			((TextView)convertView.findViewById(R.id.account_balance_tv)).setText(sum);;
		}else{
			convertView = inflater.inflate(R.layout.accountdata_lv_title, null);
			((TextView)convertView.findViewById(R.id.list_header_title)).setText(item.toString());
		}		
		return convertView;
	}
}
