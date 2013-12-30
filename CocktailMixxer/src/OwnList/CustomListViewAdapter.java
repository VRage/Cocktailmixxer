package OwnList;

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

public class CustomListViewAdapter extends ArrayAdapter<RowItem> implements
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ViewHolder holder;
	Context context;
	public final static String APP_PATH_SD_CARD = "/cocktailmixxer/userpics";
	String fullPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + APP_PATH_SD_CARD;

	
	public void setDesc(RowItem rowItem) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		// Toast.makeText(getContext(), getContext().getClass().getSimpleName(),
		// Toast.LENGTH_SHORT).show();
		if (rowItem instanceof Saft){
			if (getContext().getClass().getSimpleName()
					.equals("ActivityAddSaft"))
				// Toast.makeText(getContext(), "true",
				// Toast.LENGTH_SHORT).show();
				holder.txtDesc.setText(rowItem.getDesc());
			else
				// holder.txtDesc.setText(((Saft) rowItem).getProcent());
				holder.txtDesc.setText("\nml Anteil im Cocktail: \n"+((Saft) rowItem).getMl()+"/500 ml");
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
		if(rowItem!=null){
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
		if(rowItem!=null){
		holder.txtTitle.setText(rowItem.getTitle());
		setDesc(rowItem);
		setImage(rowItem);
		}
		return convertView;
	}


}
