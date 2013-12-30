package projekt.MAtze;

import java.util.List;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Cocktail;
import OwnList.CustomListViewAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cocktailmixxer.R;

public class ActivityCocktailList extends Activity {
	CM_Status status;
	CustomListViewAdapter CLW;
	ListView lw;
	Switch switcher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cocklist);
		status = (CM_Status)getApplicationContext();
		lw = (ListView) findViewById(R.id.cocktaillist_ListView);
		switcher = (Switch) findViewById(R.id.cocktailslist_switch);
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
		switcher.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					List Cocktaillist = status.get_CocktailList();
					List Cocktaillistorderable = status.get_CocktailListOrderable();
					for (int i = 0; i < Cocktaillist.size(); i++) {
						Cocktail cocktail = (Cocktail) Cocktaillist.get(i);
						List saftliste = cocktail.get_SaftList_cocktail();
						boolean allSaftincluded = true;
						
						
						for (int j = 0; j < saftliste.size(); j++) {
							if(status.get_SaftList_intern().contains(saftliste.get(j)))
							{
								Toast.makeText(getApplicationContext(), "Saft enthalten !!", Toast.LENGTH_SHORT).show();
							}
							else{ 
								allSaftincluded = false;	
							}
							
						}
						if(allSaftincluded)
							if(!Cocktaillistorderable.contains(cocktail))
								Cocktaillistorderable.add(cocktail);
					}
					CLW = new CustomListViewAdapter(getApplicationContext(), R.layout.activity_listitem,
							status.get_CocktailListOrderable());
					
					lw.setAdapter(CLW);
					CLW.notifyDataSetChanged();
					
				}
				else 
					CLW = new CustomListViewAdapter(getApplicationContext(), R.layout.activity_listitem,
							status.get_CocktailList());
				lw.setAdapter(CLW);
				CLW.notifyDataSetChanged();
			}
		});
	}
	

}