package projekt.OwnList;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import projekt.helpclasses.Cocktail;
import projekt.helpclasses.Saft;
import projekt.helpclasses.User;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cocktailmixxer.R;
//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Matthias Wildberg
/*
 * Costum List View Adapter for Costum List Items
 * Implemented to use Pistures in Lists.
 */
public class CustomListViewAdapter extends ArrayAdapter<RowItem> implements
		Serializable {
	//declare saving.
	private static final long serialVersionUID = 1L;
	
	public final static String APP_PATH_SD_CARD = "/cocktailmixxer/userpics";
	String fullPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + APP_PATH_SD_CARD;

	
	
	//start programming of class Costum ListViewAdapter
	ViewHolder holder;
	Context context;
	
	//setDesc of RowItem
	//will compare actual Activity description, different Descriptions needet in several Activitys
	public void setDesc(RowItem rowItem) {

		if (rowItem instanceof Saft) {
			if (getContext().getClass().getSimpleName().equals("ActivityNewCocktail")||getContext().getClass().getSimpleName().equals("ActivityCocktail"))
				holder.txtDesc.setText("\nml Anteil im Cocktail: \n"
						+ ((Saft) rowItem).getMl() + "/"+new Cocktail(0, null, null).cocktailsize+" ml");
			else
				holder.txtDesc.setText(rowItem.getDesc());	
			
		} 
		else
			holder.txtDesc.setText(rowItem.getDesc());

	}

	public void setImage(RowItem rowItem) {
		if (rowItem instanceof User) {
			File imgFile = new File(fullPath, rowItem.getImageId() + ".jpg");
			if (imgFile.exists()) {

				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
						.getAbsolutePath());
				holder.imageView.setImageBitmap(myBitmap);
			} else {
				holder.imageView.setImageResource(R.drawable.male_icon);

			}
		} else if (rowItem instanceof Cocktail) {
			holder.imageView.setImageResource(R.drawable.icon_cocktail);
		} else if (rowItem instanceof Saft) {
			 if (getContext().getClass().getSimpleName().equals("ActivityAdmin") && rowItem.getDesc().isEmpty() &&rowItem.getTitle().isEmpty())
				 holder.imageView.setImageResource(0);
			 else
				holder.imageView.setImageResource(R.drawable.saft_icon);
		}

	}

	public CustomListViewAdapter(Context context, int resourceId,
			List<RowItem> items) {
		super(context, resourceId, items);
		this.context = context;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
		TextView txtDesc;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder holder = null;
		RowItem rowItem = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.activity_listitem, null);
			holder = new ViewHolder();
			holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		// holder.txtDesc.setText(rowItem.getDesc());
		if (rowItem != null) {
			holder.txtTitle.setText(rowItem.getTitle());
			setDesc(rowItem);
			setImage(rowItem);
		}
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// ViewHolder holder = null;
		RowItem rowItem = getItem(position);

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.activity_listitem, null);
			holder = new ViewHolder();
			holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
			holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		// holder.txtDesc.setText(rowItem.getDesc());
		if (rowItem != null) {
			holder.txtTitle.setText(rowItem.getTitle());
			setDesc(rowItem);
			setImage(rowItem);
		}
		return convertView;
	}

}
