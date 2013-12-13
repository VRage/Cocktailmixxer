package projekt.Activitys;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Cocktail;
import OwnList.CustomListViewAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.cocktailmixxer.ActivityCocktail;
import com.example.cocktailmixxer.R;

public class ActivityCocktailList extends Activity {
	CM_Status status;
	CustomListViewAdapter CLW;
	ListView lw;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cocklist);
		status = (CM_Status)getApplicationContext();
		lw = (ListView) findViewById(R.id.cocktaillist_ListView);
		CLW = new CustomListViewAdapter(this, R.layout.activity_listitem,
				status.get_CocktailList());
		
		lw.setAdapter(CLW);
		lw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				status.set_ActiveCocktail((Cocktail) status.get_CocktailList().get(arg2));
				startActivity(new Intent(ActivityCocktailList.this, ActivityCocktail.class));
				
			}
		});
	}
	

}
