package projekt.Jakob;

import java.util.List;

import projekt.OwnList.CustomListViewAdapter;
import projekt.OwnList.RowItem;
import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Saft;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.example.cocktailmixxer.R;
//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Jakob Nisin
//@ Description: Liste mit den in der Maschine enthaltenen Säften wird angezeigt.
public class ActivityAdmin extends Activity implements OnClickListener {
	// Deklaration ..
	CM_Status status;				//Status 
	List<RowItem> safte;			// Liste der Säfte
	ListView saftlist_intern;		// ListView
	static Button b1, b2, b3, b4, b5, b6, b7, b8; // buttons
	CustomListViewAdapter adapter;	// Adapter

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		// Zuweisungen
		status = (CM_Status) getApplicationContext();

		saftlist_intern = (ListView) findViewById(R.id.admin_saftintern);
		safte = status.get_SaftList_intern();

		adapter = new CustomListViewAdapter(this,R.layout.activity_listitem, safte);
		saftlist_intern.setAdapter(adapter);
	
		TabHost thost = (TabHost) findViewById(R.id.tabhost);
		thost.setup();
		TabSpec spec = thost.newTabSpec("tag1");
		spec.setContent(R.id.Behaelter);
		spec.setIndicator("Behälter");
		thost.addTab(spec);
		spec = thost.newTabSpec("tag2");
		spec.setContent(R.id.Einstellungen);
		spec.setIndicator("Einstellungen");
		thost.addTab(spec);
		
		final Builder builder = new Builder(this); //Erstellen eines Dialogs
		//Beim "klicken" auf einer Saft in der ListView wird gefragt ob man den Saft löschen will
		saftlist_intern.setOnItemClickListener(new OnItemClickListener() {
			Intent intent = new Intent(ActivityAdmin.this,SelectSaftActivity.class);
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				intent.putExtra("Bottlenumber", ""+arg2);
				startActivity(intent);
				
			}
		});
		saftlist_intern.setOnItemLongClickListener(new OnItemLongClickListener() { 

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				
				builder.setTitle("ACHTUNG!!")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setMessage(
								"Sind Sie sicher, dass Sie den Saft löschen wollen?")
						.setPositiveButton("Löschen",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

											safte.set(safte.indexOf((Saft)saftlist_intern.getItemAtPosition(arg2)), new Saft("","",0));
										
										adapter.notifyDataSetChanged();
										
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
	


	@Override
	protected void onResume() {
		adapter.notifyDataSetChanged();  //Bei wiederaufruf der Activity wird der Adapter aktualisiert 
		super.onResume();
		
	}



	
	public void clearList(View view)  //Methode für den Clearbutton
	{
		Builder builder = new Builder(this);
		//Erstellen eines Dialogs
		//Beim "klicken" auf einer Saft in der ListView wird gefragt ob man den Saft löschen will
		builder.setTitle("ACHTUNG!!")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage(
						"Sind Sie sicher dass Sie alle Säfte löschen wollen?")
				.setPositiveButton("Löschen",						
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								for(int i=0;i<8;i++){					//löschung aller Elemente aus der liste
									
									safte.set(i, new Saft("","",0));
								}
								adapter.notifyDataSetChanged();
								
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



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
