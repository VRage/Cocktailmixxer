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
		SaefteCocktail = (ListView) findViewById(R.id.newCocktail_listSaefte);
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
				status.set_ActiveCocktail(buildetCocktail);
				// Hole angeklickten saft 
				Saft safttemp = (Saft) status.get_ActiveCocktail().get_SaftList_cocktail().get(arg2);
				//Reseten der ml 
				status.get_ActiveCocktail()
						.setMlLeft(
								status.get_ActiveCocktail().getMlLeft()+safttemp.getMl()
								);
				((Saft) status.get_ActiveCocktail().get_SaftList_cocktail().get(arg2)).setMl(0);
						
				startActivity(new Intent(ActivityNewCocktail.this,
						ActivitySetMlSaft.class));

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
			String temp = Name.getText().toString();
			buildetCocktail.setTitle(temp);
			buildetCocktail.setDesc(Desc.getText().toString());
			status.add_CocktailItem(buildetCocktail);
			status.saveToSerFile("Cocktail", status.get_CocktailList());
			finish();
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
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
