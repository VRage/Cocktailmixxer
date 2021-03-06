package projekt.Matze;

import projekt.OwnList.CustomListViewAdapter;
import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Cocktail;
import projekt.helpclasses.Saft;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cocktailmixxer.R;
//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Matthias Wildberg
public class ActivityNewCocktail extends Activity {
	SeekBar seekbar;
	TextView Name;
	TextView Desc;
	int imageId;
	ImageView Icon;
	Button newSaft;
	Button order;
	CM_Status status;
	CustomListViewAdapter CLWA;
	ListView SaefteCocktail;
	Cocktail buildetCocktail = new Cocktail(R.drawable.cocktail_icon_lol, "NoName","-");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newcocktail);
		Icon = (ImageView) findViewById(R.drawable.cocktail_icon_lol);
		newSaft = (Button) findViewById(R.id.Cocktail_btnOrder);
		SaefteCocktail = (ListView) findViewById(R.id.newCocktail_listSaefte3);
		//SaefteCocktail = (ListView) findViewById(R.id.newCocktail_listSaefte);
		status = (CM_Status) getApplicationContext();
		status.set_ActiveCocktail(buildetCocktail);
		CLWA = new CustomListViewAdapter(this, R.layout.activity_listitem,
				buildetCocktail.get_SaftList_cocktail());
		order = (Button) findViewById(R.id.newCocktail_btnBestellen);
		SaefteCocktail.setAdapter(CLWA);
		Name = (TextView) findViewById(R.id.newCocktail_editTextName);
		Desc = (TextView)findViewById(R.id.newCocktail_editTextDesc);

		SaefteCocktail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// Hole angeklickten cocktail 
				status.set_ActiveCocktail(buildetCocktail);
				startActivity(new Intent(ActivityNewCocktail.this,
						ActivitySetMlSaft.class));
				status.get_ActiveCocktail().setActiveSaft((Saft) SaefteCocktail.getItemAtPosition(arg2));

			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		CLWA.notifyDataSetChanged();
	}

	public void newcocktails_onClickOrder(View view)
			throws InterruptedException

	{
		// Toast.makeText(this, "Coktail hinzugefügt",
		// Toast.LENGTH_LONG).show();
		try {
			if(buildetCocktail.get_SaftList_cocktail().get(0)==null){
				Toast.makeText(getApplicationContext(), "Getränkeliste ist leer", Toast.LENGTH_SHORT);
			}
			else if(!Name.getText().toString().equals(""))
			{
			buildetCocktail.setTitle(Name.getText().toString());
			buildetCocktail.setDesc(Desc.getText().toString());
			
			status.add_CocktailItem(buildetCocktail);
			status.saveToSerFile("Cocktail", status.get_CocktailList());
			finish();
			}
			else{
				Toast.makeText(getApplicationContext(),"Bitte gültigen Cocktail Namen eingeben ! "/*"Bitte gültigen Namen und/oder beschreibung eintragen"*/, Toast.LENGTH_LONG).show();
			}
			
				
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "Getränkeliste ist leer \n\n\n" + e.toString(), Toast.LENGTH_LONG).show();
		}

		// startActivity(new Intent(MainActivity.this,
		// ActivityBluetooth.class)); //btnPressedCockTailsList();
	}

	public void newCocktail_OnClick_newSaft(View v) {
		status.set_ActiveCocktail(buildetCocktail);
		startActivity(new Intent(ActivityNewCocktail.this,
				ActivityAddSaft.class));
	}

}
