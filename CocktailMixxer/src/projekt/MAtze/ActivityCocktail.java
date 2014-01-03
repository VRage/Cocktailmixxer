package projekt.MAtze;

import projekt.OwnList.CustomListViewAdapter;
import projekt.helpclasses.BluetoothSerialService;
import projekt.helpclasses.CM_Status;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cocktailmixxer.R;

public class ActivityCocktail extends Activity {
	CM_Status status;
	ListView lw;
	CustomListViewAdapter CLVA;
	TextView Header;
	BluetoothSerialService service;
	Button order;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cocktail);
		status = (CM_Status) getApplicationContext();
		service = status.getBTservice();
		lw = (ListView)findViewById(R.id.Cocktail_ListView);
		CLVA = new CustomListViewAdapter(this, R.layout.activity_listitem,
				status.get_ActiveCocktail().get_SaftList_cocktail());
		lw.setAdapter(CLVA);
		order = (Button) findViewById(R.id.Cocktail_btnOrder);
		//dsafdsaf
		Header = (TextView)findViewById(R.id.cocktail_header);
		Header.setText(status.get_ActiveCocktail().getTitle());
		lw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				//status.set_ActiveSaft((Saft) CLVA.getItem(arg2));
				
			}
		});
		order.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Sendet Byte Array an Bluetooth 
				service.write(status.get_ActiveCocktail().getBluetoothSignal());
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cocktail, menu);
		return true;
		
	}

}
