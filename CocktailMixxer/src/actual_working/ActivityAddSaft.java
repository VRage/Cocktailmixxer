package actual_working;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Cocktail;
import OwnList.CustomListViewAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.cocktailmixxer.ActivitySetMlSaft;
import com.example.cocktailmixxer.R;

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
				status.get_SaftList_intern());
		lw.setAdapter(CLW);
		
		
		lw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Saft temp=null;
				try {
					temp = (Saft) ((Saft) status.get_SaftList_intern().get(arg2)).clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				status.get_ActiveCocktail().setActiveSaft(temp);
				
				startActivity(new Intent (ActivityAddSaft.this, ActivitySetMlSaft.class));
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cocktail, menu);
		return true;
	}
	public void saefte_btnNewCocktails_onClick(View v){
		startActivity(new Intent(ActivityAddSaft.this, NewSaft.class));
	}


}