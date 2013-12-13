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
		seekbarValueMl = (SeekBar)findViewById(R.id.addSaft_seekbar);
		
		
		
		
		
		MlLeft =(TextView)findViewById(R.id.addSaft_textViewMlLeft);
		MlActual = (TextView)findViewById(R.id.addSaft_textViewMlActual);
		NewSaft = (Button) findViewById(R.id.addSaft_ButtonNewSaft);
		cocktail= status.get_ActiveCocktail();
		
		MlLeft.setText("Verfügbare Menge:" +cocktail.getMlLeft());
		seekbarValueMl.setProgress(cocktail.getMlLeft());
		saft=status.get_ActiveCocktail().getActiveSaft();
		
		seekbarValueMl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				//int MlLeftint= (int) ((double)saft.getProcent()*status.get_Cocktailsize()/100);
				String ausgabe = "hallo: "+(cocktail.getMlLeft());
				int value = seekbarValueMl.getProgress();
				if(value >=cocktail.getMlLeft()){
				saft.setMl(value);
				MlLeft.setText("Verfügbare Menge: "+(cocktail.getMlLeft()-saft.getMl()));
				MlActual.setText("Aktuelle Menge: "+value);
				MlLeft.setText(ausgabe);
				}
				
			}
		});
		NewSaft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int procent =(int) ((double)100.0/cocktail.cocktailsize*seekbarValueMl.getProgress());
				Toast.makeText(getApplicationContext(), procent+"", Toast.LENGTH_LONG).show();
				saft.setProcent(procent);
				
//				double procent = 100.0/status.get_Cocktailsize()*ValueMl.getProgress();
//				saft.setProcent(procent);
//				Toast.makeText(getApplicationContext(), procent+"", Toast.LENGTH_LONG);
//				status.set_MlLeft(status.get_MlLeft()/*+(int)(saft.getProcent()/100*status.get_Cocktailsize())*/-ValueMl.getProgress());
//				//saft.setDescList();
				cocktail.addSaft((Saft)saft);
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
		//saft.setdescNormal();
	}

}
