package projekt.Activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.cocktailmixxer.R;


public class ActivitySettings extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	//Define Button **Cocktails** and On Click Action
			Button btn_Bluetooth = (Button)findViewById(R.id.Settings_BtnBluetooth);
			btn_Bluetooth.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					startActivity(new Intent(ActivitySettings.this, ActivityBluetooth.class)); //btnPressedCockTailsList();
				}
				
				
			});
	
}
}
