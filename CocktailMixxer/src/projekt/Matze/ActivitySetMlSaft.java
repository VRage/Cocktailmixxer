package projekt.Matze;

import projekt.helpclasses.CM_Status;
import projekt.helpclasses.Cocktail;
import projekt.helpclasses.Saft;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cocktailmixxer.R;
//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Matthias Wildberg
public class ActivitySetMlSaft extends Activity {
	CM_Status status;
	SeekBar seekbar;
	TextView MlLeft;
	TextView MlActual;
	TextView title;
	Button NewSaft;
	Button delete;
	Button up;
	Button down;
	Cocktail cocktail;
	Saft saft;
	int Progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addsaft);
		// hole Attribute 
		status = (CM_Status) getApplicationContext();
		cocktail = status.get_ActiveCocktail();
		saft = status.get_ActiveCocktail().getActiveSaft();
		
		//definiere GUI Elemente 
		seekbar = (SeekBar) findViewById(R.id.addSaft_seekbar);
		title= (TextView) findViewById(R.id.addsaft_title);
		MlLeft = (TextView) findViewById(R.id.addSaft_textViewMlLeft);
		MlActual = (TextView) findViewById(R.id.addSaft_textViewMlActual);
		delete = (Button) findViewById(R.id.addSaft_delete);
		up = (Button) findViewById(R.id.addSaft_up);
		up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				seekbar.setProgress(seekbar.getProgress()+1);
				setTextfields();
				
			}
		});
		down = (Button) findViewById(R.id.addSaft_down);
		down.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				seekbar.setProgress(seekbar.getProgress()-1);
				setTextfields();
				if(Progress <0)
					seekbar.setProgress(cocktail.cocktailsize-cocktail.getMlLeft());
				
			}
		});
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cocktail.get_SaftList_cocktail().remove(saft);
				finish();
			}
		});
		NewSaft = (Button) findViewById(R.id.addSaft_ButtonNewSaft);
		NewSaft.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ButtonClickAction();
			}
		});
		seekbar.setMax(cocktail.cocktailsize);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if(Progress<0)
					seekbar.setProgress(cocktail.cocktailsize-cocktail.getMlLeft());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				setTextfields();
				
			}
		});
	}
	@Override
	protected void onResume() { //DO NOT TOUCH ! 
		// Setzte Cocktail ml left - ml des aktuellen Saftes 
		//und setzte textfelder 
		cocktail = status.get_ActiveCocktail();
		saft = status.get_ActiveCocktail().getActiveSaft();
		//Seetzte verbleibende Ml 
		cocktail.setMlLeft((int) (cocktail.getMlLeft()+saft.getMl()));
		//setzte seekbar auf verbleibende ml 
		seekbar.setProgress(cocktail.cocktailsize-cocktail.getMlLeft());
		setTextfields();
		
		//Wenn Cocktail bereits in Liste kannn er mittels Button gelöscht werden 
		if(cocktail.get_SaftList_cocktail().contains(saft))
			delete.setVisibility(Button.VISIBLE);
		else
			delete.setVisibility(Button.INVISIBLE);
		super.onResume();
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK){
			cocktail.setMlLeft((int) (cocktail.getMlLeft()-saft.getMl()));
		}
		return super.onKeyDown(keyCode, event);
	}
	private void setTextfields(){
		CalcProgress();
		MlLeft.setText("Verfügbare Menge: "+(cocktail.getMlLeft()-Progress)+"ml ");
		MlActual.setText("Enthaltene Menge: "+Progress+"ml ");
	}
	private void CalcProgress(){
		Progress = seekbar.getProgress()-(cocktail.cocktailsize-cocktail.getMlLeft());
	}
	private  void ButtonClickAction(){
		CalcProgress();
		
		if(Progress <10)
			Toast.makeText(getApplicationContext(), "Bitte Wert größer als 10 ml eingeben", Toast.LENGTH_LONG).show();
		else if(cocktail.get_SaftList_cocktail().contains(saft)){
			saft.setMl(Progress);
			cocktail.setMlLeft((int) (cocktail.getMlLeft()-saft.getMl()));	
			saft.setdescNormal();
			finish();
		}
		else 
		{
			saft.setMl(Progress);
			cocktail.setMlLeft((int) (cocktail.getMlLeft()-saft.getMl()));
			cocktail.get_SaftList_cocktail().add(saft);
			saft.setdescNormal();
			finish();
		}
			
		
	}
}
