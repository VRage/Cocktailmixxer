package projekt.Activitys;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Cocktail;
import OwnList.CustomListViewAdapter;
import actual_working.ActivityAddSaft;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cocktailmixxer.ActivitySetMlSaft;
import com.example.cocktailmixxer.R;

public class ActivityNewCocktail extends Activity {
	SeekBar seekbar;
	TextView Name;
	int imageId;
	ImageView Icon;
	Button newSaft;
	Button order;
	CM_Status status;
	CustomListViewAdapter CLWA;
	ListView SaefteCocktail;
	Cocktail buildetCocktail = new Cocktail(R.drawable.cocktail_icon_lol, "-");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newcocktail);
		Icon = (ImageView) findViewById(R.drawable.cocktail_icon_lol);
		newSaft = (Button) findViewById(R.id.newCocktail_btnNewSaft);
		SaefteCocktail = (ListView) findViewById(R.id.newCocktail_listSaefte);
		status = (CM_Status) getApplicationContext();
		status.set_ActiveCocktail(buildetCocktail);
		CLWA = new CustomListViewAdapter(this, R.layout.activity_listitem,
				buildetCocktail.get_SaftList_cocktail());
		order = (Button) findViewById(R.id.newCocktail_btnBestellen);
		SaefteCocktail.setAdapter(CLWA);
		Name = (TextView) findViewById(R.id.newCocktail_editTextName);

		SaefteCocktail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
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
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}

		status.add_CocktailItem(buildetCocktail);
		finish();
		// startActivity(new Intent(MainActivity.this,
		// ActivityBluetooth.class)); //btnPressedCockTailsList();
	}

	public void newCocktail_OnClick_newSaft(View v) {
		status.set_ActiveCocktail(buildetCocktail);
		startActivity(new Intent(ActivityNewCocktail.this,
				ActivityAddSaft.class));
	}

}
