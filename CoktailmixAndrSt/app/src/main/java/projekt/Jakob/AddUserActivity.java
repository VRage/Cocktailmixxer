package projekt.Jakob;
//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Jakob Nisin
//@ Description: Neuer Benutzer wird angelegt. Name, Gewicht, Größe, Geschlecht und Geburtsdatum wird abgefragt.
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
	//Deklaration ...
	//für Editierbaren Text
	EditText newUserLength;
	static EditText newUserName;
	private EditText newUserWeight;
	private boolean newUserGesch = true;
	private DatePicker dpResult;		//Datumauswahl
	CM_Status status;					//Status
	static int TAKE_PICTURE = 1;		//intention parameter um bild aufzunehmen
	private Bitmap photo;				//Aufgenommenes Bild
	private ImageView userpic;			//Bildanzeige
	private static final int CAMERA_REQUEST = 1888;	//Anfrageparamter um auf Aufnahmemodus zu wechseln
	public final static String APP_PATH_SD_CARD = "/cocktailmixxer/userpics";	//SpeicherOrt wird definiert
	String fullPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + APP_PATH_SD_CARD;	//dito

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		status = (CM_Status) getApplicationContext();
		setContentView(R.layout.activity_add_user);

		// Deklaration der editierbaren Textfelder
		newUserName = (EditText) findViewById(R.id.user_editTextName);
		newUserLength = (EditText) findViewById(R.id.user_edittextSize);
		newUserWeight = (EditText) findViewById(R.id.user_edittextWeight);
		newUserName = (EditText) findViewById(R.id.user_editTextName);
		dpResult = (DatePicker) findViewById(R.id.datePicker1);
		userpic = (ImageView) findViewById(R.id.user_pic);

		// Standart Datumswert für den Datepicker wird definiert
		dpResult.init(1990, 05, 15, null);
		
		if (savedInstanceState != null) {  //Beim Umkippen der Ansicht wird das bild korrekt angezeigt
			Bitmap photo = savedInstanceState.getParcelable("savedImage");
			userpic.setImageBitmap(photo);

		}
		super.onCreate(savedInstanceState);
	}

	public void addUser(View view) {  //Aktion für "neu anlegen"button wird definiert
		try {
			status.add_UserItem(new User(newUserName.getText().toString(), //Neuer benutzer wird angelegt und in Userliste eingefügt
					Double.parseDouble(newUserWeight.getText().toString()),
					Double.parseDouble(newUserLength.getText().toString()),
					newUserGesch, new GregorianCalendar(dpResult.getYear(),
							dpResult.getMonth() + 1, dpResult.getDayOfMonth()))); // Abfrage der EditText felder und des Datepickers
			status.saveToSerFile("User", status.get_UserList());				  // User wird auf internen gespeichert
			//Aufrufen der User Activity
			Intent intent = new Intent(view.getContext(), ActivityUser.class); 	  
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);								

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"Bitte Eingaben überprüfen", Toast.LENGTH_LONG).show();//falls ein Feld leer wird bekommt man ein hinweis
		}

	}

	public void takePic(View view) {

		// Erstellen einer intent mit ACTION_IMAGE_CAPTURE aktion
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST); //aufrufen der Kamera
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);	//bei "zerstörung" der Activity wird das Bild in der Bildanzeige temporer gespeichert, ...
		userpic.buildDrawingCache();			//sowas passiert z.b. beim Kippen der Ansicht
		Parcelable savedpic = Bitmap.createBitmap(userpic.getDrawingCache());
		outState.putParcelable("savedImage", savedpic);
		userpic.destroyDrawingCache();

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK
				&& resultCode == Activity.RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");		//Bild wird von der Kamera empfangen
			userpic.setImageBitmap(photo);
			try {
				File dir = new File(fullPath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				OutputStream fOut = null;
				File file = new File(fullPath, CM_Status.getUserid() + ".jpg"); //Datei wird für das Bild definiert..
				file.createNewFile();											//und auf dem speicher angelegt
				fOut = new FileOutputStream(file);								//Bild von der Kamera wird in die Datei geschrieben
				photo.compress(Bitmap.CompressFormat.JPEG, 100, fOut);			//Bild wird komprimiert
				fOut.flush();
				fOut.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void onRadioButtonClicked(View view) {
		boolean checked = ((RadioButton) view).isChecked();
		switch (view.getId()) {				//Abfrage des Geschlechts des Users mittels Radiobutton
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
	public void onBackPressed() { //Die Backtastenfunktion wird überschrieben d.h. unbrauchbar gemacht 
	}
	public void goBack(View view) {	//damit man nur über diese Methode zurück kommt
		File file = new File(fullPath, CM_Status.getUserid() + ".jpg"); //Bild vom Speicher abrufen
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(file.exists())
		{
			file.delete();				// Bild löschen
		}
		Intent intent = new Intent(view.getContext(), ActivityUser.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);			//Zurück zur User Activity springen
	}

}
