package actual_working;

import com.example.cocktailmixxer.R;
import com.example.cocktailmixxer.R.layout;
import com.example.cocktailmixxer.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NewSaft extends Activity {

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
