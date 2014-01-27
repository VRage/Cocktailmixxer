package projekt.Matze;

import java.util.List;

import projekt.OwnList.CustomListViewAdapter;
import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Cocktail;
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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cocktailmixxer.R;
//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Matthias Wildberg
public class ActivityCocktailList extends Activity {
	CM_Status status;
	CustomListViewAdapter CLW;
	ListView lw;
	Switch switcher;
	Button newCocktail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cocklist);
		status = (CM_Status)getApplicationContext();
		newCocktail = (Button)findViewById(R.id.cocktails_btnNewCocktail);
		lw = (ListView) findViewById(R.id.cocktaillist_ListView);
		switcher = (Switch) findViewById(R.id.cocktailslist_switch);
		CLW = new CustomListViewAdapter(this, R.layout.activity_listitem,
				status.get_CocktailList());
		
		final Builder builder = new Builder(this);
		lw.setAdapter(CLW);
		lw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(switcher.isChecked())
					status.set_ActiveCocktail((Cocktail) status.get_CocktailListOrderable().get(arg2));
				else
				status.set_ActiveCocktail((Cocktail) status.get_CocktailList().get(arg2));
				startActivity(new Intent(ActivityCocktailList.this, ActivityCocktail.class));
				
			}
		});
		lw.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				
				
				builder.setTitle("ACHTUNG!!")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setMessage(
								"Sind Sie sicher dass Sie >>"+status.get_CocktailList().get(arg2).getTitle()+"<< löschen wollen?")
						.setPositiveButton("Löschen",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										
										status.get_CocktailList().remove(arg2);
										CLW.notifyDataSetChanged();
										
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
								});
				builder.show();
				
				
				
			
				return false;
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
								
							}
							else{ 
								allSaftincluded = false;	
								
								
							}
							
							
						}
						if(allSaftincluded){
							if(!Cocktaillistorderable.contains(cocktail)){
								Cocktaillistorderable.add(cocktail);
								
							}
		
						}
					}
					CLW = new CustomListViewAdapter(getApplicationContext(), R.layout.activity_listitem,
							Cocktaillistorderable);
					
					lw.setAdapter(CLW);
					CLW.notifyDataSetChanged();
					
				}
				else {
					status.get_CocktailListOrderable().clear();
					CLW = new CustomListViewAdapter(getApplicationContext(), R.layout.activity_listitem,
							status.get_CocktailList());
				lw.setAdapter(CLW);
				CLW.notifyDataSetChanged();
				}
			}
		});
		newCocktail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ActivityCocktailList.this,ActivityNewCocktail.class));
				
			}
		});
	}
	@Override
	protected void onResume() {
		CLW.notifyDataSetChanged();
		super.onResume();
	}


}
