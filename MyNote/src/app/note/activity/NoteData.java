package app.note.activity;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteData implements Parcelable{
	int		id;			// transaction record id, primary key
	int		type; 		// 0 means income, 1 means payout;
	int 	infoId;
	double	money;
	int 	category_id;
	int 	subcategory_id;
	int 	account_id;
	int 	shop_id;
	int 	item_id;
	String 	date;
	String 	memo;
	
	
	public NoteData(int type,
					int infoId,
					double money,
					int category_id,
					int subcategory_id,
					int account_id,
					int shop_id,
					int item_id,
					String date,
					String memo){
			
		this.type = type; 		
		this.infoId = infoId;
		this.money = money;
		this.category_id =	category_id;
		this.subcategory_id = subcategory_id;
		this.account_id = account_id;
		this.shop_id =  shop_id;
		this.item_id =  item_id;
		this.date = date;
		this.memo = memo;	
	}
	
	public NoteData(Parcel in){
		this.id			 	= in.readInt();		
		this.type		 	= in.readInt(); 		
		this.infoId 	 	= in.readInt();
		this.money 		 	= in.readDouble();
		this.category_id 	=	in.readInt();
		this.subcategory_id = in.readInt();
		this.account_id 	= in.readInt();
		this.shop_id 		=  in.readInt();
		this.item_id 		=  in.readInt();
		this.date 			= in.readString();
		this.memo 			= in.readString();
	}
	
	public static final Parcelable.Creator<NoteData> CREATOR = new Parcelable.Creator<NoteData>() 
			{
				public NoteData createFromParcel(Parcel in) {
					return new NoteData(in);
				}
			
				public NoteData[] newArray(int size) {
					return new NoteData[size];
				}
			};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeInt(type);
		dest.writeInt(infoId);
		dest.writeDouble(money);
		dest.writeInt(category_id);
		dest.writeInt(subcategory_id);
		dest.writeInt(account_id);
		dest.writeInt(shop_id);
		dest.writeInt(item_id);
		dest.writeString(date);
		dest.writeString(memo);
	}

}
