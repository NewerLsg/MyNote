package app.note.activity;

import android.os.Parcel;
import android.os.Parcelable;

public class AccountData implements Parcelable{

	int id;
	int infoid;
	int firstLevelCategory;
	int subCategory;
	String name;
	double sum; 
	
	AccountData(int infoid,
				int firstLevelCategory,
				int subCategory,
				String name,
				double sum) {	
		this.infoid = infoid;
		this.firstLevelCategory = firstLevelCategory;
		this.subCategory = subCategory;
		this.name = name;
		this.sum = sum;
	}
	
	AccountData(Parcel in) {
		id = in.readInt();
		infoid = in.readInt();
		firstLevelCategory =  in.readInt();
		subCategory = in.readInt();
		name = in.readString();
		sum = in.readDouble();
	}
	
	public static final Parcelable.Creator<AccountData> CREATOR = new Parcelable.Creator<AccountData>() 
			{
				public AccountData createFromParcel(Parcel in) {
					return new AccountData(in);
				}
			
				public AccountData[] newArray(int size) {
					return new AccountData[size];
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
		dest.writeInt(infoid);
		dest.writeInt(firstLevelCategory);
		dest.writeInt(subCategory);
		dest.writeString(name);
		dest.writeDouble(sum);
	}

}
