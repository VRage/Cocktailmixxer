package projekt.Jakob;
//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Jakob Nisin
//@ Description: Benutzer werden angezeigt und eine Liste ihrer verzehrten Cocktails
import java.text.DecimalFormat;
import java.util.List;

import projekt.OwnList.CustomListViewAdapter;
import projekt.OwnList.RowItem;
import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Cocktail;
import projekt.helpclasses.User;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cocktailmixxer.R;

public class ActivityUser extends Activity {
	//Deklaration ... test test stest
	Button newUser;			// Neuer benutzer Button
	CM_Status status;		// status
	List<RowItem> users;	// Userliste
	List<RowItem> cocktail;	// Cocktailliste vom User
	ListView cocktails_list;// Cocktaillistview
	Spinner sp1;			// spinner zum anzeigen der User
	CustomListViewAdapter adapter; 	//Adapter für User
	CustomListViewAdapter adapter_cocktail; //adapter für Cocktails
	TextView Promille;		// Textansicht für Promillewert des Users
	DecimalFormat f = new DecimalFormat("0.0000"); // Formatierung für den Promillewert

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		//Zuweisungen
		cocktails_list = (ListView) findViewById(R.id.User_Cocktaillist);
		status = (CM_Status) getApplicationContext();
		users = status.get_UserList();
		Promille = (TextView) findViewById(R.id.user_promille);
		Promille.setText("");
		newUser = (Button) findViewById(R.id.btn_newuser);
		sp1 = (Spinner) findViewById(R.id.user_SpinnerUsers);
		
		if (status.get_ActiveUser() != null) {	//wenn es einen User gibt wird cocktailliste abgefragt und adapter gesetzt
			cocktail = status.get_ActiveUser().get_CocktailList();
			adapter_cocktail = new CustomListViewAdapter(this,
					R.layout.activity_listitem, cocktail);
			cocktails_list.setAdapter(adapter_cocktail);
		}

		registerForContextMenu(sp1); 	//Aufklappmenü 
		adapter = new CustomListViewAdapter(this, R.layout.activity_listitem,
				users);

		sp1.setAdapter(adapter);
		sp1.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				openContextMenu(v);
				return false;
			}

		});
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {	//Aufklappen des Usersauswahls


			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, // User wird ausgewählt
					int arg2, long arg3) {
				try {												
					status.set_ActiveUser((User) sp1.getSelectedItem());//ausgewählter User wird activeUser
					status.get_ActiveUser().calcAlkAnteil();			  //berechnung des Promillewerts
					cocktail = status.get_ActiveUser().get_CocktailList();//Cocktailliste wird aufgerufen
					adapter_cocktail = new CustomListViewAdapter(getApplicationContext(),
							R.layout.activity_listitem, cocktail);
					cocktails_list.setAdapter(adapter_cocktail);
					Promille.setText("Aktueller Promillewert: "
							+ f.format(status.get_ActiveUser().getPromille())		//aktueller Promillewert wird mit formatierung angezeigt
							+ "‰");
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Fehler: " + e.toString(), Toast.LENGTH_LONG)
							.show();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		newUser.setOnClickListener(new OnClickListener() { // Aktion für neuer benutzer button 
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(ActivityUser.this,
						AddUserActivity.class));			// Activity wird aufgerufen um neuen Benutzer anzulegen
			}

		});
		if (users.isEmpty()) {					//Wenn es keine User gibt wird der Spinner versteckt, ansonsten angezeigt
			sp1.setVisibility(View.INVISIBLE);
		} else {
			sp1.setVisibility(View.VISIBLE);
			sp1.setSelection(adapter.getPosition(status.get_ActiveUser()));
		}
		final Builder builder = new Builder(this);//Erstellen eines Dialogs
		//Beim "klicken" auf einer Cocktail in der ListView wird gefragt ob man den Cocktail löschen will
		cocktails_list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				
				builder.setTitle("ACHTUNG!!")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setMessage(
								"Sind Sie sicher, dass Sie den Cocktail löschen wollen?")
						.setPositiveButton("Löschen",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										//Cocktail wird entfernt
											status.get_ActiveUser().get_CocktailList().remove((Cocktail)cocktails_list.getItemAtPosition(arg2));
											status.get_ActiveUser().setAlkgehalt();
											status.get_ActiveUser().calcAlkAnteil();  //Neuer Promillewert wird berechnet und angezeigt
											Promille.setText("Aktueller Promillewert: "
													+ f.format(status.get_ActiveUser().getPromille())
													+ "‰");
											adapter_cocktail.notifyDataSetChanged();	// Adapter wird aktualisiert
										
									}
								})
						.setNegativeButton("Abbruch",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

										dialog.cancel();
									}
								}).show();

			
				return false;
			}
		});
		
	}

	public void deleteUser(View view) { //Aktion für "Löschen" button wird definiert
		if (!users.isEmpty()) {
			Builder builder = new Builder(this);
			builder.setTitle("ACHTUNG!!")
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setMessage(
							"Sind Sie sicher dass Sie den Benützer löschen wollen?")
					.setPositiveButton("Löschen",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									
									status.delete_UserItem((User) sp1
											.getSelectedItem());
									adapter.notifyDataSetChanged(); //User wird gelöscht und Adapter wird aktualisiert
									
									
									if (!users.isEmpty()) { //Falls es noch User gibt ...
										status.set_ActiveUser((User) users
												.get(0));
										Promille.setText("Aktueller Promillewert: "	//User an der Position 0 wird neuer aktive User
												+ f.format(status
														.get_ActiveUser()
														.getPromille()) + "‰");
										cocktail = status.get_ActiveUser().get_CocktailList();
										adapter_cocktail = new CustomListViewAdapter(getApplicationContext(),
												R.layout.activity_listitem, cocktail);
										cocktails_list.setAdapter(adapter_cocktail);
									} else {
										adapter_cocktail.clear();		//Cocktailliste wird gereiningt
										status.set_ActiveUser(null);	//aktiver User wird auf Null gesetzt
										Promille.setText("");			//Promille textanzeige wird "unsichtbar" gestellt
										sp1.setVisibility(View.INVISIBLE);//spinner ist unsichtbar
									}
								}
							})
					.setNegativeButton("Abbruch",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub

									dialog.cancel();
								}
							}).show();

		}
	}

}
