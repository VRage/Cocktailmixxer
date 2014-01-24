package projekt.Jakob;

import java.util.List;

import projekt.OwnList.CustomListViewAdapter;
import projekt.OwnList.RowItem;
import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Saft;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cocktailmixxer.R;
//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Jakob Nisin
public class ActivityAdmin extends Activity implements OnClickListener {
	CM_Status status;
	List<RowItem> safte;
	ListView saftlist_intern;
	static Button b1, b2, b3, b4, b5, b6, b7, b8;
	CustomListViewAdapter adapter;

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		status = (CM_Status) getApplicationContext();

		saftlist_intern = (ListView) findViewById(R.id.admin_saftintern);
		safte = status.get_SaftList_intern();

		adapter = new CustomListViewAdapter(this,R.layout.activity_listitem, safte);
		saftlist_intern.setAdapter(adapter);
	

		b1 = (Button) findViewById(R.id.admin_behalter1);
		b2 = (Button) findViewById(R.id.admin_behalter2);
		b3 = (Button) findViewById(R.id.admin_behalter3);
		b4 = (Button) findViewById(R.id.admin_behalter4);
		b5 = (Button) findViewById(R.id.admin_behalter5);
		b6 = (Button) findViewById(R.id.admin_behalter6);
		b7 = (Button) findViewById(R.id.admin_behalter7);
		b8 = (Button) findViewById(R.id.admin_behalter8);

		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b7.setOnClickListener(this);
		b8.setOnClickListener(this);
		
		final Builder builder = new Builder(this);
		
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
		adapter.notifyDataSetChanged();
		try{
		List saefte = status.get_SaftList_intern();
		String ausgabe ="Saefte;";
		for (int i = 0; i < saefte.size(); i++) {
			
			try{
			Saft temp = (Saft) saefte.get(i);
			ausgabe = ausgabe+temp.getTitle()+"\n";
			}
			catch(Exception e)
			{
				
			}
		}

		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
		
		super.onResume();
		
	}



	public void onClick(View v)
	{
		Intent intent = new Intent(ActivityAdmin.this,SelectSaftActivity.class);
		switch (v.getId()){
		case R.id.admin_behalter1:
			intent.putExtra("Bottlenumber", "1");
			startActivity(intent);
			break;
		case R.id.admin_behalter2:
			intent.putExtra("Bottlenumber", "2");
			startActivity(intent);
			break;
		case R.id.admin_behalter3:
			intent.putExtra("Bottlenumber", "3");
			startActivity(intent);
			break;
		case R.id.admin_behalter4:
			intent.putExtra("Bottlenumber", "4");
			startActivity(intent);
			break;
		case R.id.admin_behalter5:
			intent.putExtra("Bottlenumber", "5");
			startActivity(intent);
			break;
		case R.id.admin_behalter6:
			intent.putExtra("Bottlenumber", "6");
			startActivity(intent);
			break;
		case R.id.admin_behalter7:
			intent.putExtra("Bottlenumber", "7");
			startActivity(intent);
			break;
		case R.id.admin_behalter8:
			intent.putExtra("Bottlenumber", "8");
			startActivity(intent);
			break;
			
		}
	}
	public void clearList(View view)
	{
		Builder builder = new Builder(this);
		builder.setTitle("ACHTUNG!!")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setMessage(
						"Sind Sie sicher dass Sie alle Säfte löschen wollen?")
				.setPositiveButton("Löschen",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								
								for(int i=0;i<8;i++){
									
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
}
