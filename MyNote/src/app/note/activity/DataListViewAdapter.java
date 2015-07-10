package app.note.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import app.note.R;

public class DataListViewAdapter extends BaseAdapter{
	
	//private int resource;
	List<Object> objects;
	Context cnt;
	LayoutInflater inflater;
	
	public DataListViewAdapter(Context context, ArrayList<Object> objects) {

		// TODO Auto-generated constructor stub
		this.inflater = LayoutInflater.from(context);
		this.cnt = context;
		this.objects = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		Object item = this.objects.get(position);	
		if(item.getClass() == NoteData.class) {			
			convertView  = 	this.inflater.inflate(R.layout.notedata_lv_item, null);		
			ImageView image = (ImageView) convertView.findViewById(R.id.category_icon_iv);
			TextView ctgTxt = (TextView) convertView.findViewById(R.id.category_name_tv);
			TextView costTxt = (TextView) convertView.findViewById(R.id.cost_tv);	
			
			NoteData data = (NoteData)item;	
			int icon;
			String name;
			if(data.type == 1) {
				icon = R.drawable.default_subcategory_icon;
				name = cnt.getResources().getStringArray(R.array.TBL_EXPENDITURE_SUB_CATEGORY_1 + data.category_id)[data.subcategory_id];
				costTxt.setTextColor(cnt.getResources().getColor(R.color.transaction_payout_amount));	
			}else {
				icon = R.drawable.icon_qtsr;
				name = cnt.getResources().getStringArray(R.array.TBL_INCOME_SUB_CATEGORY_1 + data.category_id)[data.subcategory_id];
				costTxt.setTextColor(cnt.getResources().getColor(R.color.transaction_income_amount));
			}
			image.setBackgroundResource(icon);
			ctgTxt.setText(name);
			costTxt.setText(String.valueOf(data.money));
			convertView.setTag(data);
		}else {
			convertView = inflater.inflate(R.layout.notedata_lv_title, null);
			TextView title = (TextView)convertView.findViewById(R.id.list_header_title);
			title.setText(item.toString());
			convertView.setTag(null);
		}
		
		return convertView;
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
	
}
