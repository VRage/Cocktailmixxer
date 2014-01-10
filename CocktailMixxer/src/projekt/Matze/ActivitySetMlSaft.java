package projekt.Matze;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Cocktail;
import projekt.helpclasses.Saft;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cocktailmixxer.R;

public class ActivitySetMlSaft extends Activity {
	CM_Status status;
	SeekBar seekbarValueMl;
	TextView MlLeft;
	TextView MlActual;
	Button NewSaft;
	Cocktail cocktail;
	Saft saft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addsaft);
		
		status = (CM_Status) getApplicationContext();
		seekbarValueMl = (SeekBar) findViewById(R.id.addSaft_seekbar);
		saft = status.get_ActiveCocktail().getActiveSaft();
		
		MlLeft = (TextView) findViewById(R.id.addSaft_textViewMlLeft);
		MlActual = (TextView) findViewById(R.id.addSaft_textViewMlActual);
		NewSaft = (Button) findViewById(R.id.addSaft_ButtonNewSaft);
		cocktail = status.get_ActiveCocktail();
		

		// Setze menge auf Progress
		seekbarValueMl.setProgress(cocktail.cocktailsize-cocktail.getMlLeft());
		int Progress = seekbarValueMl.getProgress()-(cocktail.cocktailsize-cocktail.getMlLeft());
		// Set default text of textView
		MlLeft.setText("Verfügbare Menge:" +(cocktail.cocktailsize- cocktail.getMlLeft()));
		MlActual.setText("Aktuelle Menge:" + Progress);

		seekbarValueMl
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					int MlLeftv = cocktail.getMlLeft();
					int cocktailsize = cocktail.cocktailsize;
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						int Progress = seekbarValueMl.getProgress()-(cocktail.cocktailsize-MlLeftv);

						//Die Progressbar darf nicht weniger werden als der Cocktail schon zur verfügung hat 
						if (Progress < 0)
							seekbarValueMl.setProgress(cocktail.cocktailsize-MlLeftv);
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// hole aktuellen Prozessstatus
						int Progress = seekbarValueMl.getProgress()-(cocktail.cocktailsize-MlLeftv);
						

						// Grenzen setzen darf nicht kleiner sein als aktuell
						// noch verfügbar und nicht größer sein als cocktail


						// Setze textfelder
						MlLeft.setText("Verfügbare Menge:\t\t"
								+ (MlLeftv - Progress)+" ml");
						MlActual.setText("Aktuelle Menge:\t\t" + Progress+" ml");

					}

				});
		


		NewSaft.setOnClickListener(new OnClickListener() {

			/*
			 * Setze On ClickListener für "Neu Anlegen
			 * 
			 * Setzt interne SaftMl auf den Progress wert 
			 * Setzt Die Die Verbleibenden Ml des Cocktails 
			 * Addet den Saft zur cocktailListe 
			 */



			@Override
			public void onClick(View v) {
				int Progress = seekbarValueMl.getProgress()-(cocktail.cocktailsize-cocktail.getMlLeft());
				
				
				Toast.makeText(getApplicationContext(), Progress+"", Toast.LENGTH_LONG).show();
				saft.setMl(Progress);
				cocktail.setMlLeft(cocktail.getMlLeft()-Progress);


				cocktail.addSaft((Saft) saft);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_saft, menu);
		return true;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// saft.setdescNormal();
	}

}
