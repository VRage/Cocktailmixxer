package projekt.Matze;

import org.achartengine.chart.PieChart;

import projekt.OwnList.CustomListViewAdapter;
import projekt.helpclasses.BluetoothSerialService;
import projekt.helpclasses.CM_Status;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cocktailmixxer.R;
//@ Project : Cocktailmixxer
//@ Date : 31.10.2013
//@ Author : Matthias Wildberg
public class ActivityCocktail extends Activity {
	CM_Status status;
	ListView lw;
	CustomListViewAdapter CLVA;
	TextView Header;
	TextView Desc;
	BluetoothSerialService service;
	Button order;
	Button graph;
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
		Desc = (TextView)findViewById(R.id.cocktail_desc);
		Desc.setText(status.get_ActiveCocktail().getDesc());
		graph = (Button) findViewById(R.id.cocktail_graph); 
		
		
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

				service.write(status.get_ActiveCocktail().getBluetoothSignal(status.get_SaftList_intern()));
				Toast.makeText(getApplicationContext(), status.get_ActiveCocktail().ret, Toast.LENGTH_LONG).show();
				if (status.get_ActiveUser() != null){
					status.get_ActiveUser().addCocktail(status.get_ActiveCocktail());
					status.get_ActiveUser().setAlkgehalt();
					status.get_ActiveUser().calcAlkAnteil();
				}
				finish();
			}
		});
		graph.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PieGraph pie = new PieGraph();
				Intent lineIntend = pie.getIntent(getApplicationContext());
				startActivity(lineIntend);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cocktail, menu);
		return true;
		
	}
	@Override
	protected void onResume() {
		
		boolean allSaftincluded = true;
		
		
		for (int j = 0; j < status.get_ActiveCocktail().get_SaftList_cocktail().size(); j++) {
			if(status.get_SaftList_intern().contains(status.get_ActiveCocktail().get_SaftList_cocktail().get(j)))			{}
			else				allSaftincluded = false;
			

		if(allSaftincluded)
			order.setVisibility(View.VISIBLE);
		else
			order.setVisibility(View.INVISIBLE);
		if(service.getState()!=BluetoothSerialService.STATE_CONNECTED)
			order.setText("noch nicht connected");
		else 
			order.setText("Bestellen");
		super.onResume();
	}
	}
	


}
