package projekt.MAtze;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.example.cocktailmixxer.R;

public class NewSaft extends Activity {
	// Nur ein Test 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newsaft);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_saft, menu);
		return true;
	}

}
