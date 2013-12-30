package projekt.Jakob;

import java.util.List;

import projekt.helpclasses.BluetoothSerialService;
import projekt.helpclasses.CM_Status;
import OwnList.CustomListViewAdapter;
import OwnList.RowItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.example.cocktailmixxer.R;

public class ActivityAdmin extends Activity implements OnClickListener {
	CM_Status status;
	List<RowItem> safte;
	ListView saftlist_intern;
	static Button b1, b2, b3, b4, b5, b6, b7, b8;
	CustomListViewAdapter adapter;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		status = (CM_Status) getApplicationContext();

		saftlist_intern = (ListView) findViewById(R.id.admin_saftintern);
		safte = status.get_SaftList_intern();

		adapter = new CustomListViewAdapter(this,R.layout.activity_listitem, safte);
		saftlist_intern.setAdapter(adapter);
	

		b1 = (Button) findViewById(R.id.admin_behalter1);
		b2 = (Button) findViewById(R.id.admin_behalter2);
		b3 = (Button) findViewById(R.id.admin_behalter3);
		b4 = (Button) findViewById(R.id.admin_behalter4);
		b5 = (Button) findViewById(R.id.admin_behalter5);
		b6 = (Button) findViewById(R.id.admin_behalter6);
		b7 = (Button) findViewById(R.id.admin_behalter7);
		b8 = (Button) findViewById(R.id.admin_behalter8);

		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b7.setOnClickListener(this);
		b8.setOnClickListener(this);
		
		
	}
	


	@Override
	protected void onResume() {
		adapter.notifyDataSetChanged();
		super.onResume();
	}



	public void onClick(View v)
	{
		Intent intent = new Intent(ActivityAdmin.this,SelectSaftActivity.class);
		switch (v.getId()){
		case R.id.admin_behalter1:
			intent.putExtra("Bottlenumber", "1");
			startActivity(intent);
			break;
		case R.id.admin_behalter2:
			intent.putExtra("Bottlenumber", "2");
			startActivity(intent);
			break;
		case R.id.admin_behalter3:
			intent.putExtra("Bottlenumber", "3");
			startActivity(intent);
			break;
		case R.id.admin_behalter4:
			intent.putExtra("Bottlenumber", "4");
			startActivity(intent);
			break;
		case R.id.admin_behalter5:
			intent.putExtra("Bottlenumber", "5");
			startActivity(intent);
			break;
		case R.id.admin_behalter6:
			intent.putExtra("Bottlenumber", "6");
			startActivity(intent);
			break;
		case R.id.admin_behalter7:
			intent.putExtra("Bottlenumber", "7");
			startActivity(intent);
			break;
		case R.id.admin_behalter8:
			intent.putExtra("Bottlenumber", "8");
			startActivity(intent);
			break;
			
		}
	}
	public void clearList(View view)
	{
		for(int i=0;i<8;i++){
			
			safte.set(i, null);
		}
		adapter.notifyDataSetChanged();
		
	}
}
