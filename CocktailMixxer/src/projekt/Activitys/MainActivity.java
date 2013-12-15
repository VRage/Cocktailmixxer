/*		Activity- 		For Activity Classes
 * 		btn_ - 			For Button Ids and Button Methods
 * 		button - 		For Button Strings
 * 
 */
package projekt.Activitys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.User;
import OwnList.CustomListViewAdapter;
import OwnList.RowItem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import com.example.cocktailmixxer.R;

public class MainActivity extends Activity {
	CM_Status status;
	CustomListViewAdapter adapter;
	Spinner spinnerUser;
	List<RowItem> users;
	public final static String APP_PATH_SD_APPLICATION = "/cocktailmixxer/";
	static String fullPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + APP_PATH_SD_APPLICATION;

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;// return true so to menu pop up is opens

	}

	@Override
	protected void onStop() {
		status.saveAll();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
		if (users.isEmpty()) {
			spinnerUser.setVisibility(View.INVISIBLE);
		} else {
			spinnerUser.setVisibility(View.VISIBLE);
			spinnerUser.setSelection(users.indexOf(status.get_ActiveUser()));
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		status = (CM_Status) getApplicationContext();
		status.loadAll();
		users = status.get_UserList();
		setContentView(R.layout.activity_main);
		spinnerUser = (Spinner) findViewById(R.id.main_spinnerUsers);
		adapter = new CustomListViewAdapter(this, R.layout.activity_listitem,
				users);
		spinnerUser.setAdapter(adapter);
		spinnerUser.setSelection(adapter.getPosition(status
				.get_ActiveUser()));
		// Define Button **Cocktails** and On Click Action
		Button btn_CocktailListe = (Button) findViewById(R.id.main_btnCocktails);
		btn_CocktailListe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this,
						ActivityCocktailList.class)); // btnPressedCockTailsList();
			}
		});

		Button btn_User = (Button) findViewById(R.id.btn_User);
		btn_User.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, ActivityUser.class)); // btnPressedCockTailsList();
			}
		});

		// Define Button **Connect** and On Click Action
		Button btn_Bluetooth = (Button) findViewById(R.id.main_btnConnect);
		btn_Bluetooth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this,
						ActivityBluetooth.class));
			}

		});
		spinnerUser.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				try {
					status.set_ActiveUser((User) spinnerUser.getSelectedItem());

				} catch (Exception e) {

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});

		Button btn_NewCocktail = (Button) findViewById(R.id.main_btnNewCocktail);
		btn_NewCocktail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this,
						ActivityNewCocktail.class)); // btnPressedCockTailsList();
			}

		});

	}

	public void gotoBenutzer() {
		setContentView(R.layout.activity_user);
	}

//	public static boolean saveStatus(CM_Status status) {
//		try {
//			File statusFile = new File(fullPath, "cm_status.dat");
//			statusFile.createNewFile();
//			FileOutputStream fos = new FileOutputStream(statusFile);
//			ObjectOutputStream oos = new ObjectOutputStream(fos);
//			oos.writeObject(status);
//			oos.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//	}
//
//	public static CM_Status getStatus() { // Changed new method
//		File statusFile = new File(fullPath, "cm_status.dat");
//		if (statusFile.exists()) {
//			try {
//
//				FileInputStream fis = new FileInputStream(statusFile);
//				ObjectInputStream is = new ObjectInputStream(fis);
//				Object readObject = is.readObject();
//				is.close();
//
//				if (readObject != null && readObject instanceof CM_Status) {
//					return (CM_Status) readObject;
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}

}