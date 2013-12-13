package projekt.Activitys;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.User;
import OwnList.CustomListViewAdapter;
import OwnList.RowItem;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cocktailmixxer.R;

public class ActivityUser extends Activity {
	Button newUser;
	CM_Status status;
	List<RowItem> users;
	Spinner sp1;
	CustomListViewAdapter adapter;
	TextView Promille;
	TextView Fahren;
	DecimalFormat f = new DecimalFormat("0.0000");

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		status = (CM_Status) getApplicationContext();
		users = status.get_UserList();
		Promille = (TextView) findViewById(R.id.user_promille);
		Fahren = (TextView) findViewById(R.id.user_fahren);
		Promille.setText("");
		Fahren.setText("");
		newUser = (Button) findViewById(R.id.btn_newuser);
		sp1 = (Spinner) findViewById(R.id.user_SpinnerUsers);


		registerForContextMenu(sp1);
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
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onNothingSelected(AdapterView<?> arg0) { //

			}


			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				try {
					status.set_ActiveUser((User) sp1.getSelectedItem());

					status.get_ActiveUser().calcAlkAnteil();
					Promille.setText("Aktueller Promillewert: "
							+ f.format(status.get_ActiveUser().getPromille())
							+ "‰");
					Fahren.setText("Fahrtüchtig in: " + 0 + "std");
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Fehler",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		

		newUser.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(ActivityUser.this,
						AddUserActivity.class));
			}

		});
		if (users.isEmpty()){
			sp1.setVisibility(View.INVISIBLE);
		}
		else{
			sp1.setVisibility(View.VISIBLE);
			sp1.setSelection(adapter.getPosition(status.get_ActiveUser()));
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	protected void onStart() { // TODO Auto-generated method stub
		super.onStart();
	}

	public void deleteUser(View view) {
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
									adapter.notifyDataSetChanged();
									if (!users.isEmpty()) {
										status.set_ActiveUser((User) users
												.get(0));
										Promille.setText("Aktueller Promillewert: "
												+ f.format(status
														.get_ActiveUser()
														.getPromille()) + "‰");
										Fahren.setText("Fahrtüchtig in: " + 0
												+ "std");

									} else {
										status.set_ActiveUser(null);
										Promille.setText("");
										Fahren.setText("");
										sp1.setVisibility(View.INVISIBLE);
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
