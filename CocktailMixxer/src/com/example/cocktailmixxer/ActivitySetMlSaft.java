package com.example.cocktailmixxer;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Cocktail;
import actual_working.Saft;
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

		// Set default text of textView
		MlLeft.setText("Verfügbare Menge:" + cocktail.getMlLeft());
		MlActual.setText("Aktuelle Menge:" + seekbarValueMl.getProgress());

		seekbarValueMl
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					int MlLeftv = cocktail.getMlLeft();
					int cocktailsize = cocktail.cocktailsize;
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						int Progress = seekbarValueMl.getProgress()-(cocktail.cocktailsize-MlLeftv);
						//Die Progressbar darf nicht 
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
						// int MlLeftint= (int)
						// ((double)saft.getProcent()*status.get_Cocktailsize()/100);

						// hole aktuellen Prozessstatus
						int Progress = seekbarValueMl.getProgress()-(cocktail.cocktailsize-MlLeftv);
						

						// Grenzen setzen darf nicht kleiner sein als aktuell
						// noch verfügbar und nicht größer sein als cocktail
						
//						else if (Progress >= cocktailsize)
//							seekbarValueMl.setProgress(cocktailsize);

						// Setze textfelder
						MlLeft.setText("Verfügbare Menge:\t\t"
								+ (MlLeftv - Progress)+" ml");
						MlActual.setText("Aktuelle Menge:\t\t" + Progress+" ml");

					}

				});
		NewSaft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int Progress = seekbarValueMl.getProgress()-(cocktail.cocktailsize-cocktail.getMlLeft());
				
				
				cocktail.setMlLeft(cocktail.getMlLeft()-Progress);
				// double procent =
				// 100.0/status.get_Cocktailsize()*ValueMl.getProgress();
				// saft.setProcent(procent);
				// Toast.makeText(getApplicationContext(), procent+"",
				// Toast.LENGTH_LONG);
				// status.set_MlLeft(status.get_MlLeft()/*+(int)(saft.getProcent()/100*status.get_Cocktailsize())*/-ValueMl.getProgress());
				// //saft.setDescList();
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
