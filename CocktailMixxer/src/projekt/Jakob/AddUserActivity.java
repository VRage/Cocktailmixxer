package projekt.Jakob;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.GregorianCalendar;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.User;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.cocktailmixxer.R;

public class AddUserActivity extends Activity {
	EditText newUserLength;
	static EditText newUserName;
	private EditText newUserWeight;
	private boolean newUserGesch = true;
	private DatePicker dpResult;
	CM_Status status;
	static int TAKE_PICTURE = 1;
	private Bitmap photo;
	private ImageView userpic;
	private static final int CAMERA_REQUEST = 1888;
	public final static String APP_PATH_SD_CARD = "/cocktailmixxer/userpics";
	String fullPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + APP_PATH_SD_CARD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		status = (CM_Status) getApplicationContext();
		setContentView(R.layout.activity_add_user);

		// Declare buttons and Textfields
		newUserName = (EditText) findViewById(R.id.user_editTextName);
		newUserLength = (EditText) findViewById(R.id.user_edittextSize);
		newUserWeight = (EditText) findViewById(R.id.user_edittextWeight);
		newUserName = (EditText) findViewById(R.id.user_editTextName);
		dpResult = (DatePicker) findViewById(R.id.datePicker1);
		userpic = (ImageView) findViewById(R.id.user_pic);

		// set default datePicker date
		dpResult.init(1990, 05, 15, null);
		if (savedInstanceState != null) {

			Bitmap photo = savedInstanceState.getParcelable("savedImage");
			userpic.setImageBitmap(photo);

		}
		super.onCreate(savedInstanceState);
	}

	public void addUser(View view) {
		try {
			status.add_UserItem(new User(newUserName.getText().toString(),
					Double.parseDouble(newUserWeight.getText().toString()),
					Double.parseDouble(newUserLength.getText().toString()),
					newUserGesch, new GregorianCalendar(dpResult.getYear(),
							dpResult.getMonth() + 1, dpResult.getDayOfMonth())));
			Intent intent = new Intent(view.getContext(), ActivityUser.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Bitte Eingaben überprüfen", Toast.LENGTH_LONG).show();
		}

	}

	public void takePic(View view) {

		// create intent with ACTION_IMAGE_CAPTURE action
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		userpic.buildDrawingCache();
		Parcelable savedpic = Bitmap.createBitmap(userpic.getDrawingCache());
		outState.putParcelable("savedImage", savedpic);
		userpic.destroyDrawingCache();

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK
				&& resultCode == Activity.RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			userpic.setImageBitmap(photo);
			try {
				File dir = new File(fullPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				OutputStream fOut = null;
				File file = new File(fullPath, CM_Status.getUserid() + ".jpg");
				file.createNewFile();
				fOut = new FileOutputStream(file);

				// 100 means no compression, the lower you go, the stronger the
				// compression
				photo.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
				fOut.flush();
				fOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.radio0:
			if (checked)
				newUserGesch = true;
			break;
		case R.id.radio1:
			if (checked)
				newUserGesch = false;
			break;
		}
	}
	@Override
	public void onBackPressed() {
	}
	public void goBack(View view) {
		File file = new File(fullPath, CM_Status.getUserid() + ".jpg");
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(file.exists())
		{
			file.delete();
		}
		Intent intent = new Intent(view.getContext(), ActivityUser.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
	}

}
