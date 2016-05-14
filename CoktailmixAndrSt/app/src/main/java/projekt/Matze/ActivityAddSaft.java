package projekt.Matze;

import projekt.OwnList.CustomListViewAdapter;
import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Saft;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.cocktailmixxer.R;
//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Matthias Wildberg
public class ActivityAddSaft extends Activity {
	CM_Status status;
	CustomListViewAdapter CLW;
	ListView lw;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saftlist);
		status = (CM_Status)getApplicationContext();
		lw = (ListView) findViewById(R.id.saftlist_ListView);
		CLW = new CustomListViewAdapter(this, R.layout.activity_listitem,
				status.get_SaftList_all());
		lw.setAdapter(CLW);
		
		
		lw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Saft temp=null;
				try {
					temp = (Saft) ((Saft) status.get_SaftList_all().get(arg2)).clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(status.get_ActiveCocktail().get_SaftList_cocktail().contains(temp)){
					Toast.makeText(getApplicationContext(), "Getränk bereits in Cocktailliste, \nbitte anderes Getränk wählen", Toast.LENGTH_LONG).show();
				}
				else{
					status.get_ActiveCocktail().setActiveSaft(temp);
					
					startActivity(new Intent (ActivityAddSaft.this, ActivitySetMlSaft.class));
					finish();
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cocktail, menu);
		return true;
	}


}
